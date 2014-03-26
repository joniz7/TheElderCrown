package model.villager.intentions.action;

import model.entity.top.Tree;
import model.item.food.Food;
import model.item.liquid.Drink;
import model.item.liquid.DrinkSource;
import model.item.liquid.WaterBowl;
import model.path.FindObject;
import model.path.criteria.HasDrink;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import util.EntityType;

public class DrinkAction extends Action{

	private int stacks, stacksToDrink;
	
	public DrinkAction(Villager villager) {
		super(villager);
		name = "Drinking";
		stacksToDrink = 250;
	}

	@Override
	public void tick(VillagersWorldPerception world){
		villager.updateStatus("drinking");
		if(villager.getActiveItem() instanceof Drink){
			Drink d = (Drink) villager.getActiveItem();
			if(!d.consumed()){
				villager.satisfyThirst(d.drunk());
				
				stacks++;
				if(stacks > stacksToDrink){
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