package model.villager.intentions.action;

import model.item.food.Food;
import model.item.food.FoodSource;
import model.path.FindObject;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.AgentWorld;

public class EatAction extends Action{

	private int stacks, stacksToEat;
	
	public EatAction(Villager villager) {
		super(villager);
		name = "Eating";
		stacksToEat = 500;
	}

	@Override
	public void tick(AgentWorld world){
		if(villager.getActiveItem() instanceof Food){
			Food f = (Food) villager.getActiveItem();
			if(!f.consumed()){
				villager.satisfyHunger(f.eaten());
				
				stacks++;
				if(stacks > stacksToEat){
					villager.updateStatus("statusEnd");
					actionFinished();
				}else if(f.consumed()){
					villager.setActiveItem(null);
					villager.updateStatus("statusEnd");
				}
			}
		}else if(FindObject.getAdjacentObject(world, new HasFood(), null, villager.getX(),
				villager.getY()) != null) {
			FoodSource fs = (FoodSource) FindObject.getAdjacentObject(world, new HasFood(), null, villager.getX(),
					villager.getY());
			villager.updateStatus("eating");
			villager.setActiveItem(fs.getFood());
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