package ch.darklions.main.world;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import ch.darklions.util.AssetPool;
import ch.darklions.voxelengine.GameItem;
import ch.darklions.voxelengine.graph.Mesh;
import ch.darklions.voxelengine.graph.ShaderProgram;
import ch.darklions.voxelengine.graph.Texture;
import ch.darklions.voxelengine.graph.Transformation;

public class Chunk {

	GameItem gameItem;
	/*======================================
	 * Used List because it is more flexible then a array.
	=====================================*/
	ArrayList<Float> posList = new ArrayList<>();
	ArrayList<Integer> indicesList = new ArrayList<>();
	ArrayList<Float> texturePosList = new ArrayList<>();
	Vector3f position;
	
	/*
	 * Will be later replaced with a 3D integer array
	 */
	private boolean[][][] voxelMap = new boolean[VoxelData.CHUNK_WIDTH][VoxelData.CHUNK_HEIGHT][VoxelData.CHUNK_WIDTH];

	public Chunk(Vector3f chunkPosition, float[][] noiseMap, int height) {
		this.position = chunkPosition;

		this.generateVoxelMap(height, noiseMap);
		
		int cubeNumber = 0;
		for (int y = 0; y < VoxelData.CHUNK_HEIGHT; y++) {
			for (int x = 0; x < VoxelData.CHUNK_WIDTH; x++) {
				for (int z = 0; z < VoxelData.CHUNK_WIDTH; z++) {	
					if (voxelMap[x][y][z]) {
						if (addVoxelDataToChunk(
								new Vector3f(chunkPosition.x + x, chunkPosition.y + y, chunkPosition.z + z), cubeNumber)) {
							//Only increase the cube number if it possible to add a new cube to the mesh.
							//To prevent some problems with the indices.
							cubeNumber++;
						};
					}
				}
			}
		}
		
		/*
		 * No better solution then loop over the list to convert it to an array.
		 */
		float[] positions = new float[posList.size()];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = posList.get(i);
		}

		int[] indices = new int[indicesList.size()];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = indicesList.get(i);
		}

		float[] textureCoords = new float[texturePosList.size()];
		for (int i = 0; i < textureCoords.length; i++) {
			textureCoords[i] = texturePosList.get(i);
		}

		/*
		 * Try function, becuase the Texture class is throwing an exception.
		 */
		try {
			Texture texture = AssetPool.getTexture("src/main/resources/textures/grassblock.png");
			gameItem = new GameItem(new Mesh(positions, textureCoords, indices, texture));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean addVoxelDataToChunk(Vector3f pos, int cubeNumber) {
		/*
		 * To check if it necessary to render the voxel.
		 */
		if (checkForVoxel(pos)) {
			for (int i = 0; i < 6; i++) {

				// Add cube vertices to mesh
				for (int v = 0; v < 4; v++) {
					posList.add(VoxelData.VOXEL_VERTICES[i][0 + (v * 3)] + pos.x);
					posList.add(VoxelData.VOXEL_VERTICES[i][1 + (v * 3)] + pos.y);
					posList.add(VoxelData.VOXEL_VERTICES[i][2 + (v * 3)] + pos.z);
				}

				// Add vertex indices to mesh
				for (int j = 0; j < VoxelData.VOXEL_INDICES[i].length; j++) {
					indicesList.add(VoxelData.VOXEL_INDICES[i][j] + (cubeNumber * 24)); // 24 vertices count
				}

				// Add texture positions to mesh
				for (int j = 0; j < VoxelData.TEXTURE_POSITIONS[i].length; j++) {
					texturePosList.add(VoxelData.TEXTURE_POSITIONS[i][j]);
				}
			}
			
			//Return true statement if it possible to add a voxel to the mesh.
			return true;
		} else {
			//Return false statement if it not possible to add a voxel to the mesh.
			return false;
		}
	}

	/*
	 * use the noisemap to set the y-height
	 */
	private void generateVoxelMap(int height, float[][] noiseMap) {
		for (int x = 0; x < VoxelData.CHUNK_WIDTH; x++) {
			for (int z = 0; z < VoxelData.CHUNK_WIDTH; z++) {
				
				//Y value is from the perlinnoise.
				for (int y = 0; y < (int) (noiseMap[x][z] * height) + 1; y++)
					voxelMap[x][y][z] = true;
			}
		}
	}
	
	private boolean checkForVoxel(Vector3f pos) {
		
		/*
		 * The voxel position minus the chunkposition to get the voxelposition relative to the chunk.
		 */
		int x = (int) (pos.x - position.x);
		int y = (int) (pos.y - position.y);
		int z = (int) (pos.z - position.z);
		
		//Forloop to check for all 6 sides
		for(int i = 0; i < 6; i++) {
			try {

				if(!voxelMap
						[(int) (x + VoxelData.FACE_SIDE_CHECKS[i].x)]
						[(int) (y + VoxelData.FACE_SIDE_CHECKS[i].y)]
						[(int) (z + VoxelData.FACE_SIDE_CHECKS[i].z)]) {
					//Return true statement if has no neighbors
					return true;
				}
			} catch (Exception e) {
				//Return true if it out of arraybound.
				return true;
			}
		}

		//Return false if it has all 6 neighbors and no "ArrayIndexOutOfBoundsExepction".
		return false;
	}

	//Each mesh has its own render method.
	public void renderVoxels(ShaderProgram shaderProgram, Transformation transformation, Matrix4f viewMatrix) {
		// Set model view matrix for this item
		Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
		shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
		gameItem.getMesh().render();
	}

	public void cleanUp() {
		gameItem.getMesh().cleanup();
	}
	
	/*
	 * Centered chunkposition
	 */
	public Vector3f getChunkPosition() {
		float x = position.x + (VoxelData.CHUNK_WIDTH / 2);
		float y = position.y + (VoxelData.CHUNK_HEIGHT / 2);
		float z = position.z + (VoxelData.CHUNK_WIDTH / 2);
		
		return new Vector3f(x, y, z);
	}
	
	/*
	 * Chunkposition with custom y-axis
	 */
	public Vector3f getChunkPosition(float y) {
		float x = position.x + (VoxelData.CHUNK_WIDTH / 2);
		float z = position.z + (VoxelData.CHUNK_WIDTH / 2);
		
		return new Vector3f(x, y, z);
	}
}
