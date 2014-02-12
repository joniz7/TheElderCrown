package resource;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static synchronized BufferedImage loadImage(String folder, String name){
		BufferedImage img = null;
		try {
			img = ImageIO.read(ImageLoader.class.getResource(folder + "/" + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

}