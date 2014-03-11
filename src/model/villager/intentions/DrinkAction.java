package model.villager.intentions;

import model.path.FindObject;
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
		if(FindObject.isAdjacentTile(world, EntityType.WATER_TILE, villager.getX(),
				villager.getY())){
			villager.updateStatus("drinking");
			
			villager.satisfyThirst(0.5f);
			stacks++;
			if(stacks > stacksToDrink){
				villager.updateStatus("statusEnd");
				actionFinished();
			}
				
				
		}else{
			villager.updateStatus("statusEnd");
			actionFailed();
		}
			

	}

 }