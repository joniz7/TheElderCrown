package model.villager.intentions;

import java.awt.Point;

import model.villager.Villager;
import model.villager.intentions.plan.Plan;
import model.villager.intentions.plan.SocialiseInitPlan;
import model.villager.intentions.plan.SocialisePlan;
import model.villager.order.Order;

public class SocialiseInitIntent extends Intent{

	private Point otherPos;
	private Order otherOrder;
	private int otherId;
	
	public SocialiseInitIntent(Villager villager, Order otherOrder, Point otherPos, int otherId) {
		super(villager);
		this.otherId = otherId;
		this.otherOrder = otherOrder;
		this.otherPos = otherPos;
	}

	@Override
	public Plan getPlan(){
		return new SocialiseInitPlan(villager, otherOrder, otherPos, otherId);
	}

	@Override
	public void calculateDesire() {
		setDesire(-villager.getSocial() * 4);
//		System.out.println("EatIntent: " + desire);
	}
}
