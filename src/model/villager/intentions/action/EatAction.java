package model.villager.intentions.action;

import model.entity.top.Tree;
import model.item.food.Food;
import model.item.food.FoodSource;
import model.path.FindObject;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import util.Constants;
import util.EntityType;

public class EatAction extends Action{

	private int stacks, stacksToEat;
	
	public EatAction(Villager villager) {
		super(villager);
		name = "Eating";

	}

	@Override
	public void tick(VillagersWorldPerception world){
		villager.updateStatus("eating");
		if(villager.getActiveItem() instanceof Food){
			Food f = (Food) villager.getActiveItem();
			if(!f.consumed()){
				villager.satisfyHunger(f.eaten());

				if(villager.getHunger() >= Constants.MAX_HUNGER){
					villager.updateStatus("statusEnd");
					actionFinished();
				}else if(f.consumed()){
					villager.setActiveItem(null);
					villager.updateStatus("statusEnd");
				}
			}else{
				villager.setActiveItem(null);
				villager.updateStatus("statusEnd");
			}
		}else if(FindObject.getAdjacentObject(world, new HasFood(), null, villager.getX(),
				villager.getY()) != null) {
			FoodSource fs = (FoodSource) FindObject.getAdjacentObject(world, new HasFood(), null, villager.getX(),
					villager.getY());
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