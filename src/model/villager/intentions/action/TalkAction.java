package model.villager.intentions.action;

import java.awt.Point;

import model.villager.Villager;

public class TalkAction extends Action {

	// How long we should spend talking
	private int ticksToTalk;
	// How much satisfaction we should gain each tick from talking
	private float satisfyAmount;

	// The villager we're talking to, and her position
	private int otherId;
	private Point otherPos;
	
	public TalkAction(Villager villager, Point otherPos, int otherId) {
		// TODO: get name of other villager
		super(villager, "Talking to "+otherId);
		
		ticksToTalk = 300;
		// TODO base this on how much we like the other villager
		satisfyAmount = 0.0015f;
		this.otherId = otherId;
		this.otherPos = otherPos;
		
	}

	@Override
	public void tick(ImpactableByAction world) {
		
		// TODO look if other villager has left - if so, become sad and abort
		
		if (--ticksToTalk < 0) {
			actionSuccess();
		} else {
			System.out.println("talking to "+otherId+", social: "+villager.getSocial());
			villager.satisfySocial(satisfyAmount);
		}
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return ticksToTalk;
	}

}
