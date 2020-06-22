package ch.darklions.main;

import ch.darklions.voxelengine.GameEngine;
import ch.darklions.voxelengine.ILogic;

public class Main 
{
	public static void main(String[] args)
	{
		try
		{
			boolean vSync = true;
			ILogic logic = new VoxelEngine();
			GameEngine engine = new GameEngine("Voxel Engine", 1080, 720, vSync, logic);
			engine.run();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
