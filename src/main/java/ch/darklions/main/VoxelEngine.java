package ch.darklions.main;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import ch.darklions.main.world.Chunk;
import ch.darklions.main.world.TerrainGeneration;
import ch.darklions.main.world.VoxelData;
import ch.darklions.voxelengine.GameItem;
import ch.darklions.voxelengine.ILogic;
import ch.darklions.voxelengine.Utils;
import ch.darklions.voxelengine.Window;
import ch.darklions.voxelengine.graph.Camera;
import ch.darklions.voxelengine.graph.Texture;
import ch.darklions.voxelengine.input.KeyboardInput;
import ch.darklions.voxelengine.input.MouseInput;

public class VoxelEngine implements ILogic {
	private final Renderer renderer;
	private GameItem[] gameItems;
	private final Camera camera;
	private Window window;
	private KeyboardInput keyboardInput;
	private final List<Chunk> chunks;

	public VoxelEngine() {
		this.renderer = new Renderer();
		this.camera = new Camera();
		this.chunks = new ArrayList<>();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);

		this.window = window;

		int chunkNumber = 25;
		for (int x = 0; x < chunkNumber; x++) {
			for (int z = 0; z < chunkNumber; z++) {
				chunks.add(new Chunk(new Vector3f(x * VoxelData.CHUNK_WIDTH, 0, z * VoxelData.CHUNK_WIDTH)));
			}
		}
		
		System.out.println(Math.pow(chunkNumber, 2) * (Math.pow(VoxelData.CHUNK_WIDTH, 2) * VoxelData.CHUNK_HEIGHT) + " voxels are generated.");

		/*
		 * Setup keyboard Inputs or callbacks
		 */
		this.keyboardInput = new KeyboardInput(window, this, camera);
		keyboardInput.init();
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

	public void createNewTerrain() {
		try {
			Texture texture = new Texture(Utils.getResourceLocation("textures/grassblock.png"));
			this.gameItems = TerrainGeneration.createTerrain(200, 2, 200, -100, -100, -100, texture, 4, 50);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
