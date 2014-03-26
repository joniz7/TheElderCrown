package view.ui.icons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import view.ui.UI;

public class ItemIcon extends UI{

	protected Image image;
	
	public ItemIcon(String name) {
		super(name);
	}

	public void draw(int x, int y, Graphics g, Image emptyIcon){
		if(image != null)
			g.drawImage(image, x, y);
		else
			g.drawImage(emptyIcon, x, y);
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	

	
}
