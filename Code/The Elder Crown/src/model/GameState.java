package model;

import java.awt.Graphics;
import java.util.ArrayList;

import view.Drawable;
import head.Tickable;

public abstract class GameState implements Tickable{

	protected ArrayList<Tickable> tickables = new ArrayList<Tickable>();

	protected int viewX = 0, viewY = 0;
	
	@Override
	public void tick() {
		for(Tickable t : tickables){
			t.tick();
		}
	}

	public int getViewX() {
		return viewX;
	}

	public int getViewY() {
		return viewY;
	}
	
	public void incViewX(){
		viewX++;
		viewX++;
	}
	
	public void incViewY(){
		viewY++;
		viewY++;
	}
	
	public void decViewX(){
		viewX--;
		viewX--;
	}
	
	public void decViewY(){
		viewY--;
		viewY--;
	}
	
	
	

}
