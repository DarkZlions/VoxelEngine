package ch.darklions.main.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageWriter {
	
	public static void greyWhiteImage(float[][] data) {
		// Takes and array of double between 0 and 1 and generate a grey scale image
		
		BufferedImage image = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_RGB);
		
		for(int y = 0; y < data[0].length; y++) {
			for(int x = 0; x < data.length; x++) {
				Color colour = new Color((float) data[x][y], (float)data[x][y], (float)data[x][y]);
				
				image.setRGB(x, y, colour.getRGB());
				
			}
		}
		
		try {
			File outputFile = new File("saved.png");
			outputFile.createNewFile();
			
			ImageIO.write(image, "png", outputFile);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
