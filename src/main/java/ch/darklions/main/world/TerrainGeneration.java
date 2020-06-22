package ch.darklions.main.world;

import java.util.ArrayList;
import java.util.List;

import ch.darklions.main.Transform;
import ch.darklions.voxelengine.GameItem;
import ch.darklions.voxelengine.graph.Texture;

public class TerrainGeneration {

	public static GameItem[] createTerrain(int xSize, int ySize, int zSize, float offset_X, float offset_Y, float offset_Z,
			Texture texture, int noiseFrequency, int maxHeight) {

		PerlinNoise noise = new PerlinNoise();
		List<GameItem> itemList = new ArrayList<>();

		float[][] seed = PerlinNoise.GenerateWhiteNoise(xSize, zSize);
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

}
