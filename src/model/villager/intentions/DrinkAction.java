package model.villager.intentions;

import util.EntityType;
import model.World;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFruit;
import model.villager.Villager;

public class DrinkAction extends Action{

	private int stacks, stacksToDrink;
	
	public DrinkAction(Villager villager) {
		super(villager);
		stacksToDrink = 250;
	}

	@Override
	public void tick(World world){
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

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

 }