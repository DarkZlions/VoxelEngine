package ch.darklions.main.world;

import org.joml.Vector3f;

public class VoxelData {
	
	public static final int CHUNK_WIDTH = 16;
	public static final int CHUNK_HEIGHT = 256;

	/*
	 * Coordinates for each cube face
	 * "v" stand for "vertices"
	 */
	public final static float[][] VOXEL_VERTICES = {
			//front face
			//v0
			{-0.5f, 0.5f, 0.5f,
			//v1
			-0.5f,-0.5f, 0.5f,
			//v2
			0.5f,-0.5f, 0.5f,
			//v3
			0.5f, 0.5f, 0.5f,},
			
			// BackFace
			//v4
			 {-0.5f, 0.5f,-0.5f,
			//v5
			 0.5f, 0.5f,-0.5f,
			//v6
			-0.5f,-0.5f,-0.5f,
			//v7
			0.5f,-0.5f,-0.5f,},
			
			//Texture positions:
			
			// Top face
			//v8 = v4
			{-0.5f, 0.5f,-0.5f,
			//v9 = v0
			-0.5f, 0.5f, 0.5f,
			//v10 = v5
			 0.5f, 0.5f,-0.5f,
			//v11 = v3
			0.5f, 0.5f, 0.5f,},
			
			//Bottom face
			//v12 = v6
			{-0.5f,-0.5f,-0.5f,
			//v13 = v1
			-0.5f,-0.5f, 0.5f,
			//v14 = v7
			0.5f,-0.5f,-0.5f,
			//v15 = v2
			0.5f,-0.5f, 0.5f,},
			
			// Left face
			//v16 = v4
			{-0.5f, 0.5f,-0.5f,
			//v17 = v6
			-0.5f,-0.5f,-0.5f,
			//v18 = v0
			-0.5f, 0.5f, 0.5f,
			//v19 = v1
			-0.5f,-0.5f, 0.5f,
			},
			
			// Right face
			//v20 = v5
			{0.5f, 0.5f,-0.5f,
			//v21 = v7
			0.5f,-0.5f,-0.5f,
			//v22 = v3
			0.5f, 0.5f, 0.5f,
			//v23 = v2
			0.5f,-0.5f, 0.5f,},
	};
	public final static float[][] TEXTURE_POSITIONS = { 
            //Front face
			//Texture vertice order:
			//top-left -> top-right -> down-left -> down-right
			{
			0.0f, 0.0f,
			0.0f, 0.5f,
			0.5f, 0.5f,
			0.5f, 0.0f,
			},
			
			//Back face
			//Texture vertice order:
			//top-left -> top-right -> down-left -> down-right
			{
			 0.0f, 0.0f,
			 0.5f, 0.0f,
			 0.0f, 0.5f,
			 0.5f, 0.5f,
			},
			
			//Top face = back face
			{
			0.0f, 0.5f,
			0.5f, 0.5f,
			0.0f, 1.0f,
			0.5f, 1.0f,
			},
			
			//Bottom face = backface
			{
			0.5f, 0.0f,
			1.0f, 0.0f,
			0.5f, 0.5f,
			1.0f, 0.5f,
			},
			
			//Left face = back face
			//Texture vertice order:
			//top-right -> down-right -> top-left -> down-left
			{					
			0.5f, 0.0f,
			0.5f, 0.5f,
			0.0f, 0.0f,
			0.0f, 0.5f,
			},
			
			//Right face = back face
			//Texture vertice order:
			//top-right -> down-right -> top-left -> down-left
			{
			0.5f, 0.0f,
			0.5f, 0.5f,
			0.0f, 0.0f,
			0.0f, 0.5f,
			},
            
	};
	
	/*
	 * Indices for each cube face
	 */
	public final static int[][] VOXEL_INDICES = {
			//Front face
			{ 0,  1,  3,  3,  1,  2,},
			//Back face
			{ 4,  6,  5,  5,  6,  7,},
			//top face
			{ 8,  9, 10, 10,  9, 11,},
			//Bottom face
			{12, 13, 14, 14, 13, 15,},
			//Left face
			{16, 17, 18, 18, 17, 19,},
			//Right face
			{20, 21, 22, 22, 21, 23,},
	};
	
	/*
	 * Each xyz value a plus and minus, to check for each side.
	 */
	public static final Vector3f[] FACE_SIDE_CHECKS = {
			new Vector3f(0, 0, 1),
			new Vector3f(0, 0,-1),
			new Vector3f(0, 1, 0),
			new Vector3f(0,-1, 0),
			new Vector3f(1, 0, 0),
			new Vector3f(-1, 0, 0)
	};
}
