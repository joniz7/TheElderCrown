package model.villager.intentions.gathering;

import util.EntityType;
import model.entity.top.Tree;
import model.item.food.Food;
import model.path.FindObject;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import model.villager.intentions.action.Action;

public class GatherFoodAction extends Action{

	private int stacks = 0, stacksToStore;
	
	public GatherFoodAction(Villager villager) {
		super(villager);
		name = "Gathering Food";
		stacksToStore = 100;
	}

	@Override
	public void tick(VillagersWorldPerception world){
		if(FindObject.getAdjacentObject(world, new HasFood(), EntityType.TREE, villager.getX(),
				villager.getY()) != null) {
			Tree tree = (Tree) FindObject.getAdjacentObject(world, new HasFood(), EntityType.TREE, villager.getX(),
					villager.getY());
			stacks++;
			if(stacks >= stacksToStore){
				if(!villager.addToInventory(tree.getFood())){
					villager.updateStatus("statusEnd");
					actionFinished();
				}
				stacks = 0;
			}
		}else{
			villager.updateStatus("statusEnd");
			actionFailed();
		}
	}
}
