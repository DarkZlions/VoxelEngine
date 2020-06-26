package ch.darklions.voxelengine.graph;

import org.joml.Vector3f;

import ch.darklions.util.Utils;

public class Camera {

	private final Vector3f position;

	private final Vector3f rotation;

	private final Vector3f cameraInc;

	public static float CAMERA_POS_STEP = 0.01f;

	private int renderDistance;

	private static final float MOUSE_SENSITIVITY = .05f;

	public Camera() {

		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		cameraInc = new Vector3f();
		renderDistance = 200;

	}

	public Camera(Vector3f positionIn, Vector3f rotationIn) {

		this.position = positionIn;
		this.rotation = rotationIn;
		this.cameraInc = new Vector3f();

	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float posX, float posY, float posZ) {
		position.x = posX;
		position.y = posY;
		position.z = posZ;
	}

	public void movePosition(float offsetX, float offsetY, float offsetZ) {

		if (offsetZ != 0) {

			position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
			position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;

		}

		if (offsetX != 0) {

			position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
			position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;

		}

		position.y += offsetY;

	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(float rotX, float rotY, float rotZ) {
		rotation.x = rotX;
		rotation.y = rotY;
		rotation.z = rotZ;
	}

	public void moveRotation(float offsetX, float offsetY, float offsetZ) {
		rotation.x += offsetX;
		rotation.y += offsetY;
		rotation.z += offsetZ;
	}

	public float getCameraPosStep() {
		return CAMERA_POS_STEP;
	}

	public float getMouseSensitivity() {
		return MOUSE_SENSITIVITY;
	}

	public static void setCameraPosStep(float posStep) {
		CAMERA_POS_STEP = posStep;
	}

	public int getRenderDistance() {
		return this.renderDistance;
	}

	public void setRenderDistance(int distance) {
		this.renderDistance = (int) Utils.clampValue(distance, 10, 1000);
	}

	public Vector3f getCameraInc() {
		return this.cameraInc;
	}

	public void setCameraInc(float x, float y, float z) {
		this.cameraInc.x = x;
		this.cameraInc.y = y;
		this.cameraInc.z = z;
	}

	public void updatePosition() {

		movePosition(
				cameraInc.x * getCameraPosStep(),
				cameraInc.y * getCameraPosStep(),
				cameraInc.z * getCameraPosStep());
	}
}
