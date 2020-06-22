package ch.darklions.voxelengine.graph;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture 
{
	private final int id;
	
	public Texture(String fileName) throws Exception
	{
		this(loadTexture(fileName));
	}
	
	public Texture(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	private static int loadTexture(String fileName) throws Exception
	{
		int width;
		int height;
		
		ByteBuffer buffer;
		
		//Load texture file
		try(MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer channels = stack.mallocInt(1);
			
			buffer = stbi_load(fileName, w, h, channels, 4);
			
			if(buffer == null)	{
				throw new Exception("Image file [" + fileName + "] not loaded: " + stbi_failure_reason());
			}
			
			// Get width and height of image
			width = w.get();
			height = h.get();
		}
		
		//Create a new OpenGL texture
		int textureId = glGenTextures();
		
		//Store the texture identifier in the GPU
		glBindTexture(GL_TEXTURE_2D, textureId);
		
		
        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching the image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // When shrinking an image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		//Tell OpenGL how to unpack the RGBA bytes.
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		
		//Upload the texture data
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width,
				height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		/*
		 * glTexImage2D parameters:
		 * target: Specifies the target texture(type). In this case : GL_TEXTURE_2D.
		 * 
		 * level: Specifies the level of detail number. Level 0 is the base image level. Level n is the
		 * nth mipmap reduction image.
		 * 
		 * internal format: Specifies the number of colour components in the texture.
		 * 
		 * width: Specifies the width of the texture.
		 * 
		 * height: Specifies the height of the texture image.
		 * 
		 * border: This value must be zero
		 * 
		 * format: Specifies the format of the pixel data: RGBA is this case.
		 * 
		 * type: Specifies the data type of the pixel data.
		 * 
		 * data: the buffer that stores the data.
		 */
		
		glGenerateMipmap(GL_TEXTURE_2D);
		/*
		 * Mipmap is decreasing resolution of a set of high detailed texture. Those lower resolution images
		 * will be used automatically when the object is scaled.
		 */
		return textureId;
	}
	
	public void cleanup()
	{
		glDeleteTextures(id);
	}
}
