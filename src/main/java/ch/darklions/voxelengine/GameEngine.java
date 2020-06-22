package ch.darklions.voxelengine;

import ch.darklions.voxelengine.input.MouseInput;

public class GameEngine implements Runnable {
	public static final int TARGET_FPS = 75;

	public static final int TARGET_UPS = 30;

	private final Window window;

	private final Timer timer;

	private final ILogic logic;

	private final MouseInput input;

	private final Thread gameLoopThread;

	public GameEngine(String windowTitle, int width, int height, boolean vSync, ILogic logic) throws Exception {
		this.window = new Window(windowTitle, width, height, vSync);
		input = new MouseInput();
		this.logic = logic;
		this.timer = new Timer();
		gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
	}

	@Override
	public void run() {
		try {
			init();
			loop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanup();
		}
	}

	protected void init() throws Exception {
		window.init();
		timer.init();
		logic.init(window);
		input.init(window);
	}

	protected void loop() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;

		boolean running = true;
		while (running && !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;

			input();

			while (accumulator >= interval) {
				update(interval);
				accumulator -= interval;
			}

			render();

			if (!window.isvSync()) {
				sync();
			}
		}
	}

	protected void cleanup() {
		logic.cleanup();
	}

	private void sync() {
		float loopSlot = 1f / TARGET_FPS;
		double endTime = timer.getLastLoopTime() + loopSlot;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void input() {
		input.input(window);
		logic.input(window, input);
	}

	protected void update(float interval) {
		logic.update(interval, input);
	}

	protected void render() {
		logic.render(window);
		window.update();
	}
}
