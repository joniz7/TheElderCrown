package resource;

import java.util.Random;

public class RandomClass {

	private static Random rnd = new Random();
	
	public static int getRandomInt(int dice, int base){
		return rnd.nextInt(dice) + base;
	}
	
}
