package ch.darklions.voxelengine.input;

import static org.lwjgl.glfw.GLFW.*;

import ch.darklions.main.VoxelEngine;
import ch.darklions.voxelengine.Window;
import ch.darklions.voxelengine.graph.Camera;

public class KeyboardInput {

	private final Window window;
	private final VoxelEngine engine;
	private final Camera camera;

	public boolean isCursorLocked = false;

	public KeyboardInput(Window windowIn, VoxelEngine engine, Camera camera) {
		this.window = windowIn;
		this.engine = engine;
		this.camera = camera;
	}

	public void init() {
		glfwSetKeyCallback(window.getWindowHandle(), (windowHandle, key, scancode, action, mods) -> {
			this.closeWindowCallback(windowHandle, key, scancode, action, mods);
			this.toggleCursorLock(windowHandle, key, scancode, action, mods);
			this.generateNewTerrain(windowHandle, key, scancode, action, mods);
		});
	}

	private void lockCursor() {

		glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	}

	private void unlockCursor() {

		glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);

	}

	private void closeWindowCallback(long windowHandle, int key, int scancode, int action, int mods) {

		if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
			glfwSetWindowShouldClose(windowHandle, true);
		}
	}

	/*
	 * Toggle cursor lock for window key: TAB
	 */
	private void toggleCursorLock(long windowHandle, int key, int scancode, int action, int mods) {

		if (isCursorLocked) {
			lockCursor();
		} else {
			unlockCursor();
		}

		if (action == GLFW_RELEASE)
			return;
		if (key == GLFW_KEY_TAB)
			isCursorLocked = !isCursorLocked;
	}

	/*
	 * Generate new Terrain
	 */
	private void generateNewTerrain(long windowHandle, int key, int scancode, int action, int mods) {

		if (action == GLFW_RELEASE) {
			return;
		}
		if (key == GLFW_KEY_R) {
			engine.createNewTerrain();
		}
	}

	/*
	 * Update the keyboard input. Make the voxelengine class more clean
	 */
	public void updateKeyboardInput(Camera camera, Window window) {
		movement(window, camera);

		if (window.isKeyPressed(GLFW_KEY_UP)) {
			camera.setRenderDistance(camera.getRenderDistance() + 1);
		} else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			camera.setRenderDistance(camera.getRenderDistance() - 1);
		}
	}

	private void movement(Window window, Camera camera) {
		camera.getCameraInc().set(0, 0, 0);

		if (window.isKeyPressed(GLFW_KEY_W)) {
			camera.getCameraInc().z = -1;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			camera.getCameraInc().z = 1;
		}

		if (window.isKeyPressed(GLFW_KEY_A)) {
			camera.getCameraInc().x = -1;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			camera.getCameraInc().x = 1;
		}

		if (window.isKeyPressed(GLFW_KEY_SPACE)) {
			camera.getCameraInc().y = 1;
		} else if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
			camera.getCameraInc().y = -1;
		}
	}
}
