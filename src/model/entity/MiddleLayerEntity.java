package model.entity;

import resource.ObjectType;
import view.View;

/**
 * A class representing all objects visible and at the same height as the villagers.
 * 
 * @author Teodor O
 *
 */
public class MiddleLayerEntity extends Entity {

	/**
	 * General constructor for a graphical entity.
	 * 
	 * @param name The name of the image to be associated with this object.
	 */
	public MiddleLayerEntity(String name, int x, int y, ObjectType id) {
		super(name, x, y, id);
		View.addMidGraphic(drawable);
	}

}
