package view;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import resource.ImageLoader;

public class Drawable{

	private Image image;
	private int worldX, worldY;
	private String name;
	
	public Drawable(String name){
		image = ImageLoader.getImage(name);
		this.name = name;
	}
	
	public Drawable(Image image){
		this.image = image;
	}
	
	public boolean draw(Graphics g, int viewX, int viewY, int width, int height){
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		int transposedX = worldX - viewX;
		int transposedY = worldY - viewY;
			
		if(transposedX + imageWidth > 0 && transposedX < width)
			if(transposedY + imageHeight > 0 && transposedY < height){
				g.drawImage(image, transposedX, transposedY);
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

	public void setImage(String name) {
		image = ImageLoader.getImage(name);
	}
	
	
	
}
