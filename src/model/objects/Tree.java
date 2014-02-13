package model.objects;

import head.Tickable;
import model.entity.TopLayerGraphicalEntity;

/**
 * A class to represent a tree.
 * 
 * @author Simon E
 */
public class Tree extends TopLayerGraphicalEntity implements Tickable{
	
	private int fruitRegTime = 5000, timer = 0;
	private boolean fruit = true;
	private int tileX, tileY;
	
	/**
	 * The constructor which initialises all the necessary things for a tree.
	 * 
	 * @param tileX	The column in which the tree will stand in.
	 * @param tileY The row in which the tree will stand in.
	 */
	public Tree(int tileX, int tileY) {
		super("tree2");
		this.tileX = tileX;
		this.tileY = tileY;
		updatePos((tileX * 20) - 20, (tileY * 20) - 20);
	}

	@Override
	/**
	 * The method to 'tick' a tree in order for it to generate fruit.
	 */
	public void tick() {
		timer = timer + 1;
		if(fruit)
			timer = 0;
		if(timer > fruitRegTime){
			drawable.setImage("tree2");
			fruit = true;
		}
	}
	
	/**
	 * The method to call when the fruit of a tree has been eaten.
	 */
	public void eaten(){
		drawable.setImage("tree");
		fruit = false;
	}
	
	/**
	 * A method to find the column of the tree.
	 * 
	 * @return the column in which the tree is.
	 */
	public int getTileX() {
		return tileX;
	}

	/**
	 * A method to find the row of the tree.
	 * 
	 * @return the row in which the tree is.
	 */
	public int getTileY() {
		return tileY;
	}

	/**
	 * A method to check if the tree has fruit or not.
	 * 
	 * @return true if the tree has fruit to be harvested, false otherwise.
	 */
	public boolean hasFruit() {
		return fruit;
	}
	
}
