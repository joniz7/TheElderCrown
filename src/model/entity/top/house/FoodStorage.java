package model.entity.top.house;

import java.util.ArrayList;

import model.item.food.Food;
import util.EntityType;

/**
 * The class to represent a storage.
 * 
 * @author Simon E
 *
 */
public class FoodStorage extends HousePart {
	
	private ArrayList<Food> food = new ArrayList<Food>();
	
	/**
	 * The general constructor.
	 * 
	 * @param x the x-coordinate of the storage.
	 * @param y the y-coordinate of the storage.
	 */
	public FoodStorage(int x, int y) {
		super(x, y, EntityType.FOOD_STORAGE, true);
	}
	
	@Override
	public FoodStorage copy() {
		return new FoodStorage(x,y);
	}
	
	public void addFood(Food newFood){
		food.add(newFood);
	}
	
	public Food getFood(){
		if(food.size() > 0)
			return food.get(0);
		return null;
	}

}
