package model.villager;

import java.awt.Point;

import javax.naming.OperationNotSupportedException;

import model.entity.Agent;
import model.entity.mid.MidEntity;
import model.entity.top.house.HouseWall;
import model.item.Item;
import model.villager.intentions.Intent;
import model.villager.intentions.IntentionHandler;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.DieAction;
import model.villager.intentions.plan.DrinkPlan;
import model.villager.intentions.plan.EatPlan;
import model.villager.intentions.plan.ExplorePlan;
import model.villager.intentions.plan.Plan;
import model.villager.intentions.plan.SleepPlan;
import model.villager.order.Order;
import model.villager.util.NameGen;
import util.EntityType;
import util.RandomClass;
import view.entity.EntityView;
import view.entity.mid.VillagerView;

public class Villager extends MidEntity implements Agent {

	private IntentionHandler ih;

	private float hunger = 10f, thirst = 10f, speed = 30, sleepiness = 40f, laziness = 20f, obedience = 0 ;
	private VillagerWorld world;
	private boolean dead = false;
	private String currentAction, currentPlan;
	private Plan activePlan;

	private boolean mustExplore, isShowUI = false;
	private int length, weight;
	private String name;
	private int sex; // 0 -female, 1 - male
	private Point myBed = null;
	
	private int time;
	
	private Item activeItem;
	private Item[] inventory = new Item[6];
	
	public Villager(Point p) {
		super(p.x, p.y, EntityType.VILLAGER);
		world = new VillagerWorld();
		length = 140 + RandomClass.getRandomInt(50, 0);
		weight = length / 4 + RandomClass.getRandomInt(length/4, 0);
		
		this.name = NameGen.newName(true);
		ih = new IntentionHandler(this);
		
		this.sex = RandomClass.getRandomInt(2, 0);
		if(sex == 0){
			this.name = NameGen.newName(true);
		}else{
			this.name = NameGen.newName(false);
		}
		
		currentAction = "Doing Nothing";
		currentPlan = "Doing Nothing";
		
		System.out.println("New villager created: " + name+ " " +length+"  "+weight+ " " +sex);
	}
	
	public VillagersWorldPerception getWorld() {
		return world;
	}

	@Override
	/**
	 * Update this villager's needs and plans for the future.
	 * 
	 * The villager is given a perception, which includes information
	 * about her position, and possibly an order she should obey.
	 */
	public void update(Perception p, int time) {
		updatePos(p.position.x, p.position.y);
		updateUI();
		
		this.time = time;
		
		world.updateBotEntities(p.botEntities);
		world.updateMidEntities(p.midEntities);
		world.updateTopEntities(p.topEntities);

		adjustNeeds();
		
		// If order was received, take it into consideration when planning
		if (p.order != null) {
			addOrder(p.order);
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
		if(hunger > 80){
			hunger = 80;
			if(activePlan instanceof EatPlan){
				disposePlan();
				plan();
			}
		}
	}
	
	public void satisfyThirst(float f) {
		this.thirst += f;
		if(thirst > 80){
			thirst = 80;
			if(activePlan instanceof DrinkPlan){
				disposePlan();
				plan();
			}
		}
	}
	
	public void satisfySleep(float f){
		this.sleepiness += f;
		if(sleepiness > 80){
			sleepiness = 80;
			if(activePlan instanceof SleepPlan){
				disposePlan();
				plan();
			}
		}
	}
	
	public void setExplore(){
		mustExplore=true;
	}
	
	private void adjustNeeds() {
		int hours = time / 750;
		
		if(hours >= 22 || hours < 8){

		}else{
			hunger = hunger - 0.01f;
			thirst = thirst - 0.02f;
		}
		
		sleepiness = sleepiness - 0.012f;
	}
	
	private void seeIfDead() {
		if(hunger < -100.f || thirst < -100.f) {
			dead = true;
		}
	}
	
	private void plan() {
		ih.update();
		if(activePlan == null) {
			if(!mustExplore){
				activePlan = ih.getFirstPlan();
//				System.out.println(activePlan);
			}else{
//				System.out.println("Creating ExplorePlan");
				activePlan=new ExplorePlan(this);
				mustExplore = false;
			}
		}else{
			if(activePlan.getActiveAction() != null)
				currentAction = activePlan.getActiveAction().getName();
			currentPlan = activePlan.getName();
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
	
	public float getLaziness(){
		return laziness;
	}
	
	public int getLength() {
		return length;
	}

	public int getWeight() {
		return weight;
	}
	
	public int getSex(){
		return sex;
	}
	
	public boolean isMale(){
		if(sex == 1){
			return true;
		}
		return false;
	}
	
	public boolean isFemale(){
		if(sex == 0){
			return true;
		}
		return false;
	}
	
	public void setBed(Point p){
		this.myBed = p;
	}
	
	public Point getBedPos(){
		return myBed;
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
	 * Method for showing or hiding the UI for this Villager
	 * 
	 * @param show - true if the UI should be shown
	 */
	public void setShowUI(boolean show){
		if(show){
			pcs.firePropertyChange("status", null, "show");
			pcs.firePropertyChange("status", null, "highlight");
		}else{
			pcs.firePropertyChange("status", null, "hide");	
			pcs.firePropertyChange("status", null, "unHighlight");
		}
		isShowUI = show;
	}
	
	public void updateUI(){
		if(isShowUI){
			pcs.firePropertyChange("status", hunger, "hunger");
			pcs.firePropertyChange("status", thirst, "thirst");
			pcs.firePropertyChange("status", sleepiness, "sleepiness");
			
			pcs.firePropertyChange("status", currentAction, "action");
			pcs.firePropertyChange("status", currentPlan, "plan");
		}
	}
	
	/**
	 * Creates and returns a new VillagerView.
	 * Registers the view as our listener.
	 */
	public EntityView createView() {
		EntityView view = new VillagerView(x, y, length, weight);
		pcs.addPropertyChangeListener(view);
		return view;
	}

	public void attemptMove(Point newPos) {
		pcs.firePropertyChange("move", null, newPos);
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	public String getName() {
		return name;
	}
	
	public float getObedience() {
		return obedience;
	}
	
	@Override
	/**
	 * Creates a new villager with the same position as this one.
	 * Warning: this is not a complete copy by any means! Do not use!
	 */
	public Villager copy() {
		Point p = new Point(x,y);
		Villager copy = new Villager(p);
		return copy;
//		throw new org.newdawn.slick.util.OperationNotSupportedException("what is a human mind?");
		
	}

	public Item getActiveItem() {
		return activeItem;
	}

	public void setActiveItem(Item activeItem) {
		this.activeItem = activeItem;
	}
	
	public boolean addToInventory(Item item){
		int count = 0;
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] == null)
				count++;
		System.out.println("FREE SPACE " + count);
			
		for(int i = 0; i < inventory.length; i++)
			if(inventory[i] == null){
				inventory[i] = item;
				pcs.firePropertyChange("status", inventory, "inventory");
				return true;
			}
		return false;
	}
	
	public void removeFromInventory(int index){
		inventory[index] = null;
		pcs.firePropertyChange("status", inventory, "inventory");
	}
	
	public void clearInventory(){
		for(int i = 0; i < inventory.length; i++)
			inventory[i] = null;
		pcs.firePropertyChange("status", inventory, "inventory");
	}
	
	public Item[] getInventory(){
		return inventory;
	}

	public Plan getActivePlan() {
		return activePlan;
	}

	public int getTime() {
		return time;
	}
}
