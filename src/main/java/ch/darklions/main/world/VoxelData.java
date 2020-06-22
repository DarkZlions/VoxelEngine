package ch.darklions.main.world;

import org.joml.Vector3f;

public class VoxelData {
	
	public static final int CHUNK_WIDTH = 16;
	public static final int CHUNK_HEIGHT = 8;

	public final static float[][] VOXEL_VERTICES = {
			// V0
            {-0.5f, 0.5f, 0.5f,},
            // V1
            {-0.5f, -0.5f, 0.5f,},
            // V2
            {0.5f, -0.5f, 0.5f,},
            // V3
            {0.5f, 0.5f, 0.5f,},
            // V4
            {-0.5f, 0.5f, -0.5f,},
            // V5
            {0.5f, 0.5f, -0.5f,},
            // V6
            {-0.5f, -0.5f, -0.5f,},
            // V7
            {0.5f, -0.5f, -0.5f,},
            
            // For text coords in top face
            // V8: V4 repeated
            {-0.5f, 0.5f, -0.5f,},
            // V9: V5 repeated
            {0.5f, 0.5f, -0.5f,},
            // V10: V0 repeated
            {-0.5f, 0.5f, 0.5f,},
            // V11: V3 repeated
            {0.5f, 0.5f, 0.5f,},
            
            // For text coords in right face
            // V12: V3 repeated
            {0.5f, 0.5f, 0.5f,},
            // V13: V2 repeated
            {0.5f, -0.5f, 0.5f,},
            
            // For text coords in left face
            // V14: V0 repeated
            {-0.5f, 0.5f, 0.5f,},
            // V15: V1 repeated
            {-0.5f, -0.5f, 0.5f,},
            
            // For text coords in bottom face
            // V16: V6 repeated
            {-0.5f, -0.5f, -0.5f,},
            // V17: V7 repeated
            { 0.5f, -0.5f, -0.5f,},
            // V18: V1 repeated
            {-0.5f, -0.5f, 0.5f,},
            // V19: V2 repeated
            {0.5f, -0.5f, 0.5f,},
	};
	public final static float[][] TEXTURE_POSITIONS = { 
            {0.0f, 0.0f,},
            {0.0f, 0.5f,},
            {0.5f, 0.5f,},
            {0.5f, 0.0f,},
            
            {0.0f, 0.0f,},
            {0.5f, 0.0f,},
            {0.0f, 0.5f,},
            {0.5f, 0.5f,},
            
            // For text coords in top face
            {0.0f, 0.5f,},
            {0.5f, 0.5f,},
            {0.0f, 1.0f,},
            {0.5f, 1.0f,},
            
            // For text coords in right face
            {0.0f, 0.0f,},
            {0.0f, 0.5f,},
            
            // For text coords in left face
            {0.5f, 0.0f,},
            {0.5f, 0.5f,},
            
            // For text coords in bottom face
            {0.5f, 0.0f,},
            {1.0f, 0.0f,},
            {0.5f, 0.5f,},
            { 1.0f, 0.5f,},
            
	};
	public final static int[][] VOXEL_INDICES = {
            // Front face
            {0, 1, 3, 3, 1, 2,},
            // Top Face
            {8, 10, 11, 9, 8, 11,},
            // Right face
            {12, 13, 7, 5, 12, 7,},
            // Left face
            {14, 15, 6, 4, 14, 6,},
            // Bottom face
            {16, 18, 19, 17, 16, 19,},
            // Back face
            {4, 6, 7, 5, 4, 7,},
	};
	
	public static final Vector3f[] FACE_SIDE_CHECKS = {
			new Vector3f(0, 0, -1.0f),
			new Vector3f(0, 0,  1.0f),
			new Vector3f(0, 1.0f,  0),
			new Vector3f(0, -1.0f, 0),
			new Vector3f(-1.0f, 0, 0),
			new Vector3f(1.0f, 0,  0),
	};
}
