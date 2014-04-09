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
		if(villager.getActiveItem() instanceof Food){
			Food f = (Food) villager.getActiveItem();
			if(!f.consumed()){
				villager.satisfyHunger(f.eaten());

				if(villager.getHunger() >= Constants.MAX_HUNGER){
					villager.updateStatus("statusEnd");
					actionSuccess();
				}else if(f.consumed()){
					villager.setActiveItem(null);
					villager.updateStatus("statusEnd");
				}
			}else{
				villager.setActiveItem(null);
				villager.updateStatus("statusEnd");
			}
		}else if(FindEntity.getAdjacentObject(villager.getWorld(), new HasFood(), null, villager.getX(),
				villager.getY()) != null) {
			FoodSource fs = (FoodSource) FindEntity.getAdjacentObject(villager.getWorld(), new HasFood(), null, villager.getX(),
					villager.getY());
			villager.setActiveItem(fs.getFood());
		}else{
			villager.updateStatus("statusEnd");
			actionFail();
		}
			

	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

 }