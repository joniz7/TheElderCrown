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
		if(villager.getActiveItem() instanceof Drink){
			Drink d = (Drink) villager.getActiveItem();
			if(!d.consumed()){
				villager.satisfyThirst(d.drunk());
				
				if(villager.getThirst() >= Constants.MAX_THIRST){
					villager.updateStatus("statusEnd");
					actionFinished();
				}else if(d.consumed()){
					villager.setActiveItem(null);
					villager.updateStatus("statusEnd");
				}
			}
		}else if(FindObject.isAdjacentTile(world, EntityType.WATER_TILE, villager.getX(),
				villager.getY())) {
			villager.updateStatus("drinking");
			villager.setActiveItem(new WaterBowl());
		}else if(FindObject.getAdjacentObject(world, new HasDrink(), null, villager.getX(),
				villager.getY()) != null) {
			villager.updateStatus("drinking");
			DrinkSource ds = (DrinkSource) FindObject.getAdjacentObject(world, new HasDrink(), null, 
					villager.getX(), villager.getY());
			if(ds.hasDrink())
				villager.setActiveItem(ds.getDrink());
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