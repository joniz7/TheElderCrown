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
	
	public int findMax (int[] numbers) { 
		int max = numbers[0]; 
		for (int i = 1; i < numbers.length; i++){ 
			if (numbers[i] > max){ 
				max = numbers [i]; 
				} 
		} 
		return max; 
	} 

	
}
