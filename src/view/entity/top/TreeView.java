package view.entity.top;

import java.beans.PropertyChangeEvent;

import view.entity.EntityView;

/**
 * The view representation of a tree.
 * 
 * @author Niklas
 */
public class TreeView extends EntityView {

	/**
	 * Creates a new TreeView.
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public TreeView(int x, int y, int id) {
		super("tree2", x, y, id);
	}
	
	@Override
	/**
	 * Is called when our associated tree changes.
	 * May be due to fruit appearing/disappearing. 
	 * 
	 * @author Niklas
	 */
	public void propertyChange(PropertyChangeEvent event) {
		
		String name = event.getPropertyName();
		
		// Fruit has appeared/disappeared from the tree
		if (name.equals("fruit")) {
			boolean hasFruit = (boolean) event.getNewValue();
			changeFruit(hasFruit);
		}
		else { // Not Tree-specific
			super.propertyChange(event);
		}
	}
	
	/**
	 * Changes whether fruits are displayed on the tree or not. 
	 */
	private void changeFruit(boolean hasFruit) {
		if (hasFruit) {
			setImage("tree2");			
		} else {
			setImage("tree");
		}
	}
}
