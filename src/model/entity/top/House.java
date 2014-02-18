package model.entity.top;

import util.ObjectType;
import view.entity.EntityView;
import view.entity.bot.GrassTileView;
import model.entity.TopEntity;

public class House extends TopEntity {
	
	public static final int UP_ENTRANCE = 0, RIGHT_ENTRANCE = 1, DOWN_ENTRANCE = 2, LEFT_ENTRANCE = 3; 
	private int orientation;

	public House(int x, int y, int orientation) {
		super(x, y, ObjectType.HOUSE);
		this.orientation = orientation;
		updatePos(x-1, y-1);
	}
	
	/**
	 * Creates and returns a new HouseView.
	 * Registers the view as our listener.
	 */
	@Override
	public EntityView createView() {
		throw new UnsupportedOperationException();
	}
	
}
