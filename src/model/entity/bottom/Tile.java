package model.entity.bottom;

import util.EntityType;

/**
 * A representation of a square of the ground.
 * @author Teodor O
 *
 */
public abstract class Tile extends BottomEntity{
	
	public Tile(int x, int y, EntityType id) {
		super(x ,y, id);
		updatePos(x,y);
	}
	
	public Tile(int x, int y, EntityType id, boolean isBlocking) {
		super(x ,y, id, isBlocking);
		updatePos(x,y);
	}
}
