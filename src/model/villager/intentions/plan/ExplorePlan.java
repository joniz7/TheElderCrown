package model.villager.intentions.plan;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

import model.path.FindEntity;
import model.path.PathFinder;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.IdleAction;
import model.villager.intentions.action.MoveAction;

import org.newdawn.slick.util.pathfinding.Path;

import util.Constants;

public class ExplorePlan extends Plan {
	
	private Path path = null;
	private Point p;
	private int stacks;
	
	public ExplorePlan(Villager villager){
		super(villager, "Needs to explore");
		actionQueue = new LinkedList<Action>();
		villager.updateStatus("exploring");
		
		stacks = 0;
		while(path == null && !isFinished){
			stacks++;
			if(stacks > 100){
				addAction(new IdleAction(villager));
				isFinished = true;
				villager.updateStatus("statusEnd");
			}

			p = new Point(randInt((villager.getX()-5),(villager.getX()+5)),randInt((villager.getY()-5),(villager.getY()+5)));
			if(p.x > Constants.WORLD_WIDTH) {
				p.x = Constants.WORLD_WIDTH - 1;
			} else if(p.x < 0) {
				p.x=0;
			}
			
			if(p.y > Constants.WORLD_HEIGHT) {
				p.y = Constants.WORLD_HEIGHT - 1;
			} else if(p.y < 0) {
				p.y = 0;
			}
			
			if(p != null && p.x != villager.getX() && p.y != villager.getY()){
				path = PathFinder.getPathToAdjacent(villager.getX(),villager.getY(),p.x,p.y);
			}

		}
		
		addAction(new MoveAction(villager, path));
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

	@Override
	public void retryAction() {
		// TODO Auto-generated method stub
		
	}
}