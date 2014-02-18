package model.entity.top;

import head.Tickable;
import util.ObjectType;
import view.entity.EntityView;
import view.entity.top.TreeView;

/**
 * A class to represent a tree.
 * 
 * @author Simon E
 */
public class Tree extends TopEntity implements Tickable{
	
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
		super(x, y, ObjectType.TREE, true);
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
			// Send update to view
			pcs.firePropertyChange("fruit", false, true);
			fruit = true;
		}
	}
	
	/**
	 * The method to call when the fruit of a tree has been eaten.
	 */
	public void eaten(){
		// Send update to view
		pcs.firePropertyChange("fruit", true, false);
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
	
	/**
	 * Creates and returns a new TreeView.
	 * Registers the view as our listener.
	 */
	@Override
	public EntityView createView() {
		EntityView view = new TreeView(x, y);
		pcs.addPropertyChangeListener(view);
		return view;
	}
	
}
