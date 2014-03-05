package model.villager.intentions;

import model.villager.Villager;
import model.villager.VillagersWorldPerception;

public class DieAction extends Action{

	public DieAction(Villager villager) {
		super(villager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(VillagersWorldPerception world) {
		villager.kill();
	}

}
