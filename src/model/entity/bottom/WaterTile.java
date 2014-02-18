package model.entity.bottom;

import util.ObjectType;
import view.entity.EntityView;
import view.entity.bot.WaterTileView;

public class WaterTile extends Tile{

	public WaterTile(int x, int y) {
		super(x, y, ObjectType.WATER_TILE, true);
		typeID = 1;
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
