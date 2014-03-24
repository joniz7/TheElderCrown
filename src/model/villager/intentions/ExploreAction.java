package model.villager.intentions;

import util.EntityType;
import model.World;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;

public class ExploreAction extends Action {

	public ExploreAction(Villager villager) {
		super(villager);
		name = "Exploring";
	}


	@Override
	public void tick(VillagersWorldPerception world){
		//TODO: Use instead of only MoveAction
		}


	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}
			

 }
