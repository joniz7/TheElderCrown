package model.villager.intentions;

import model.entity.top.Tree;
import model.item.food.Food;
import model.item.liquid.Drink;
import model.item.liquid.WaterBowl;
import model.path.FindObject;
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
		}else{
			villager.updateStatus("statusEnd");
			actionFailed();
		}
			

	}

 }