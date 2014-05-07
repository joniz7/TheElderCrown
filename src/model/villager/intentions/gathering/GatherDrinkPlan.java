package model.villager.intentions.gathering;

import java.awt.Point;
import java.util.LinkedList;

import model.path.FindEntity;
import model.path.PathFinder;
import model.path.criteria.IsDrinkStorage;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.MoveAction;
import model.villager.intentions.plan.Plan;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class GatherDrinkPlan extends Plan{

	public GatherDrinkPlan(Villager villager){
		super(villager, "Gathers drink");
		actionQueue = new LinkedList<Action>();
		
		villager.clearInventory();
		
		boolean water = FindEntity.isAdjacentTile(villager.getWorld() ,
				EntityType.WATER_TILE, villager.getX(), villager.getY());

		// We're next to a tree. fill Inventory!
		if(water){
			actionQueue.addLast(new GatherDrinkAction(villager));
			actionQueue.addLast(new MoveAction(villager, null, new IsDrinkStorage()));
			actionQueue.addLast(new StoreDrinkAction(villager));
		}
		// We need to move, and then fill Inventory
		else{
			Point p = FindEntity.findBotEntityNeighbour(villager.getWorld(), EntityType.WATER_TILE, 
					villager.getX(), villager.getY());
			Path movePath = null;
			if(p != null){
				movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
			}else{
				villager.setExplore();
				isFinished=true;
			}

			actionQueue.add(new MoveAction(villager, movePath));
			actionQueue.addLast(new GatherDrinkAction(villager));
			actionQueue.addLast(new MoveAction(villager, null, new IsDrinkStorage()));
			actionQueue.addLast(new StoreDrinkAction(villager));
		}
		
	}

	@Override
	public void retryAction() {
		throw new UnsupportedOperationException("IPA refactoring not done");
	}
}
