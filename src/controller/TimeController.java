package controller;

import java.util.Timer;
import java.util.TimerTask;

import model.World;

public class TimeController extends Timer{
	
	private World world;
	
	public TimeController(World world){
		this.world=world;
	}
	public void run() {
		world.tick();
		
	}

}
