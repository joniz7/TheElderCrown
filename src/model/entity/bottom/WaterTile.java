package model.entity.bottom;

import util.EntityType;
import view.entity.EntityView;
import view.entity.bot.WaterTileView;

public class WaterTile extends Tile{

	public WaterTile(int x, int y) {
		super(x, y, EntityType.WATER_TILE);
	}

	/**
	 * Creates and returns a new WaterTileView.
	 * Registers the view as our listener.
	 */
	@Override
	public EntityView createView() {
		EntityView view = new WaterTileView(x, y);
		pcs.addPropertyChangeListener(view);
		return view;
	}
	
}
