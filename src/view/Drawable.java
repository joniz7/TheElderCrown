package view;

import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Image;

import resource.ImageLoader;

public class Drawable{

	private Image image;
	private int worldX, worldY;
	
	public Drawable(String folder, String name){
		image = ImageLoader.loadImage(folder, name);
	}
	
	public Drawable(Image image){
		this.image = image;
	}
	
	public boolean draw(Graphics g, int viewX, int viewY, DisplayMode activeDisplay){
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		
		int transposedX = worldX - viewX;
		int transposedY = worldY - viewY;
			
		if(transposedX + width > 0 && transposedX < activeDisplay.getWidth())
			if(transposedY + height > 0 && transposedY < activeDisplay.getHeight()){
				g.drawImage(image, transposedX, transposedY, null);
				return true;
			}
		return false;
	}

	public void setDrawX(int drawX) {
		this.worldX = drawX;
	}

	public void setDrawY(int drawY) {
		this.worldY = drawY;
	}

	public void setImage(String folder, String name) {
		image = ImageLoader.loadImage(folder, name);
	}
	
	
	
}
