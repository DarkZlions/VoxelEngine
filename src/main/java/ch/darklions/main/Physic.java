package ch.darklions.main;

import ch.darklions.voxelengine.GameItem;
import ch.darklions.voxelengine.graph.Camera;

public class Physic {

	private final Camera cam;
	private static final double gravity = 9.81;
	
	private boolean isColliding = false;
	
	public Physic(Camera camIn) {
		this.cam = camIn;
	}
	
	public void applyGravity() {
		if(!isColliding) {
		cam.movePosition(0, -0.001f, 0);
		}
	}
	
	public void applyTerrainCollision() {
		
	}
	
}
