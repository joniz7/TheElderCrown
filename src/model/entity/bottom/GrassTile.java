package model.entity.bottom;

import util.EntityType;
import view.entity.EntityView;
import view.entity.bot.GrassTileView;

public class GrassTile extends Tile{

	public GrassTile(int x, int y) {
		super(x ,y, EntityType.GRASS_TILE);
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