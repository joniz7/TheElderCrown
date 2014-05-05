package model.villager.intentions;

import java.awt.Point;

import util.Constants;

import model.villager.Villager;
import model.villager.intentions.plan.Plan;
import model.villager.intentions.plan.SocialisePlan;

public class SocialiseIntent extends Intent{

	private Point myPos;
	private Point otherPos;
	private int otherId;
	
	/**
	 * Create a new SocialiseIntent.
	 * 
	 * @param villager - the receiving villager, who should walk to the sender 
	 * @param myPos - the position we should walk to
	 * @param otherPos - the sender's position
	 * @param otherId - the id of the sender
	 */
	public SocialiseIntent(Villager villager, Point myPos, Point otherPos, int otherId) {
		super(villager);
		this.myPos = myPos;
		this.otherId = otherId;
		this.otherPos = otherPos;		
	}

	@Override
	public Plan getPlan(){
		return new SocialisePlan(villager, myPos, otherPos, otherId);
	}

	@Override
	public void calculateDesire() {
		// TODO socialising always on top - super ugly!
		float socialiseDesire = -villager.getSocial();// + villager.getRelation(otherId); 
		setDesire(socialiseDesire * Constants.JOIN_SOCIALISE_DESIRE);
	}
	
	@Override
	public String toString() {
		return super.toString()+", myPos: "+myPos+", otherPos: "+otherPos+", otherId:"+otherId;
	}
}
