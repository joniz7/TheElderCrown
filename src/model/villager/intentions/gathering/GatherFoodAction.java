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
		if(FindEntity.getAdjacentObject(world, new HasFood(), EntityType.TREE, villager.getX(),
				villager.getY()) != null) {
			Tree tree = (Tree) FindEntity.getAdjacentObject(world, new HasFood(), EntityType.TREE, villager.getX(),
					villager.getY());
			stacks++;
			if(stacks >= stacksToStore){
				if(!villager.addToInventory(tree.getFood())){
					villager.updateStatus("statusEnd");
					System.out.println("GATHERFOOD FINISHED");
					actionFinished();
				}
				stacks = 0;
			}
		}else{
			villager.updateStatus("statusEnd");
			System.out.println("GATHERFOOD FAILED");
			actionFailed();
		}
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}
}
