package model.villager.intentions.gathering;

import model.entity.top.house.DrinkStorage;
import model.item.Item;
import model.item.liquid.Drink;
import model.path.FindEntity;
import model.path.criteria.IsDrinkStorage;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.ImpactableByAction;
import util.EntityType;

public class StoreDrinkAction extends Action{

	private int stacks = 0, stacksToStore;
	
	public StoreDrinkAction(Villager villager) {
		super(villager, "Storing drink");
		stacksToStore = 20;
	}

	@Override
	public void tick(ImpactableByAction world){
		if(FindEntity.getAdjacentObject(world, new IsDrinkStorage(), EntityType.DRINK_STORAGE, villager.getX(),
				villager.getY()) != null) {
			DrinkStorage fs = (DrinkStorage) FindEntity.getAdjacentObject(world, new IsDrinkStorage(), 
					EntityType.DRINK_STORAGE, villager.getX(), villager.getY());
			
			stacks++;
			if(stacks >= stacksToStore){
				Item[] items = villager.getInventory();
				Drink drink = null;
				for(int i = 0; i < items.length; i++)
					if(items[i] instanceof Drink){
						drink = (Drink) items[i];
						villager.removeFromInventory(i);
						items[i] = null;
						break;
					}
				
				if(drink == null){
					villager.updateStatus("statusEnd");
					actionFinished();
				}else{
					fs.addDrink(drink);
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
