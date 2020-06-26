package ch.darklions.util;

import java.util.HashMap;
import java.util.Map;

import ch.darklions.voxelengine.graph.Texture;

public class AssetPool {
	private static Map<String, Texture> textureMap = new HashMap<>();
	
	public static Texture getTexture(String resourceName) {
		try {
		if (AssetPool.textureMap.containsKey(resourceName)) {
			return AssetPool.textureMap.get(resourceName);
		} else {
			Texture texture = new Texture(resourceName);
			AssetPool.textureMap.put(resourceName, texture);
			return texture;
		}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
