package model.villager.intentions.gathering;

import model.entity.top.house.FoodStorage;
import model.item.Item;
import model.item.food.Food;
import model.path.FindEntity;
import model.path.criteria.IsFoodStorage;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.ImpactableByAction;
import util.EntityType;

public class StoreFoodAction extends Action{

	private int stacks = 0, stacksToStore;
	
	public StoreFoodAction(Villager villager) {
		super(villager, "Storing food");
		stacksToStore = 20;
	}

	@Override
	public void tick(ImpactableByAction world){
		if(FindEntity.getAdjacentObject(world, new IsFoodStorage(), EntityType.FOOD_STORAGE, villager.getX(),
				villager.getY()) != null) {
			FoodStorage fs = (FoodStorage) FindEntity.getAdjacentObject(world, new IsFoodStorage(), 
					EntityType.FOOD_STORAGE, villager.getX(), villager.getY());
			
			stacks++;
			if(stacks >= stacksToStore){
				Item[] items = villager.getInventory();
				Food food = null;
				for(int i = 0; i < items.length; i++)
					if(items[i] instanceof Food){
						food = (Food) items[i];
						villager.removeFromInventory(i);
						items[i] = null;
						break;
					}
				
				if(food == null){
					villager.updateStatus("statusEnd");
					actionFinished();
				}else{
					fs.addFood(food);
				}
				stacks = 0;
			}
		}else{
			villager.updateStatus("statusEnd");
			System.out.println("FAIL");
			actionFailed();
		}
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}
}
