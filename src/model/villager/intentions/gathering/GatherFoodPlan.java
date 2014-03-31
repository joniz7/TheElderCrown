package model.villager.intentions.gathering;

import java.awt.Point;
import java.util.LinkedList;

import model.entity.top.Tree;
import model.path.FindEntity;
import model.path.PathFinder;
import model.path.criteria.HasFood;
import model.path.criteria.IsFoodStorage;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.MoveAction;
import model.villager.intentions.plan.Plan;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class GatherFoodPlan extends Plan{

	public GatherFoodPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		name = "Gathers food";
		
		System.out.println("Villager: " + villager);
		villager.clearInventory();
		
		Tree tree = (Tree) FindEntity.getAdjacentObject(villager.getWorld(), new HasFood(), 
				EntityType.TREE, villager.getX(), villager.getY());

		// We're next to a tree. fill Inventory!
		if(tree != null){
			actionQueue.addLast(new GatherFoodAction(villager));
			actionQueue.addLast(new MoveAction(villager, null, new IsFoodStorage()));
			actionQueue.addLast(new StoreFoodAction(villager));
		}
		// We need to move, and then fill Inventory
		else{
			Point p = FindEntity.findTopMidEntityNeighbour(villager.getWorld(), new HasFood(), EntityType.TREE, 
					villager.getX(), villager.getY());
			Path movePath = null;
			if(p != null){
				movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
			}else{
				System.out.println("GATHERFOODPLAN: FAILED FINDING TREE");
				villager.setExplore();
				isFinished=true;
			}

			actionQueue.add(new MoveAction(villager, EntityType.TREE, new HasFood()));
			actionQueue.addLast(new GatherFoodAction(villager));
			actionQueue.addLast(new MoveAction(villager, null, new IsFoodStorage()));
			actionQueue.addLast(new StoreFoodAction(villager));
		}
		
	}
}
