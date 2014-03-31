package model.villager.intentions;

import java.awt.Point;

import model.villager.Villager;
import model.villager.intentions.plan.Plan;
import model.villager.intentions.plan.SocialisePlan;

public class SocialiseIntent extends Intent{

	private Point otherPos;
	private int otherId;
	
	public SocialiseIntent(Villager villager, Point otherPos, int otherId) {
		super(villager);
		this.otherId = otherId;
		this.otherPos = otherPos;		
	}

	@Override
	public Plan getPlan(){
		return new SocialisePlan(villager, otherPos, otherId);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getSocial() * 4);
//		System.out.println("EatIntent: " + desire);
	}
}
