package model.villager.intentions.plan;

import java.awt.Point;
import java.util.LinkedList;

import model.RandomWorld;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.EatAction;
import model.villager.intentions.action.MoveAction;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class EatPlan extends Plan{

	public EatPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		name = "Wants to eat";
		
		Tree tree = (Tree) FindObject.getAdjacentObject(villager.getWorld(), new HasFood(), 
				EntityType.TREE, villager.getX(), villager.getY());

		// We're next to a tree. Eat!
		if(tree != null){
			actionQueue.addLast(new EatAction(villager));
		}
		
		// We need to move, and then eat
		else{
			Point p = FindObject.findObjectNeighbour(villager.getWorld(), new HasFood(), EntityType.TREE, 
					villager.getX(), villager.getY());
			Path movePath = null;
			if(p != null){
				movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
			}else{
				villager.setExplore();
				isFinished=true;
			}
			actionQueue.add(new MoveAction(villager, movePath));
			actionQueue.addLast(new EatAction(villager));
		}
		
	}
}
