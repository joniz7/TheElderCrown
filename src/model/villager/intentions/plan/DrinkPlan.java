package model.villager.intentions.plan;

import java.awt.Point;
import java.util.LinkedList;

import model.path.FindObject;
import model.path.PathFinder;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.DrinkAction;
import model.villager.intentions.action.MoveAction;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class DrinkPlan extends Plan{

	public DrinkPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		name = "Wants to drink";
		
		Point p = FindObject.findTileNeighbour(villager.getWorld(), EntityType.WATER_TILE, 
				villager.getX(), villager.getY());
		if(p!=null){
			System.out.println(""+p.toString());
			Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
			actionQueue.add(new MoveAction(villager, movePath));
			actionQueue.addLast(new DrinkAction(villager));
		}else{
			villager.setExplore();
			isFinished=true;
		}
	}
}
