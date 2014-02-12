package resource;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageLoader {
	
	private static HashMap<String, Image> imageLib = new HashMap<String, Image>();
	
	public ImageLoader(){
		// Tiles
		imageLib.put("grass", loadImage("image", "grass.png"));
		imageLib.put("water", loadImage("image", "water.png"));
		
		// World Entities
		imageLib.put("hut", loadImage("image", "hut.png"));
		imageLib.put("tree", loadImage("image", "tree.png"));
		imageLib.put("tree2", loadImage("image", "tree2.png"));
		
		//Villager
		imageLib.put("villager", loadImage("image", "villager.png"));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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