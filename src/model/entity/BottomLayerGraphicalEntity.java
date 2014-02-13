package model.entity;

import view.View;

/**
 * A class representing all objects visible and below the villagers.
 * 
 * @author Teodor O
 *
 */
public class BottomLayerGraphicalEntity extends GraphicalEntity {
	
	private int tileID;

	/**
	 * General constructor for a graphical entity.
	 * 
	 * @param name The name of the image to be associated with this object.
	 */
	public BottomLayerGraphicalEntity(String name, int x, int y) {
		super(name, x, y);
		View.addBotGraphic(drawable);
	}
	
	public void setTileID(int id){
		tileID = id;
	}
	
	public int getTileID(){
		return tileID;
	}

}
