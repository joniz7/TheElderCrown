package model.villager;

import java.awt.Point;

import model.TestWorld;
import model.World;
import model.entity.Agent;
import model.entity.MidEntity;
import model.path.FindObject;
import model.villager.brain.Brain;
import model.villager.intentions_Reloaded.Action;
import model.villager.intentions_Reloaded.EatPlan;
import model.villager.intentions_Reloaded.IntentionHandler;
import model.villager.intentions_Reloaded.Plan;
import util.EntityType;
import util.RandomClass;
import util.SoundP;
import util.Tickable;
import view.entity.EntityView;
import view.entity.mid.VillagerView;

public class Villager extends MidEntity implements Agent {

	private float hunger = -15f, thirst = 2f, speed = 20;
	private World world;
	private Point currentPos;
	private Plan activePlan;
	private IntentionHandler IH = new IntentionHandler(this);
	
	private int test = 0, height, weight;
	
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
		currentPos = pos;
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
	
	private void adjustNeeds() {
		hunger = hunger - 0.01f;
		thirst = thirst - 0.01f;
	}
	
	private void plan() {
		IH.update();
		if(activePlan == null) {
			activePlan = IH.getFirstPlan();
			System.out.println(activePlan);
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
	
	
	
	public int getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}
	
	/**
	 * Method for updating the view (and thus set villager looks) after what they are currently doing.
	 * Should probably be called in the *Action-classes
	 * 
	 * @param The new status for the villager. Currently only "sleeping" or "awake".
	 */
	protected void updateStatus(String newStatus){
		pcs.firePropertyChange("status", null, newStatus);
		
	}

	/*
	private Brain brain;
	private TestWorld world;

	private int speedCap = 240, progress = 0, speed = 20, stepCount = 0;
	private boolean moving = false;
	private String status;
	
	public Villager(TestWorld world, int x, int y){

		super(x , y, EntityType.VILLAGER);
		this.world = world;
		updatePos(x, y);
		brain = new Brain(this, world);
	}

	@Override
	public void tick(){
		brain.tick();

		if(moving)
			move();
		else
			updatePos(x , y);
	}
	
	public void move(){
		progress += speed;
		
		int interPolX = 0;
		int interPolY = 0;
		
		if(stepCount < brain.getCurrentPath().getLength()){
			interPolX = brain.getCurrentPath().getStep(stepCount).getX() - x;
			interPolY = brain.getCurrentPath().getStep(stepCount).getY() - y;
		}else{
			setMoving(false);
		}
		
        if(progress > speedCap && stepCount < brain.getCurrentPath().getLength()){
        	progress = 0;
        	x = brain.getCurrentPath().getStep(stepCount).getX();
        	y = brain.getCurrentPath().getStep(stepCount++).getY();
        }
        
        updatePos(x, y, (interPolX * (progress/12)), (interPolY * (progress/12)));
        updateStatus("awake");
	}
	
	public boolean eat(){
		if(FindObject.isAdjacentObject(world, EntityType.TREE, x, y)){
			if(world.getTree(x + 1, y) != null){
				if(world.getTree(x + 1, y).hasFruit()){
					world.getTree(x + 1, y).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(x - 1, y) != null){
				if(world.getTree(x - 1, y).hasFruit()){
					world.getTree(x - 1, y).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(x, y + 1) != null){
				if(world.getTree(x, y + 1).hasFruit()){
					world.getTree(x, y + 1).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
			
			if(world.getTree(x, y - 1) != null){
				if(world.getTree(x, y - 1).hasFruit()){
					world.getTree(x, y - 1).eaten();
					brain.getBrainStem().getHunger().satisfy(6);
					SoundP.playSound("ph", "eat.wav");
					return true;
				}
			}
		}
		updateStatus("awake");
		return false;
	}
	
	public boolean drink(){
		updateStatus("awake");
		if(FindObject.isAdjacentTile(world, EntityType.WATER_TILE, x, y)){
			brain.getBrainStem().getThirst().satisfy(5);
			SoundP.playSound("ph", "drink.wav");
			return true;
		}
		return false;
	}
	
	public void sleep(){
		brain.getBrainStem().getSleep().satisfy(100);
		SoundP.playSound("ph", "sleep.wav");
		updateStatus("sleeping");
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
		stepCount = 0;
	}


	public TestWorld getWorld() {
		return world;
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
