package model.villager.intentions.plan;

import java.util.LinkedList;

import model.item.food.FoodSource;
import model.path.FindEntity;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.EatAction;
import model.villager.intentions.action.MoveAction;

public class EatPlan extends Plan{

	public EatPlan(Villager villager){
		super(villager, "Wants to eat");
		actionQueue = new LinkedList<Action>();
		
		FoodSource fs = (FoodSource) FindEntity.getAdjacentObject(villager.getWorld(), new HasFood(), 
				null, villager.getX(), villager.getY());

		// We're next to a tree. Eat!
		if(fs != null){
			addAction(new EatAction(villager));
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
			addAction(new MoveAction(villager, null, new HasFood()));
			addAction(new EatAction(villager));
		}
		
	}

	@Override
	public void retryAction() {
		// TODO Auto-generated method stub
		
	}
}
