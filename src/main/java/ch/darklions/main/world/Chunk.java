package ch.darklions.main.world;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import ch.darklions.voxelengine.GameItem;
import ch.darklions.voxelengine.Utils;
import ch.darklions.voxelengine.graph.Mesh;
import ch.darklions.voxelengine.graph.ShaderProgram;
import ch.darklions.voxelengine.graph.Texture;
import ch.darklions.voxelengine.graph.Transformation;

public class Chunk {

	GameItem gameItem = new GameItem(null);
	ArrayList<Float> posList = new ArrayList<>();
	ArrayList<Integer> indicesList = new ArrayList<>();
	ArrayList<Float> texturePosList = new ArrayList<>();
	private Vector3f chunkPosition;
	private boolean[][][] voxelMap = new boolean[VoxelData.CHUNK_WIDTH][VoxelData.CHUNK_HEIGHT][VoxelData.CHUNK_WIDTH];

	public Chunk(Vector3f chunkPosition) {
		this.chunkPosition = chunkPosition;
		//this.populateVoxelMap();

		int cubeNumber = 0;
		for (int y = 0; y < VoxelData.CHUNK_HEIGHT; y++) {
			for (int x = 0; x < VoxelData.CHUNK_WIDTH; x++) {
				for (int z = 0; z < VoxelData.CHUNK_WIDTH; z++) {
					addVoxelDataToChunk(new Vector3f(chunkPosition.x + x, chunkPosition.y + y, chunkPosition.z + z), cubeNumber);
					cubeNumber++;
				}
			}
		}
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

		try {

			Texture texture = new Texture(Utils.getResourceLocation("textures/grass.png"));
			gameItem = new GameItem(new Mesh(positions, textureCoords, indices, texture));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addVoxelDataToChunk(Vector3f pos, int cubeNumber) {

		for (int i = 0; i < VoxelData.VOXEL_VERTICES.length; i++) {
			posList.add(VoxelData.VOXEL_VERTICES[i][0] + pos.x);
			posList.add(VoxelData.VOXEL_VERTICES[i][1] + pos.y);
			posList.add(VoxelData.VOXEL_VERTICES[i][2] + pos.z);
		}

		for (int i = 0; i < VoxelData.VOXEL_INDICES.length; i++) {
			for (int j = 0; j < VoxelData.VOXEL_INDICES[0].length; j++) {
				indicesList.add(VoxelData.VOXEL_INDICES[i][j] + (cubeNumber * VoxelData.VOXEL_VERTICES.length));
			}
		}
		
		for (int i = 0; i < VoxelData.TEXTURE_POSITIONS.length; i++) {
			
				texturePosList.add(VoxelData.TEXTURE_POSITIONS[i][0]);
				texturePosList.add(VoxelData.TEXTURE_POSITIONS[i][1]);
		}
	}

	private void populateVoxelMap() {
		for (int y = 0; y < VoxelData.CHUNK_HEIGHT; y++) {
			for (int x = 0; x < VoxelData.CHUNK_WIDTH; x++) {
				for (int z = 0; z < VoxelData.CHUNK_WIDTH; z++) {

					voxelMap[x][y][z] = true;
				}
			}
		}
	}
	
	private boolean CheckVoxel(Vector3f pos) {

		int x = (int) (pos.x - this.chunkPosition.x);
		int y = (int) (pos.y - this.chunkPosition.y);
		int z = (int) (pos.z - this.chunkPosition.z);

		return voxelMap[x][y][z];
	}

	public void renderVoxels(ShaderProgram shaderProgram, Transformation transformation, Matrix4f viewMatrix) {
		// Set model view matrix for this item
		Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
		shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
		gameItem.getMesh().render();
	}

	public void cleanUp() {
		gameItem.getMesh().cleanup();
	}

	public Vector3f getPosition() {
		return this.chunkPosition;
	}
}
