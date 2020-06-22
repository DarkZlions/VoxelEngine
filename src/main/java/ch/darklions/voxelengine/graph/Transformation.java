package ch.darklions.voxelengine.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import ch.darklions.voxelengine.GameItem;

public class Transformation 
{
	private final Matrix4f projectionMatrix;
	
	private final Matrix4f modelViewMatrix;
	
	private final Matrix4f viewMatrix;
	
	public Transformation()
	{
		projectionMatrix = new Matrix4f();
		modelViewMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}
	
    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        return projectionMatrix.setPerspective(fov, width / height, zNear, zFar);
    }
    
    public Matrix4f getViewMatrix(Camera cam) {
    	
    	Vector3f pos = cam.getPosition();
    	Vector3f rot = cam.getRotation();
    	
    	viewMatrix.identity();
    	
    	//First do the rotation so camera rotates over its position
    	viewMatrix
    	.rotate((float)Math.toRadians(rot.x), new Vector3f(1, 0, 0))
    	.rotate((float)Math.toRadians(rot.y), new Vector3f(0, 1, 0));
    	
    	//Then do the translation
    	viewMatrix.translate(-pos.x, -pos.y, -pos.z);
    	
    	return viewMatrix;
    	
    }
    
    public Matrix4f getModelViewMatrix(GameItem item, Matrix4f viewMatrix) {
    	
    	Vector3f rotation = item.getRotation();
    	
    	modelViewMatrix.identity().translate(item.getPosition())
    		.rotateX((float)Math.toRadians(-rotation.x))
    		.rotateY((float)Math.toRadians(-rotation.y))
    		.rotateZ((float)Math.toRadians(-rotation.z))
    		.scale(item.getScale());
    	
    	Matrix4f viewCurrent = new Matrix4f(viewMatrix);
    	
    	return viewCurrent.mul(modelViewMatrix);
    	
    }
}
