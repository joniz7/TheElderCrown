package view.entity.bot;

import view.entity.EntityView;

/**
 * The view representation of a water tile.
 * 
 * @author Niklas
 */
public class WaterTileView extends EntityView {

	/**
	 * Creates a new WaterTileView.
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public WaterTileView(int x, int y, int id) {
		super("water", x, y, id);
	}
	
}
