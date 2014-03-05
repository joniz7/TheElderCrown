package model.villager;

import java.awt.Point;

import model.entity.Agent;
import model.entity.MidEntity;
import model.villager.intentions.Action;
import model.villager.intentions.DieAction;
import model.villager.intentions.IntentionHandler;
import model.villager.intentions.Plan;
import util.EntityType;
import util.RandomClass;

public class Villager extends MidEntity implements Agent {

	private float hunger = -15f, thirst = 2f, speed = 20, sleepiness = 2f ;
	private VillagerWorld world;
	private boolean dead = false;
	private Plan activePlan;
	private IntentionHandler ih = new IntentionHandler(this);
	
	private int height, weight;
	
	public Villager(int x, int y) {
		super(x, y, EntityType.VILLAGER);
		world = new VillagerWorld();
		height = 140 + RandomClass.getRandomInt(50, 0);
		weight = height / 4 + RandomClass.getRandomInt(height/4, 0);
		System.out.println("New villager created: "+height+"  "+weight);
	}
	
	public VillagersWorldPerception getWorld() {
		return world;
	}

	@Override
	public void update(Perception p) {
		updatePos(p.position.x, p.position.y);
		world.updateBotEntities(p.botEntities);
		world.updateMidEntities(p.midEntities);
		world.updateTopEntities(p.topEntities);
		adjustNeeds();
		seeIfDead();
		plan();
	}

	public void satisfyHunger(float f) {
		this.hunger += f;
	}
	
	public void satisfyThirst(float f) {
		this.thirst += f;
	}
	
	public void satisfySleep(float f){
		this.sleepiness += f;
	}
	
	private void adjustNeeds() {
		hunger = hunger - 0.01f;
		thirst = thirst - 0.01f;
		sleepiness = sleepiness - 0.003f;
	}
	
	private void seeIfDead() {
		if(hunger < -100.f || thirst < -100.f) {
			dead = true;
		}
	}
	
	private void plan() {
		ih.update();
		if(activePlan == null) {
			activePlan = ih.getFirstPlan();
			System.out.println(activePlan);
		}
	}

	public Action getAction() {
		if(dead) {
			return new DieAction(this);
		}
		return activePlan.getActiveAction();
	}
	
	public void disposePlan() {
		activePlan = null;
	}
	
	public void actionDone(){
		if(!activePlan.isFinished())
			activePlan.actionDone();
		else
			activePlan = null;
	}

	public float getSpeed() {
		return speed;
	}

	public float getHunger() {
		return hunger;
	}

	public float getThirst() {
		return thirst;
	}
	
	public float getSleepiness(){
		return sleepiness;
	}
	
	public int getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}
	
	/**
	 * Method for updating the view (and thus set villager looks) after what they are currently doing.
	 * @param The new status for the villager. Currently only "sleeping" or "awake".
	 * 
	 * @author Tux
	 */
	public void updateStatus(String newStatus){
		pcs.firePropertyChange("status", null, newStatus);		
	}

	public void attemptMove(Point newPos) {
		pcs.firePropertyChange("move", null, newPos);
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	public void kill() {
		pcs.firePropertyChange("status", null, "dead");
	}
}
