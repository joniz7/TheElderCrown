package model.villager.intentions.action;

import model.villager.Villager;

public class ExploreAction extends Action {

	public ExploreAction(Villager villager) {
		super(villager);
		villager.updateStatus("exploring");
		name = "Exploring";
	}


	@Override
	public void tick(ImpactableByAction world){
		//TODO: Use instead of only MoveAction
		}


	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}
			

 }
