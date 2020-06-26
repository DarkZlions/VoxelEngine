package ch.darklions.main;

import java.util.List;

import ch.darklions.main.world.Chunk;
import ch.darklions.main.world.TerrainGeneration;
import ch.darklions.util.AssetPool;
import ch.darklions.voxelengine.ILogic;
import ch.darklions.voxelengine.Window;
import ch.darklions.voxelengine.graph.Camera;
import ch.darklions.voxelengine.input.KeyboardInput;
import ch.darklions.voxelengine.input.MouseInput;

public class VoxelEngine implements ILogic {
	private final Renderer renderer;
	private final Camera camera;
	private Window window;
	private KeyboardInput keyboardInput;
	private List<Chunk> chunks;

	public VoxelEngine() {
		this.renderer = new Renderer();
		this.camera = new Camera();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);

		this.window = window;

		/*
		 * Generate world
		 */
		chunks = TerrainGeneration.createVoxelTerrain(5, 32, 20);
		
		/*
		 * Setup keyboard Inputs or callbacks
		 */
		this.keyboardInput = new KeyboardInput(window, this, camera);
		keyboardInput.init();
		
		loadResources();
	}
	
	private void loadResources() {
		AssetPool.getTexture("src/main/resources/textures/grassblock.png");
	}

	@Override
	public void input(Window window, MouseInput input) {
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		// Update camera based on mouse
		mouseInput.updateMouseInput(camera, mouseInput);
		camera.updatePosition();
		keyboardInput.updateKeyboardInput(camera, window);
	}

	@Override
	public void render(Window window) {
		renderer.render(window, camera, chunks);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		for (Chunk chunk : chunks) {
			chunk.cleanUp();
		}
		/*
		 * for(GameItem item : gameItems) { item.getMesh().cleanup(); }
		 */
	}
}
