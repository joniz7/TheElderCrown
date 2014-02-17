package model.entity.bottom;

import model.entity.Entity;
import util.ObjectType;
import view.View;
import view.entity.EntityView;

/**
 * A class representing all objects visible and below the villagers.
 * 
 * @author Teodor O
 *
 */
public abstract class BottomEntity extends Entity {
	
	private int tileID;

	/**
	 * General constructor for a graphical entity.
	 * 
	 * @param name The name of the image to be associated with this object.
	 */
	public BottomEntity(int x, int y, ObjectType id) {
		super(x, y, id);
//		View.addBotGraphic(drawable);
	}
	
	public void setTileID(int id){
		tileID = id;
	}
	
	public int getTileID(){
		return tileID;
	}

}
