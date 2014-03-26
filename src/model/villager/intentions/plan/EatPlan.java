package model.villager.intentions.plan;

import java.util.LinkedList;

import model.item.food.FoodSource;
import model.path.FindObject;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.EatAction;
import model.villager.intentions.action.MoveAction;

public class EatPlan extends Plan{

	public EatPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		name = "Wants to eat";
		
		FoodSource fs = (FoodSource) FindObject.getAdjacentObject(villager.getWorld(), new HasFood(), 
				null, villager.getX(), villager.getY());

		// We're next to a tree. Eat!
		if(fs != null){
			actionQueue.addLast(new EatAction(villager));
		}
		
		// We need to move, and then eat
		else{
//			Point p = FindObject.findObjectNeighbour(villager.getWorld(), new HasFood(), null, 
//					villager.getX(), villager.getY());
//			Path movePath = null;
//			if(p != null){
//				movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
//			}else{
//				villager.setExplore();
//				isFinished=true;
//			}
			actionQueue.add(new MoveAction(villager, null, new HasFood()));
			actionQueue.addLast(new EatAction(villager));
		}
		
	}
}
