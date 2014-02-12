package resource;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageLoader {
	
	public static synchronized Image loadImage(String folder, String name){
		Image img = null;
		try {
			img = new Image("resource/" + folder + "/" + name);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return img;
	}

}