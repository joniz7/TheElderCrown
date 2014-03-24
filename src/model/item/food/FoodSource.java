package model.item.food;

import model.path.criteria.HasFood;

public interface FoodSource{

	public Food getFood(); 
	public boolean hasFood();
	
}
