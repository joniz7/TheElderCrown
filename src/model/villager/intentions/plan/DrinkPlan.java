package model.villager.intentions.plan;

import java.awt.Point;
import java.util.LinkedList;

import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasDrink;
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
		
		Point p = FindObject.findObjectNeighbour(villager.getWorld(), EntityType.WATER_TILE, 
				villager.getX(), villager.getY());
		Point p2 = FindObject.findObjectNeighbour(villager.getWorld(), new HasDrink(), null,
				villager.getX(), villager.getY());
		
		if(p!=null){
			Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
			Path movePath2;
			if(p2 != null){
				movePath2 = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p2.x, p2.y);
				if(movePath2.getLength() >= movePath.getLength()){
					actionQueue.add(new MoveAction(villager, movePath));
				}else if(movePath.getLength() > movePath2.getLength()){
					actionQueue.add(new MoveAction(villager, movePath2));
				}
				actionQueue.addLast(new DrinkAction(villager));
				return;
			}
		}else if(p2 != null){
			Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p2.x, p2.y);
			actionQueue.add(new MoveAction(villager, movePath));
			actionQueue.addLast(new DrinkAction(villager));
		}
		else{
			villager.setExplore();
			isFinished=true;
		}
	}
}
