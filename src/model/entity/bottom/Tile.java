package model.entity.bottom;

import util.EntityType;

/**
 * A representation of a square of the ground.
 * @author Teodor O
 *
 */
public abstract class Tile extends BottomEntity{

	private static final long serialVersionUID = 1L;

	public Tile(int x, int y, EntityType id) {
		super(x ,y, id);
		updatePos(x,y);
	}
	
	public Tile(int x, int y, EntityType id, boolean blocking) {
		super(x ,y, id, blocking);
		updatePos(x,y);
	}
	
	@Override
	public float getSleepValue(){
		return 0f;
	}

}
