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
	private boolean isShowUI;
	
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
		pcs.firePropertyChange("status", food.size(), "fruit");
	}
	
	public Food getFood(){
		if(food.size() > 0){
			Food toReturn = food.get(0);
			food.remove(0);
			pcs.firePropertyChange("status", food.size(), "fruit");
			return toReturn;
		}
		return null;
	}

	/**
	 * Method for showing or hiding the UI for this Food Storage
	 * 
	 * @param show - true if the UI should be shown
	 */
	public void setShowUI(boolean show){
		if(show){
			pcs.firePropertyChange("status", null, "show");
		}else{
			pcs.firePropertyChange("status", null, "hide");	
		}
		isShowUI = show;
	}
	
	public void updateUI(){
		if(isShowUI){
			pcs.firePropertyChange("status", food.size(), "fruit");
		}
	}
	
}
