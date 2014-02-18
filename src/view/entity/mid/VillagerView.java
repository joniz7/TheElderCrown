package view.entity.mid;

import view.entity.EntityView;

/**
 * The view representation of a villager.
 * 
 * @author Niklas
 */
public class VillagerView extends EntityView {

	/**
	 * Creates a new VillagerView.
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public VillagerView(int x, int y) {
		super("villager", x, y);
	}
	
}
