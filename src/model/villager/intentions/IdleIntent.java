package model.villager.intentions;

import model.villager.Villager;

/**
 * Simple class for handling whether a villager wants to idle
 * @author Tux
 *
 */

public class IdleIntent extends Intent {

	public IdleIntent(Villager villager) {
		super(0, villager);
	}

	@Override
	public Plan getPlan(){
		return new IdlePlan(villager);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getLaziness() * 2);
//		System.out.println("IdleIntent: " + desire);
	}
	
}
