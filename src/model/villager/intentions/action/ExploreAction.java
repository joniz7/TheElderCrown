package model.villager.intentions.action;

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
		villager.updateStatus("exploring");
		name = "Exploring";
	}


	@Override
	public void tick(VillagersWorldPerception world){
		//TODO: Use instead of only MoveAction
		}
			

 }
