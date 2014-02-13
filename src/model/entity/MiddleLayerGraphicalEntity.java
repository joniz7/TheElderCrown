package model.entity;

import resource.ObjectID;
import view.View;

/**
 * A class representing all objects visible and at the same height as the villagers.
 * 
 * @author Teodor O
 *
 */
public class MiddleLayerGraphicalEntity extends GraphicalEntity {

	/**
	 * General constructor for a graphical entity.
	 * 
	 * @param name The name of the image to be associated with this object.
	 */
	public MiddleLayerGraphicalEntity(String name, int x, int y, ObjectID id) {
		super(name, x, y, id);
		View.addMidGraphic(drawable);
	}

}
