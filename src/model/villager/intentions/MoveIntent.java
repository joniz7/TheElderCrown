package model.villager.intentions;

import java.awt.Point;

import model.villager.Villager;

public class MoveIntent extends Intent {

	private Point p;
	private Plan plan;
	
	public MoveIntent(int cost, int desire, Villager villager, Point p) {
		super(villager);
		this.p = p;
		setDesire(desire);
	}

	@Override
	public Plan getPlan() {
		// TODO Auto-generated method stub
		plan = new MovePlan(villager, p);
		return plan;
	}

	@Override
	public void calculateDesire() {
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return plan.getCost();
	}

}
