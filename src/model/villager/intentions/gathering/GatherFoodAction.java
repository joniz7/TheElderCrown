package model.villager.intentions.gathering;

import model.entity.top.Tree;
import model.path.FindEntity;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.ImpactableByAction;
import util.EntityType;

public class GatherFoodAction extends Action{

	private int stacks = 0, stacksToStore;
	
	public GatherFoodAction(Villager villager) {
		super(villager, "Gathering food");
		stacksToStore = 100;
	}

	@Override
	public void tick(ImpactableByAction world){
		
		// Standing next to food?
		if(FindEntity.getAdjacentObject(villager.getWorld(), new HasFood(), EntityType.TREE, villager.getX(),
				villager.getY()) != null) {
			Tree tree = (Tree) FindEntity.getAdjacentObject(villager.getWorld(), new HasFood(), EntityType.TREE, villager.getX(),
					villager.getY());
			stacks++;
			// Done picking food
			if(stacks >= stacksToStore){
				if(!villager.addToInventory(tree.getFood())){
					success();
				}
				stacks = 0;
			}
		}
		// Not standing next to food
		else{
			fail();
		}
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}
}
