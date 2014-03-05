package model.villager.intentions;

import model.World;
import model.villager.Villager;

public class DieAction extends Action{

	public DieAction(Villager villager) {
		super(villager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(World world) {
		world.removeAgent(villager);
		villager.kill();
	}

}
