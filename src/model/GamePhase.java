package model;

import java.util.ArrayList;

import head.Tickable;

public abstract class GamePhase implements Tickable{

	protected ArrayList<Tickable> tickables = new ArrayList<Tickable>();
	protected int viewX, viewY;
	protected boolean paused;
	protected final int SCROLL_SPEED = 8;
	
	@Override
	public void tick(){
		if(!paused)
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
		viewX += SCROLL_SPEED;
	}
	
	public void incViewY(){
		viewY += SCROLL_SPEED;
	}
	
	public void decViewX(){
		viewX -= SCROLL_SPEED;
	}
	
	public void decViewY(){
		viewY -= SCROLL_SPEED;
	}

	public void setViewX(int viewX) {
		this.viewX = viewX;
	}

	public void setViewY(int viewY) {
		this.viewY = viewY;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	
	

}
