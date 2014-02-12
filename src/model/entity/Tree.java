package model.entity;

import head.Tickable;

import java.awt.Image;

import model.TestWorld;
import resource.ImageLoader;

public class Tree extends GraphicalEntity implements Tickable{
	
	private int fruitRegTime = 5000, timer = 0;
	private boolean fruit = true;
	private int tileX, tileY;
	private static TestWorld world;
	
	public Tree(int tileX, int tileY, TestWorld world) {
		super("tree2");
		this.tileX = tileX;
		this.tileY = tileY;
		updatePos((tileX * 20) - 20, (tileY * 20) - 20);
		Tree.world = world;
	}

	@Override
	public void tick() {
		timer = timer + 1;
		if(fruit)
			timer = 0;
		if(timer > fruitRegTime){
			drawable.setImage("tree2");
			world.setObjectIndexID(tileX, tileY, 101);
			fruit = true;
		}
		
	}
	
	public void eaten(){
		drawable.setImage("tree");
		world.setObjectIndexID(tileX, tileY, 102);
		fruit = false;
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public boolean isFruit() {
		return fruit;
	}
	
	
	
}
