package model.villager;

import java.awt.Point;

import model.World;
import model.entity.Agent;
import model.entity.MidEntity;
import model.villager.intentions.Action;
import model.villager.intentions.ExplorePlan;
import model.villager.intentions.IntentionHandler;
import model.villager.intentions.Plan;
import util.EntityType;
import util.RandomClass;
import view.entity.EntityView;
import view.entity.mid.VillagerView;

public class Villager extends MidEntity implements Agent {

	private float hunger = -15f, thirst = 2f, speed = 20, sleepiness = 2f, laziness = 1f ;
	private World world;
	private Plan activePlan;
	private IntentionHandler ih = new IntentionHandler(this);
	private boolean mustExplore = false;
	
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
	public void update(Point pos) {
		updatePos(pos.x, pos.y);
		
		adjustNeeds();
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
	
	public void setExplore(){
		mustExplore=true;
	}
	
	private void adjustNeeds() {
		hunger = hunger - 0.01f;
		thirst = thirst - 0.01f;
		sleepiness = sleepiness - 0.003f;
	}
	
	private void plan() {
		ih.update();
		if(activePlan == null) {
			if(!mustExplore){
				activePlan = ih.getFirstPlan();
				System.out.println(activePlan);
			}else{
				activePlan=new ExplorePlan(this);
				mustExplore = false;
			}
		}
	}

	public Action getAction() {
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
	
	public float getLaziness(){
		return laziness;
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
	
	/**
	 * Creates and returns a new VillagerView.
	 * Registers the view as our listener.
	 */
	public EntityView createView() {
		EntityView view = new VillagerView(x, y, height, weight);
		pcs.addPropertyChangeListener(view);
		return view;
	}


	
}
