package ch.darklions.voxelengine;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Utils 
{
	public String loadResource(String fileName) throws Exception
	{
		String result;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
	}
	
	public static String getResourceLocation(String fileName) {
		
        try {
			return "src/main/resources/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static double clampValue(double value, double min, double max)
	{
		return Math.max(min, Math.min(max, value));
	}
	
	public static int getRandomMinusOrPlus() {
		Random rand = new Random();
		return rand.nextInt(2) == 0 ? -1 : 1;
	}
	
	public static <E extends Object> E[] ListToArray(List<E> listIn) {
		E[]	newArray = (E[]) new Object[listIn.size()];
		
		for(int i = 0; i < newArray.length; i++) {
			newArray[i] = listIn.get(i);
		}
		
		return newArray;
	}
}
