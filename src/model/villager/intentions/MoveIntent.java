package model.villager.intentions;

import java.awt.Point;

import model.villager.Villager;

public class MoveIntent extends Intent {

	private Point p;
	
	public MoveIntent(int cost, int desire, Villager villager, Point p) {
		super(cost, villager);
		this.p = p;
		setDesire(desire);
	}

	@Override
	public Plan getPlan() {
		// TODO Auto-generated method stub
		return new MovePlan(villager, p);
	}

	@Override
	public void calculateDesire() {
	}

}
