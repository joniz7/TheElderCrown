package view.entity.bot;

import util.UtilClass;
import view.entity.EntityView;

/**
 * The view representation of a grass tile.
 * 
 * @author Niklas
 */
public class GrassTileView extends EntityView {

	/**
	 * Creates a new GrassTileView.
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public GrassTileView(int x, int y) {
		super(tileType(), x, y);		
	}
	
	private static String tileType(){
		int rnd = UtilClass.getRandomInt(200, 0);
		if(rnd==20){
			return "redFlowerGrass";
		}else if(rnd==40){
			return "purpleFlowerGrass";
		}
		return "grass";
	}
	
}
