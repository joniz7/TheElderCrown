package model.villager.intentions.action;

import model.item.liquid.Drink;
import model.item.liquid.DrinkSource;
import model.item.liquid.WaterBowl;
import model.path.FindEntity;
import model.path.criteria.HasDrink;
import model.villager.Villager;
import util.Constants;
import util.EntityType;

public class DrinkAction extends Action{

	
	public DrinkAction(Villager villager) {
		super(villager, "Drinking");
	}

	@Override
	public void tick(ImpactableByAction world){
		villager.updateStatus("drinking");

		// We're holding something drinkable. Drink it!
		if (villager.getActiveItem() instanceof Drink) {
			Drink d = (Drink) villager.getActiveItem();
			if(!d.consumed()){
				villager.satisfyThirst(d.drunk());
				// Finished drinking
				if(villager.getThirst() >= Constants.MAX_THIRST){
					// Finish Plan
					success();
				}
				else if(d.consumed()){
					System.out.println("BOWL EMPTY");
					villager.setActiveItem(null);
					villager.updateStatus("statusEnd");
				}
			}else{
				System.out.println("BOWL EMPTY BEFORE");
				villager.setActiveItem(null);
				villager.updateStatus("statusEnd");
			}
		}
		// Are we standing next to water? (?)
		else if(FindEntity.isAdjacentTile(villager.getWorld(), EntityType.WATER_TILE, villager.getX(),
				villager.getY())) {
			System.out.println("FOUND ADJACAENT WATER");
			villager.updateStatus("drinking");
			villager.setActiveItem(new WaterBowl());
		}
		// Are we standing next to something drinkable? (?)
		else if(FindEntity.getAdjacentObject(villager.getWorld(), new HasDrink(), null, villager.getX(),
				villager.getY()) != null) {
			System.out.println("FOUND ADJACAENT WATER SOURCE");
			villager.updateStatus("drinking");
			DrinkSource ds = (DrinkSource) FindEntity.getAdjacentObject(villager.getWorld(), new HasDrink(), null, 
					villager.getX(), villager.getY());
			if(ds.hasDrink())
				villager.setActiveItem(ds.getDrink());
		}
		// No water available anywhere.
		else {
			// Fail DrinkPlan
			fail();
		}

	}

	@Override
	public float getCost() {
		return 0;
	}

 }