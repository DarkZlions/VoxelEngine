package ch.darklions.voxelengine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Window {
	private final String title;

	private int width;

	private int height;

	private long windowHandle;

	private boolean resized;

	private boolean vSync;

	public Window(String title, int width, int heigth, boolean vSync) {
		this.title = title;
		this.width = width;
		this.height = heigth;
		this.vSync = vSync;
		this.resized = false;
	}

	public void init() {
		// Setup an error callback. The default implemention
		// wil print the error message in System.err
		GLFWErrorCallback.createPrint(System.err).set();

		// Initliaze GLFW. Most GLFW functions will not work before doint this.
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initliaze GLFW");
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		// Create the window
		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		if (windowHandle == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Setup the resize callback
		glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
			this.width = width;
			this.height = height;
			this.setResized(true);
		});

		// Get the resolution of the main monitor
		GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center the window
		glfwSetWindowPos(windowHandle, (mode.width() - width) / 2, (mode.height() - height) / 2);

		// Make the OpenGL contex current
		glfwMakeContextCurrent(windowHandle);

		if (isvSync()) {
			// Enable v-sync
			glfwSwapInterval(1);
		}

		// Make the window visible
		glfwShowWindow(windowHandle);

		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glEnable(GL_DEPTH_TEST);
	}

	public void setClearColor(float r, float g, float b, float alpha) {
		glClearColor(r, g, b, alpha);
	}

	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}

	public boolean isKeyReleased(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_RELEASE;
	}

	public boolean windowShouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isResized() {
		return resized;
	}

	public void setResized(boolean resized) {
		this.resized = resized;
	}

	public boolean isvSync() {
		return vSync;
	}

	public void setvSync(boolean vSync) {
		this.vSync = vSync;
	}

	public long getWindowHandle() {
		return this.windowHandle;
	}

	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}

}
