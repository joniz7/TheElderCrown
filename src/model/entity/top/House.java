package model.entity.top;

import util.ObjectType;
import view.entity.EntityView;
import view.entity.top.HouseView;

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
		EntityView view = new HouseView(x, y, orientation);
		pcs.addPropertyChangeListener(view);
		return view;
	}
	
}
