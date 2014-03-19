package model.villager.intentions.gathering;

import util.EntityType;
import model.entity.top.Tree;
import model.item.food.Food;
import model.item.liquid.WaterBowl;
import model.path.FindObject;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import model.villager.intentions.action.Action;

public class GatherDrinkAction extends Action{

	private int stacks = 0, count = 0, stacksToStore;
	
	public GatherDrinkAction(Villager villager) {
		super(villager);
		name = "Gathering Drink";
		stacksToStore = 100;
	}

	@Override
	public void tick(VillagersWorldPerception world){
		if(FindObject.isAdjacentTile(world, EntityType.WATER_TILE, villager.getX(),
				villager.getY())) {

			stacks++;
			if(stacks >= stacksToStore){
				if(!villager.addToInventory(new WaterBowl())){
					System.out.println("WATER FINISHED " + count);
					villager.updateStatus("statusEnd");
					actionFinished();
				}
				count++;
				System.out.println("WATER ADDED " + count);
				stacks = 0;
			}
		}else{
			villager.updateStatus("statusEnd");
			actionFailed();
		}
	}
}
