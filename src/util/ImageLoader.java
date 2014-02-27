package util;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * A class to be used to load images using the Slick2D-architectire.
 * @author Simon E
 *  
 */
public class ImageLoader {
	
	private static HashMap<String, Image> imageLib = new HashMap<String, Image>();
	
	public ImageLoader(){
		// Tiles
		imageLib.put("grass", loadImage("image", "grass.png"));
		imageLib.put("water", loadImage("image", "water.png"));
		
		// World Entities
		imageLib.put("floor", loadImage("image", "floor.png"));
		imageLib.put("door", loadImage("image", "door.png"));
		imageLib.put("wall", loadImage("image", "wall.png"));
		imageLib.put("tree", loadImage("image", "tree.png"));
		imageLib.put("tree2", loadImage("image", "tree2.png"));
		
		//Villager
		imageLib.put("villager", loadImage("image", "villager.png"));
		imageLib.put("villagersleeping", loadImage("image", "villagersleeping.png"));
		
		//Debugging
		imageLib.put("helper", loadImage("image", "miss.png"));
		imageLib.put("helper2", loadImage("image", "hit.png"));
		imageLib.put("helper3", loadImage("image", "checker.png"));
	}
	
	public static Image getImage(String name){
		return imageLib.get(name);
	}
	
	private static synchronized Image loadImage(String folder, String name){
		Image img = null;
		try {
			img = new Image("resource/" + folder + "/" + name);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		System.out.println(folder + "/" + name + "resulted in " + img);
		return img;
	}

}