package model.villager;

import java.awt.Point;

import model.World;
import model.entity.Agent;
import model.entity.MidEntity;
import model.villager.intentions.Action;
import model.villager.intentions.DieAction;
import model.villager.intentions.Intent;
import model.villager.intentions.IntentionHandler;
import model.villager.intentions.Plan;
import model.villager.order.Order;
import util.EntityType;
import util.RandomClass;
import view.entity.EntityView;
import view.entity.mid.VillagerView;

public class Villager extends MidEntity implements Agent {

	private float hunger = -15f, thirst = 2f, speed = 20, sleepiness = 2f, obedience = 1f ;
	private boolean dead = false;
	
	private World world;
	private Plan activePlan;
	private IntentionHandler ih = new IntentionHandler(this);
	
	private int height, weight;
	
	public Villager(World world, int x, int y) {
		super(x, y, EntityType.VILLAGER);
		this.world = world;
		height = 140 + RandomClass.getRandomInt(50, 0);
		weight = height / 4 + RandomClass.getRandomInt(height/4, 0);
		System.out.println("New villager created: "+height+"  "+weight);
	}
	
	public World getWorld() {
		return world;
	}

	@Override
	/**
	 * Update this villager's needs and plans for the future.
	 * 
	 * The villager is given information about her position,
	 * 		and possibly an order she should obey.
	 */
	public void update(Point pos, Order o) {
		updatePos(pos.x, pos.y);
		adjustNeeds();
		
		// If order was received, take it into consideration when planning
		if (o != null) {
			addOrder(o);
		}
		
		seeIfDead();
		plan();
	}
	
	/**
	 * Adds an order to our intent handler,
	 * which then will be considered the next time we plan.
	 */
	private void addOrder(Order o) {
		Intent i = o.getIntent();
		// TODO modify intent desire before adding,
		//      based on obedience and other parameters
		ih.addIntent(i);
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

			activePlan = ih.getFirstPlan();
			System.out.println(activePlan);

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
		if(!activePlan.isFinished()) {
			activePlan.actionDone();
		} else {
			activePlan = null;
			ih.intentDone();
		}
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

	@Override
	public boolean isDead() {
		return dead;
	}

	public void kill() {
		pcs.firePropertyChange("status", null, "dead");
	}
	
	public float getObedience() {
		return obedience;
	}
	
}
