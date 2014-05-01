package model.villager.intentions.action;

import model.item.food.Food;
import model.item.food.FoodSource;
import model.path.FindEntity;
import model.path.criteria.HasFood;
import model.villager.Villager;
import util.Constants;

public class EatAction extends Action{

	private int stacks, stacksToEat;
	
	public EatAction(Villager villager) {
		super(villager, "Eating");
	}

	@Override
	public void tick(ImpactableByAction world){
		villager.updateStatus("eating");
		
		// Are we holding food?
		if(villager.getActiveItem() instanceof Food){
			Food f = (Food) villager.getActiveItem();
			if(!f.consumed()){
				villager.satisfyHunger(f.eaten());
				// We're done eating
				if(villager.getHunger() >= Constants.MAX_HUNGER){
					// EatPlan succeeded
					success();
				}else if(f.consumed()){
					villager.setActiveItem(null);
					villager.updateStatus("statusEnd");
				}
			}else{
				villager.setActiveItem(null);
				villager.updateStatus("statusEnd");
			}
		}
		// Are we standing next to a food source?
		else if(FindEntity.getAdjacentObject(villager.getWorld(), new HasFood(), null, villager.getX(),
				villager.getY()) != null) {
			FoodSource fs = (FoodSource) FindEntity.getAdjacentObject(villager.getWorld(), new HasFood(), null, villager.getX(),
					villager.getY());
			villager.setActiveItem(fs.getFood());
		}
		// No food could be found
		else {
			// Fail EatPlan
			fail();
		}
			

	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

 }