package model.item.liquid;

import model.item.Item;

public class Drink extends Item{

	protected int drinkTicks;
	protected float saturation;
	
	public float drunk(){
		drinkTicks--;
		return saturation;
	}
	
	public boolean consumed(){
		if(drinkTicks <= 0)
			return true;
		return false;
	}
	
}
