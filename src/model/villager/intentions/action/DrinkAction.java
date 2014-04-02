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

//		System.out.println("SLEEP!!!");
		if (villager.getActiveItem() instanceof Drink) {
			Drink d = (Drink) villager.getActiveItem();
			if(!d.consumed()){
				villager.satisfyThirst(d.drunk());
				
				if(villager.getThirst() >= Constants.MAX_THIRST){
					villager.updateStatus("statusEnd");
					actionSuccess();
				}else if(d.consumed()){
					System.out.println("BOWL EMPTY");
					villager.setActiveItem(null);
					villager.updateStatus("statusEnd");
				}
			}else{
				System.out.println("BOWL EMPTY BEFORE");
				villager.setActiveItem(null);
				villager.updateStatus("statusEnd");
			}
		}else if(FindEntity.isAdjacentTile(world, EntityType.WATER_TILE, villager.getX(),
				villager.getY())) {
			System.out.println("FOUND ADJACAENT WATER");
			villager.updateStatus("drinking");
			villager.setActiveItem(new WaterBowl());
		}else if(FindEntity.getAdjacentObject(world, new HasDrink(), null, villager.getX(),
				villager.getY()) != null) {
			System.out.println("FOUND ADJACAENT WATER SOURCE");
			villager.updateStatus("drinking");
			DrinkSource ds = (DrinkSource) FindEntity.getAdjacentObject(world, new HasDrink(), null, 
					villager.getX(), villager.getY());
			if(ds.hasDrink())
				villager.setActiveItem(ds.getDrink());
		}else{
			System.out.println("FAILED");
			villager.updateStatus("statusEnd");
			actionFail();
		}
			

	}

	@Override
	public float getCost() {
		return 0;
	}

 }