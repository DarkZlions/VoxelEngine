package ch.darklions.voxelengine;

import ch.darklions.voxelengine.input.MouseInput;

public interface ILogic 
{
	void init(Window window) throws Exception;
	
	void input(Window window, MouseInput mouseInput);
	
	void update(float interval, MouseInput mouseInput);
	
	void render(Window window);
	
	void cleanup();
}
