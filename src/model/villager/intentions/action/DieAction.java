package model.villager.intentions.action;

import model.villager.Villager;

public class DieAction extends Action{

	public DieAction(Villager villager) {
		super(villager);
		name = "Dying";
	}

	@Override
	public void tick(ImpactableByAction world) {
		villager.updateStatus("dead");
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
