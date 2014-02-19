package model.entity;

import util.EntityType;
import view.View;
import view.entity.EntityView;

/**
 * A class representing all objects visible and at the same height as the villagers.
 * 
 * @author Teodor O
 *
 */
public abstract class MidEntity extends Entity {

	/**
	 * General constructor for a graphical entity.
	 * 
	 * @param name The name of the image to be associated with this object.
	 */
	public MidEntity(int x, int y, EntityType id) {
		super(x, y, id);
//		View.addMidGraphic(drawable);
	}

}
