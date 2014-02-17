package model.objects;

import resource.ObjectType;
import head.Tickable;
import model.entity.TopLayerEntity;

/**
 * A class to represent a tree.
 * 
 * @author Simon E
 */
public class Tree extends TopLayerEntity implements Tickable{
	
	private final int FRUIT_REGROWTH = 5000;
	private int timer = 0;
	private boolean fruit = true;
	
	/**
	 * The constructor which initialises all the necessary things for a tree.
	 * 
	 * @param tileX	The column in which the tree will stand in.
	 * @param tileY The row in which the tree will stand in.
	 */
	public Tree(int x, int y) {
		super("tree2", x, y, ObjectType.TREE);
		updatePos(x-1, y-1);
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
	 * A method to check if the tree has fruit or not.
	 * 
	 * @return true if the tree has fruit to be harvested, false otherwise.
	 */
	public boolean hasFruit() {
		return fruit;
	}
	
}
