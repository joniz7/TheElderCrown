package model.item;

import org.newdawn.slick.Image;

import util.ImageLoader;

public abstract class Item {

	protected String name;
	
	public Item(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
	
	
}
