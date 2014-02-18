package view.entity.bot;

import view.entity.EntityView;

/**
 * The view representation of a grass tile.
 * 
 * @author Niklas
 */
public class GrassTileView extends EntityView {

	/**
	 * Creates a new GrassTileView.
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public GrassTileView(int x, int y) {
		super("grass", x, y);
	}
	
}
