package ch.darklions.main.world;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import ch.darklions.main.Transform;
import ch.darklions.voxelengine.GameItem;
import ch.darklions.voxelengine.graph.Texture;

public class TerrainGeneration {

	public static GameItem[] createTerrain(int xSize, int ySize, int zSize, float offset_X, float offset_Y, float offset_Z,
			Texture texture, int noiseFrequency, int maxHeight) {

		PerlinNoise noise = new PerlinNoise();
		List<GameItem> itemList = new ArrayList<>();

		float[][] seed = noise.GenerateWhiteNoise(xSize, zSize);
		float[][] seedE = noise.GenerateSmoothNoise(seed, noiseFrequency);
		float[][] perlinNoise = noise.GeneratePerlinNoise(seedE, noiseFrequency);
		
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				for (int z = 0; z < zSize; z++) {

					GameItem item = new GameItem(Transform.getCube(texture));
					item.setPosition(offset_X + x, offset_Y + y + ((int)(perlinNoise[x][z] * maxHeight)), offset_Z + z);
					itemList.add(item);

				}
			}
		}

		GameItem[] items = new GameItem[itemList.size()];

		for (int i = 0; i < itemList.size(); i++) {

			items[i] = itemList.get(i);

		}

		return items;
	}
	
	public static List<Chunk> createVoxelTerrain(int noise, int chunkNumber, int height) {	
		List<Chunk> chunks = new ArrayList<>();
		
		PerlinNoise noiseMap = new PerlinNoise();
		float[][] seed = noiseMap.GenerateWhiteNoise(
				(int) (chunkNumber * VoxelData.CHUNK_WIDTH),
				(int) (chunkNumber * VoxelData.CHUNK_WIDTH));
		
		float[][] seedE = noiseMap.GenerateSmoothNoise(seed, noise);
		float[][] perlinNoise = noiseMap.GeneratePerlinNoise(seedE, noise);
		
		for (int x = 0; x < chunkNumber; x++) {
			for (int z = 0; z < chunkNumber; z++) {
				
				float[][] newMap = new float[perlinNoise.length][perlinNoise.length];

				for (int i = 0; i < VoxelData.CHUNK_WIDTH; i++) {
					for (int j = 0; j < VoxelData.CHUNK_WIDTH; j++) {
						newMap[i][j] = perlinNoise
								[i + (x * VoxelData.CHUNK_WIDTH)]
								[j + (z * VoxelData.CHUNK_WIDTH)];
					}
				}
				
				chunks.add(
						new Chunk(new Vector3f(x * VoxelData.CHUNK_WIDTH, 0, z * VoxelData.CHUNK_WIDTH), newMap, height));
			}
		}
		
		System.out.println(String.valueOf((int) (Math.pow(chunkNumber, 2) * (Math.pow(VoxelData.CHUNK_WIDTH, 2) * VoxelData.CHUNK_HEIGHT))) + " voxels are generated.");

		return chunks;
	}

}
