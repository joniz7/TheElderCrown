package util;

import java.util.Random;

/**
 * Small class to provide various useful utilities not needing its own class.
 *
 *
 */

public class UtilClass {

	private static Random rnd = new Random();
	
	public static int getRandomInt(int dice, int base){
		return rnd.nextInt(dice) + base;
	}
	
	
	public static int findMax (int... args) { 
		int max = -80; 
		for (int arg: args){ 
			if (arg > max){ 
				max = arg; 
				} 
		} 
		return max; 
	}
	
	public static float findMax (float... args) { 
		float max = -80f; 
		for (float arg: args){ 
			if (arg > max){ 
				max = arg; 
				} 
		} 
		return max; 
	}
	
	public static int findMin (int... args) { 
		int min = 80; 
		for (int arg: args){ 
			if (arg < min){ 
				min = arg; 
				} 
		} 
		return min; 
	}
	
	public static float findMin (float... args) { 
		float min = 80f; 
		for (float arg: args){ 
			if (arg < min){ 
				min = arg; 
				} 
		} 
		return min; 
	}

	
}
