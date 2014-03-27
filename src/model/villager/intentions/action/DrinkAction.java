package model.villager.intentions.action;

import model.item.liquid.Drink;
import model.item.liquid.DrinkSource;
import model.item.liquid.WaterBowl;
import model.path.FindObject;
import model.path.criteria.HasDrink;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import util.Constants;
import util.EntityType;

public class DrinkAction extends Action{

	
	public DrinkAction(Villager villager) {
		super(villager);
		name = "Drinking";
	}

	@Override
	public void tick(VillagersWorldPerception world){
		villager.updateStatus("drinking");
		System.out.println("SLEEP!!!");
		if(villager.getActiveItem() instanceof Drink){
			System.out.println("HAS DRINK!");
			Drink d = (Drink) villager.getActiveItem();
			if(!d.consumed()){
				System.out.println("DRANK");
				villager.satisfyThirst(d.drunk());
				
				if(villager.getThirst() >= Constants.MAX_THIRST){
					System.out.println("NOT THIRSTY");
					villager.updateStatus("statusEnd");
					actionFinished();
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
		}else if(FindObject.isAdjacentTile(world, EntityType.WATER_TILE, villager.getX(),
				villager.getY())) {
			System.out.println("FOUND ADJACAENT WATER");
			villager.updateStatus("drinking");
			villager.setActiveItem(new WaterBowl());
		}else if(FindObject.getAdjacentObject(world, new HasDrink(), null, villager.getX(),
				villager.getY()) != null) {
			System.out.println("FOUND ADJACAENT WATER SOURCE");
			villager.updateStatus("drinking");
			DrinkSource ds = (DrinkSource) FindObject.getAdjacentObject(world, new HasDrink(), null, 
					villager.getX(), villager.getY());
			if(ds.hasDrink())
				villager.setActiveItem(ds.getDrink());
		}else{
			System.out.println("FAILED");
			villager.updateStatus("statusEnd");
			actionFailed();
		}
			

	}

	@Override
	public float getCost() {
		return 0;
	}

 }