package model.entity.top.house;

import model.entity.top.TopEntity;
import util.EntityType;

/**
 * The class representing a house in which villagers sleep.
 * 
 * @author Teodor O
 *
 */
public abstract class HousePart extends TopEntity {
	
	/**
	 * The constructor which creates the house and orients it properly.
	 * 
	 * @param x the x-coordinate of the top left corner of the house.
	 * @param y the y-coordinate of the top left corner of the house.
	 * @param orientation an integer that specifies what direction the entrance will be oriented in.
	 */
	public HousePart(int x, int y, EntityType type, boolean blocking) {
		super(x, y, type, blocking);
		updatePos(x, y);
	}
}
