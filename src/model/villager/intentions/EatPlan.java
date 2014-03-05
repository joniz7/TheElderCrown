package model.villager.intentions;

import java.awt.Point;
import java.util.LinkedList;

import model.TestWorld;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasFruit;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class EatPlan extends Plan{

	public EatPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		
		Tree tree = (Tree) FindObject.getAdjacentObject(villager.getWorld(), new HasFruit(), 
				EntityType.TREE, villager.getX(), villager.getY());

		// We're next to a tree. Eat!
		if(tree != null){
			actionQueue.addLast(new EatAction(villager));
		}
		
		// We need to move, and then eat
		else{
			Point p = FindObject.findObjectNeighbour(villager.getWorld(), new HasFruit(), EntityType.TREE, 
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
