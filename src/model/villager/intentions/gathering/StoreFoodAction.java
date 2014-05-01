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
		
		// Standing next to food storage?
		if(FindEntity.getAdjacentObject(villager.getWorld(), new IsFoodStorage(), EntityType.FOOD_STORAGE, villager.getX(),
				villager.getY()) != null) {
			FoodStorage fs = (FoodStorage) FindEntity.getAdjacentObject(villager.getWorld(), new IsFoodStorage(), 
					EntityType.FOOD_STORAGE, villager.getX(), villager.getY());
			
			stacks++;
			if(stacks >= stacksToStore){
				Item[] items = villager.getInventory();
				Food currentFood = null;
				for(int i = 0; i < items.length; i++)
					if(items[i] instanceof Food){
						currentFood = (Food) items[i];
						villager.removeFromInventory(i);
						items[i] = null;
						break;
					}
				// No more food to store
				if(currentFood == null){
					success();
				}
				// Store food
				else{
					fs.addFood(currentFood);
				}
				stacks = 0;
			}
		}
		// Not standing next to food storage
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
