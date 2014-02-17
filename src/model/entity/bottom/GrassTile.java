package model.entity.bottom;

import util.ObjectType;
import view.entity.EntityView;
import view.entity.bot.GrassTileView;

public class GrassTile extends Tile{

	public GrassTile(int x, int y) {
		super(x ,y, ObjectType.GRASS_TILE);
		typeID = 0;		
	}

	/**
	 * Creates and returns a new GrassTileView.
	 * Registers the view as our listener.
	 */
	@Override
	public EntityView createView() {
		EntityView view = new GrassTileView(x, y);
		pcs.addPropertyChangeListener(view);
		return view;
	}
}