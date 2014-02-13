package model.entity;

import view.View;

/**
 * A class representing all objects visible and below the villagers.
 * 
 * @author Teodor O
 *
 */
public class BottomLayerGraphicalEntity extends GraphicalEntity {

	/**
	 * General constructor for a graphical entity.
	 * 
	 * @param name The name of the image to be associated with this object.
	 */
	public BottomLayerGraphicalEntity(String name) {
		super(name);
		View.addBotGraphic(drawable);
	}

}
