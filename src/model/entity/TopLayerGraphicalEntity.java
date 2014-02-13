package model.entity;

import resource.ObjectID;
import view.View;

/**
 * A class representing all objects visible and above the villagers.
 * 
 * @author Teodor O
 *
 */
public class TopLayerGraphicalEntity extends GraphicalEntity {

	/**
	 * General constructor for a graphical entity.
	 * 
	 * @param name The name of the image to be associated with this object.
	 */
	public TopLayerGraphicalEntity(String name, int x, int y, ObjectID id) {
		super(name, x, y, id);
		View.addTopGraphic(drawable);
	}

}
