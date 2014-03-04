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
		while(path == null){
			System.out.println("Finding point to explore");
			p = new Point(randInt((villager.getX()-5),(villager.getX()+5)),randInt((villager.getY()-5),(villager.getY()+5)));
			if(p != null){
				path = PathFinder.getPathToAdjacent(villager.getX(),villager.getY(),p.x,p.y);
			}

		}
			actionQueue.add(new MoveAction(villager, path));
		}
	
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    if(max<=0){
	    	max = 1;
	    }else if(min <=0){
	    	min=1;
	    }
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    if(randomNum<1){
	    	return 1;
	    }

	    return randomNum;
		
	}
}