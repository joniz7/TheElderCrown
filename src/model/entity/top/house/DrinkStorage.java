package model.entity.top.house;

import java.util.ArrayList;

import model.item.liquid.Drink;
import model.item.liquid.DrinkSource;
import model.item.liquid.WaterBowl;
import util.EntityType;

/**
 * The class to represent a storage.
 * 
 * @author Simon E
 *
 */
public class DrinkStorage extends HousePart implements DrinkSource{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Drink> drink = new ArrayList<Drink>();
	private boolean isShowUI;
	
	/**
	 * The general constructor.
	 * 
	 * @param x the x-coordinate of the storage.
	 * @param y the y-coordinate of the storage.
	 */
	public DrinkStorage(int x, int y) {
		super(x, y, EntityType.DRINK_STORAGE, true);
		for(int i = 0; i < 10; i++)
			drink.add(new WaterBowl());
	}
	
	@Override
	public DrinkStorage copy() {
		return new DrinkStorage(x,y);
	}
	
	public void addDrink(Drink newDrink){
		drink.add(newDrink);
		pcs.firePropertyChange("status", drink.size(), "drinks");
	}
	
	public Drink getDrink(){
		if(drink.size() > 0){
			Drink toReturn = drink.get(0);
			drink.remove(0);
			pcs.firePropertyChange("status", drink.size(), "drinks");
			return toReturn;
		}
		return null;
	}

	/**
	 * Method for showing or hiding the UI for this Drink Storage
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
			pcs.firePropertyChange("status", drink.size(), "drinks");
		}
	}

	@Override
	public boolean hasDrink() {
		return drink.size() > 0;
	}
	
}
