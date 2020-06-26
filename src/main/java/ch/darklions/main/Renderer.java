package ch.darklions.main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.List;

import org.joml.Matrix4f;

import ch.darklions.main.world.Chunk;
import ch.darklions.util.Utils;
import ch.darklions.voxelengine.Window;
import ch.darklions.voxelengine.graph.Camera;
import ch.darklions.voxelengine.graph.ShaderProgram;
import ch.darklions.voxelengine.graph.Transformation;

public class Renderer {

	private ShaderProgram shaderProgram;

	private Transformation transformation;

	/*
	 * Field of view in radians
	 */
	private static final float FOV = (float) Math.toRadians(60.0f);
	private static final float zNear = 0.01f;
	private static final float zFar = 1000.f;

	public Renderer() {
		transformation = new Transformation();
	}

	public void init(Window window) throws Exception {
		shaderProgram = new ShaderProgram();

		Utils utils = new Utils();

		shaderProgram.createVertexShader(utils.loadResource("shaders/vertex.vs"));
		shaderProgram.createFragmentShader(utils.loadResource("shaders/fragment.fs"));
		shaderProgram.link();

		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("modelViewMatrix");
		shaderProgram.createUniform("texture_sampler");
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Window window, Camera camera, List<Chunk> chunks) {
		clear();

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();

		/*
		 * Update the Matrix
		 */

		// Update projection matrix
		Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(),
				zNear, zFar);
		shaderProgram.setUniform("projectionMatrix", projectionMatrix);

		// Update view Matrix
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);

		shaderProgram.setUniform("texture_sampler", 0);

		// Render each gameItem
		for (Chunk chunk : chunks) {
			if (camera.getPosition().distance(chunk.getChunkPosition(camera.getPosition().y)) < camera.getRenderDistance()) {
				chunk.renderVoxels(shaderProgram, transformation, viewMatrix);
			}
		}

		shaderProgram.unbind();
	}

	public ShaderProgram getShaderProgram() {
		return this.shaderProgram;
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
