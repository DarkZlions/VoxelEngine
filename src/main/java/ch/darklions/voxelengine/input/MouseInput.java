package ch.darklions.voxelengine.input;

import org.joml.Vector2d;
import org.joml.Vector2f;

import ch.darklions.voxelengine.Window;
import ch.darklions.voxelengine.graph.Camera;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

	private final Vector2d previousPos;

	private final Vector2d currentPos;

	private final Vector2f displVec;

	private boolean inWindow = false;

	private boolean leftButtonPressed = false;

	private boolean rightButtonPressed = false;

	public MouseInput() {
		previousPos = new Vector2d(-1, -1);
		currentPos = new Vector2d(0, 0);
		displVec = new Vector2f();
	}

	public void init(Window window) {

		glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
			currentPos.x = xpos;
			currentPos.y = ypos;
		});

		glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
			inWindow = entered;
		});

		glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
			setupMouseButtonCallback(windowHandle, button, action, mode);
		});

		glfwSetScrollCallback(window.getWindowHandle(), (windowHandle, offsetX, offsetY) -> {
			Camera.setCameraPosStep(Camera.CAMERA_POS_STEP + (float) ((offsetX + offsetY) * 0.001f));
		});
	}

	private void setupMouseButtonCallback(long window, int button, int action, int mode) {
		leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
		rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
	}
	
	public Vector2f getDisplVec() {
		return displVec;
	}

	public void input(Window window) {
		displVec.x = 0;
		displVec.y = 0;
		if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
			double deltax = currentPos.x - previousPos.x;
			double deltay = currentPos.y - previousPos.y;
			boolean rotateX = deltax != 0;
			boolean rotateY = deltay != 0;
			if (rotateX) {
				displVec.y = (float) deltax;
			}
			if (rotateY) {
				displVec.x = (float) deltay;
			}
		}
		previousPos.x = currentPos.x;
		previousPos.y = currentPos.y;
	}

	public boolean isLeftButtonPressed() {
		return leftButtonPressed;
	}

	public boolean isRightButtonPressed() {
		return rightButtonPressed;
	}

	public boolean isMouseInWindow() {
		return inWindow;
	}
	
	public void updateMouseInput(Camera camera, MouseInput mouseInput) {
        if (mouseInput.isMouseInWindow()) {
        	
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * camera.getMouseSensitivity(), rotVec.y * camera.getMouseSensitivity(), 0);
        }
	}

}
