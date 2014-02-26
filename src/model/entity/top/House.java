package model.entity.top;

import util.EntityType;

/**
 * The class representing a house in which villagers sleep.
 * 
 * @author Teodor O
 *
 */
public class House extends TopEntity {
	
	private float orientation;

	/**
	 * The constructor which creates the house and orients it properly.
	 * 
	 * @param x the x-coordinate of the top left corner of the house.
	 * @param y the y-coordinate of the top left corner of the house.
	 * @param orientation an integer that specifies what direction the entrance will be oriented in.
	 */
	public House(int x, int y, float orientation) {

		super(x, y, EntityType.HOUSE);
		this.orientation = orientation;
		updatePos(x-1, y-1);
	}
	
	/**
	 * A getter for the housese orientation.
	 * @return the float specifying the angle of the house in degrees.
	 */
	public float getOrientation(){
		return orientation;
	}
}
