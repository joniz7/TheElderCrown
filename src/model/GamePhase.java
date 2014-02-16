package model;

import java.util.ArrayList;

import head.Tickable;

public abstract class GamePhase implements Tickable{

	protected ArrayList<Tickable> tickables = new ArrayList<Tickable>();
	protected int viewX, viewY;
	protected boolean paused;
	
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
