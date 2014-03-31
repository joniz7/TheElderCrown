package model.villager.intentions.gathering;

import model.item.liquid.WaterBowl;
import model.path.FindEntity;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.ImpactableByAction;
import util.EntityType;

public class GatherDrinkAction extends Action{

	private int stacks = 0, count = 0, stacksToStore;
	
	public GatherDrinkAction(Villager villager) {
		super(villager);
		name = "Gathering Drink";
		stacksToStore = 100;
	}

	@Override
	public void tick(ImpactableByAction world){
		if(FindEntity.isAdjacentTile(world, EntityType.WATER_TILE, villager.getX(),
				villager.getY())) {

			stacks++;
			if(stacks >= stacksToStore){
				if(!villager.addToInventory(new WaterBowl())){
					System.out.println("WATER FINISHED " + count);
					villager.updateStatus("statusEnd");
					actionFinished();
				}
				count++;
				stacks = 0;
			}
		}else{
			villager.updateStatus("statusEnd");
			actionFailed();
		}
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}
}
