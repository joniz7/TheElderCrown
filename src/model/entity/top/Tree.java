package model.entity.top;

import model.item.food.Apple;
import model.item.food.FoodSource;
import util.EntityType;
import util.Tickable;

/**
 * A class to represent a tree.
 * 
 * @author Simon E
 */
public class Tree extends TopEntity implements Tickable, FoodSource{
	
	private static final long serialVersionUID = 1L;
	private final int FRUIT_REGROWTH = 5000;
	private int timer = 0;
	private boolean fruit = true, isShowUI = false;
	private int fruitCount = 15;
	
	/**
	 * The constructor which initialises all the necessary things for a tree.
	 * 
	 * @param tileX	The column in which the tree will stand in.
	 * @param tileY The row in which the tree will stand in.
	 */
	public Tree(int x, int y) {
		super(x, y, EntityType.TREE);
		// this makes the TreeView show in the right position
		updatePos(x, y);
	}

	@Override
	/**
	 * The method to 'tick' a tree in order for it to generate fruit.
	 */
	public void tick() {
		timer = timer + 1;
		if(fruit)
			timer = 0;
		if(timer > FRUIT_REGROWTH){
			// Send update to view
			pcs.firePropertyChange("fruit", false, true);
			fruit = true;
			fruitCount = 15;
		}
		updateUI();
	}
	
	/**
	 * The method to call when the fruit of a tree has been eaten.
	 */
	public Apple getFood(){
		// Send update to view
		fruitCount--;
		if(fruitCount <= 0){
			pcs.firePropertyChange("fruit", true, false);
			fruit = false;
			timer = 0;
		}
		return new Apple();
	}

	/**
	 * Method for showing or hiding the UI for this Tree
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
			pcs.firePropertyChange("status", fruitCount, "fruit");
			
			float regrowth = -80;
			regrowth += (timer / (FRUIT_REGROWTH / 160));
			
			pcs.firePropertyChange("status", regrowth, "regrowth");
		}
	}
	
	/**
	 * A method to check if the tree has fruit or not.
	 * 
	 * @return true if the tree has fruit to be harvested, false otherwise.
	 */
	public boolean hasFood() {
		return fruitCount > 0;
	}
	
	@Override
	public Tree copy() {
		Tree copy = new Tree(x, y);
		copy.timer = timer;
		copy.fruit = fruit;
		copy.fruitCount = fruitCount;
		return copy;
		
	}
	
}
