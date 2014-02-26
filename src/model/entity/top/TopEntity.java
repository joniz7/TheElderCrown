package model.entity.top;

import model.entity.Entity;
import util.EntityType;

/**
 * A class representing all objects visible and above the villagers.
 * 
 * @author Teodor O
 *
 */
public abstract class TopEntity extends Entity {

	/**
	 * General constructor for a graphical entity.
	 * 
	 * @param name The name of the image to be associated with this object.
	 */

	public TopEntity(int x, int y, EntityType id) {
		super(x, y, id);
		// View.addTopGraphic(drawable);
	}


}
