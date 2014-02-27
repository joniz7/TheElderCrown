package model.entity.bottom;

import util.EntityType;

/**
 * A representation of a square of the ground.
 * @author Teodor O
 *
 */
public abstract class Tile extends BottomEntity{
	
	public Tile(int x, int y, EntityType id, boolean blocking) {
		super(x ,y, id, blocking);
		updatePos(x,y);
	}
}
