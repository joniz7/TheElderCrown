package model.villager.intentions;

import java.awt.Point;
import java.util.LinkedList;

import model.TestWorld;
import model.path.FindObject;
import model.path.PathFinder;
import model.villager.Villager;
import java.util.Random;

import org.newdawn.slick.util.pathfinding.Path;


public class ExplorePlan extends Plan {
	
	private Path path = null;
	private Point p;
	
	public ExplorePlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		while(path == null || p == null){
			p = new Point(randInt((villager.getX()-10000),(villager.getY()-10000)),randInt((villager.getX()-10000),(villager.getY()-10000)));
			if(p != null){
				path = PathFinder.getPathToAdjacent(villager.getX(),villager.getY(),p.x,p.y);
			}

		}
			actionQueue.add(new MoveAction(villager, path));
		}
	
	public static int randInt(int min, int max) {

	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
		
	}
}