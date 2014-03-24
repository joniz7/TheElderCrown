package model.item.food;

import model.item.Item;

public class Food extends Item{

	protected int foodTicks;
	protected float saturation;
	
	public Food(String name){
		super(name);
	}
	
	public float eaten(){
		foodTicks--;
		return saturation;
	}
	
	public boolean consumed(){
		if(foodTicks <= 0)
			return true;
		return false;
	}
	
}
