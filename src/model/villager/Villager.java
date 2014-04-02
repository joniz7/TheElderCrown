package model.villager;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import model.entity.Agent;
import model.entity.mid.MidEntity;
import model.item.Item;
import model.path.FindEntity;
import model.villager.intentions.Intent;
import model.villager.intentions.IntentionHandler;
import model.villager.intentions.SocialiseInitIntent;
import model.villager.intentions.SocialiseIntent;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.DieAction;
import model.villager.intentions.plan.DrinkPlan;
import model.villager.intentions.plan.EatPlan;
import model.villager.intentions.plan.ExplorePlan;
import model.villager.intentions.plan.IdlePlan;
import model.villager.intentions.plan.Plan;
import model.villager.intentions.plan.SleepPlan;
import model.villager.intentions.plan.SocialiseInitPlan;
import model.villager.intentions.plan.SocialisePlan;
import model.villager.intentions.reminder.ProfessionLine;
import model.villager.intentions.reminder.ProfessionLine.WorkLevel;
import model.villager.intentions.reminder.profession.WaterGatherer;
import model.villager.order.Order;
import model.villager.util.NameGen;
import util.Constants;
import util.EntityType;
import util.UtilClass;

public class Villager extends MidEntity implements Agent {

	private static final long serialVersionUID = 1L;

	private IntentionHandler ih;
		
	private AgentWorld world;
	private boolean dead = false, isElder = false;

	private String currentAction, currentPlan;
	private String name;
	private Plan activePlan;
	private ProfessionLine profession;
	private boolean mustExplore, isShowUI = false;
	private int length, weight;
	private int age,ageprog;
	private int sex; // 0 -female, 1 - male
	private int deathrisk; //The deathrisk based on age.
	private int modifier;
	private Point myBed = null, northwestVillageCorner = new Point(1,1), southeastVillageCorner = new Point(2,2);
	private int time, home;
	private Item activeItem;
	private Item[] inventory = new Item[6];

	private HashMap<Point, Villager> nearbyVillagers;
	private HashMap<Point, Agent> nearbyAgents;
	
	// Characteristics (act as modifiers)
	private float hunger, thirst, speed, sleepiness, laziness, obedience;
	private float social = 0.5f; // Need of other people; [0,1] where 0 is a loner  

	// Current needs (are adjusted all the time)
	private float currentHunger, currentThirst, currentSleepiness, currentLaziness, currentSocial;

	// Limits, i.e. when we should trigger actions) (modified by modifiers)
	private float socialLimit = 10; // TODO update

	public Villager(Point p, int age, int village){
		super(p.x, p.y, EntityType.VILLAGER);
		world = new AgentWorld();
		length = 140 + UtilClass.getRandomInt(50, 0);
		weight = length / 4 + UtilClass.getRandomInt(length/4, 0);
		this.home = village;
		profession = new WaterGatherer(this, WorkLevel.MEDIUM);
		
		this.age=age;
		ageprog=0;
		deathrisk=1;
		ih = new IntentionHandler(this);
		
		this.sex = UtilClass.getRandomInt(2, 0);
		if(sex == 0){
			this.name = NameGen.newName(true);
		}else{
			this.name = NameGen.newName(false);
		}
		
		//Randomize starting values for needs, wants and stats.
		this.hunger = UtilClass.getRandomInt(20, 1);
		this.thirst = UtilClass.getRandomInt(20, 1);
		this.sleepiness = UtilClass.getRandomInt(20, 1);
		this.speed = UtilClass.getRandomInt(10, 25);
		this.laziness = UtilClass.getRandomInt(20, 1);
		this.obedience = UtilClass.getRandomInt(20, 1);
		this.modifier = UtilClass.getRandomInt(5, 1);
		this.social = 40;
		
		this.currentHunger = 50-modifier*hunger;
		this.currentThirst = 50-modifier*thirst;
		this.currentSleepiness = 40-modifier*sleepiness;
		this.currentLaziness = 50-modifier*laziness;
		this.currentSocial = 0f;
		
		currentAction = "Doing Nothing";
		currentPlan = "Doing Nothing";
		
		System.out.println("New villager created: " + name+ " " +length+"  "+weight+ " " +sex);
	}
	
	public AgentWorld getWorld() {
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
		world.updateTopEntities(p.topEntities);
		
		nearbyVillagers = p.villagers;
		nearbyAgents = p.agents;		

		northwestVillageCorner = p.northwestVillageCorner;
		southeastVillageCorner = p.southeastVillageCorner;
		
		ageprog++;
		seeIfBirthday();
		adjustNeeds();
		
		// If we see any other villagers, we may initiate an interaction
		if (p.hasVillagers()) {
			maybeSocialise(p.villagers);
		}
		
		// If order was received, take it into consideration when planning
		if (p.order != null) {
			addOrder(p.order);
		}
		
		//seeIfDead();
		plan();
		
		if(profession != null)
			profession.update(time);
		
		if(activePlan.getActiveAction() == null){
			disposePlan();
			plan();
		}
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
	
	/**
	 * Maybe initialise an interaction with another villager,
	 * if our social need/relation is high enough.
	 */
	private void maybeSocialise(HashMap<Point, Villager> villagers) {

		if (currentSocial < socialLimit) {		
			// Abort if already socialising (TODO should be smarter?)
			if (activePlan instanceof SocialiseInitPlan || activePlan instanceof SocialisePlan) {
				return;
			}
			
			// Initialise a social interaction
			Entry<Point, Villager> other = getBestFriend(villagers);
			Villager otherVillager = other.getValue();
			Point otherPos = other.getKey();
			
			// Find out where we should meet
			Point nearbyPos = FindEntity.findTileNeighbour(otherVillager.getWorld(), this.getPosition(), otherPos);
			
//			System.out.println("I am at "+this.getPosition()+", you should go to "+nearbyPos);
			
			// Create SocialiseIntent and order for other villager
			Intent othersIntent = new SocialiseIntent(otherVillager, nearbyPos, this.getId());
			Order socialiseOrder = new Order(this.getId(), otherVillager.getId(), othersIntent);
			
			// Create SocialiseInitIntent for myself
			SocialiseInitIntent initIntent = new SocialiseInitIntent(this, socialiseOrder, nearbyPos, otherVillager.getId());
			// TODO is weighted properly?
//			ih.addIntent(initIntent);
		}
		
	}
	
	/**
	 * Looks through a hashmap of villager, and finds the one I like the most.
	 * Is based on my relationship with other villagers
	 * (Atm no relationships, so is hardcoded)
	 * 
	 * @param villagers - A hashmap of villagers I should choose from 
	 * @return my best friend (of those available)
	 */
	private Entry<Point, Villager> getBestFriend(HashMap<Point, Villager> villagers) {
		
		// The best match (currently, selects the villager with the lowest ID)
		Entry<Point, Villager> bestFriend = null;
		int bestFriendsId = -1;
		
		// Loop through the hashmap
		Iterator<Entry<Point,Villager>> it = villagers.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Point, Villager> entry = it.next();
			Villager v = (Villager)entry.getValue();
			// Replace best friend if this one is a better match. Harsh
			if (bestFriendsId == -1 || v.getId()<bestFriendsId) {
				bestFriend = entry;
				bestFriendsId = v.getId();
			}
		}
		
		if (bestFriend == null) {
			System.err.println("Warning: No best friend found!");
		}
		return bestFriend;
	}

	public void satisfyHunger(float f) {
		this.currentHunger += f;
		if(currentHunger > Constants.MAX_HUNGER){
			currentHunger = Constants.MAX_HUNGER;
			if(activePlan instanceof EatPlan){
				disposePlan();
				plan();
			}
		}
	}
	
	public void satisfyThirst(float f) {
		this.currentThirst += f;
		if(currentThirst > Constants.MAX_THIRST){
			currentThirst = Constants.MAX_THIRST;
			if(activePlan instanceof DrinkPlan){
				disposePlan();
				plan();
			}
		}
	}
	
	public void satisfySleep(float f){
		this.currentSleepiness += f;
		if(currentSleepiness > Constants.MAX_SLEEP){
			currentSleepiness = Constants.MAX_SLEEP;
			if(activePlan instanceof SleepPlan){
				disposePlan();
				plan();
			}
		}
	}
	
	public void satisfyLazy(float f){
		this.currentLaziness += f;
		if(currentLaziness > Constants.MAX_LAZINESS){
			currentLaziness = Constants.MAX_LAZINESS;
			if(activePlan instanceof IdlePlan){
				disposePlan();
				plan();
			}
		}
	}
	
	public void setExplore(){
		mustExplore=true;
	}
	
	private void adjustNeeds() {
		int hours = time / Constants.TICKS_HOUR;
		
		if(hours >= Constants.NIGHT_HOUR || hours < Constants.DAY_HOUR){
			currentHunger = currentHunger - (hunger*0.0005f);
			currentThirst = currentThirst - (thirst*0.0005f);
			currentSocial -= social*0.0005f;
		}else{
			currentHunger = currentHunger - (hunger*0.001f);
			currentThirst = currentThirst - (thirst*0.001f);
			currentSocial -= social*0.001f;
		}
		
		currentSleepiness = currentSleepiness - (sleepiness*0.001f);
		currentLaziness = currentLaziness - (laziness*0.001f);
	}
	
	private void seeIfDead() {
		if(hunger < -Constants.MAX_HUNGER || thirst < -Constants.MAX_THIRST || UtilClass.getRandomInt(1000000, deathrisk) >= 1000000) {
			dead = true;
		}
	}
	
	private void seeIfBirthday(){
		if(ageprog>100){
			age++;
			ageprog=0;
			if(age>30){				
				deathrisk++;				
			}
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
	
	public void workReminder(Plan plan){
		this.disposePlan();
		activePlan = plan;
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

	public float getHowHungry() {
		return hunger;
	}

	public float getHowThirsty() {
		return thirst;
	}
	
	public float getHowSleepy(){
		return sleepiness;
	}
	
	public float getHowLazy(){
		return laziness;
	}
	
	public float getHunger() {
		return currentHunger;
	}

	public float getThirst() {
		return currentThirst;
	}
	
	public float getSleepiness(){
		return currentSleepiness;
	}
	
	public float getLaziness(){
		return currentLaziness;
	}
	
	public float getSocial() {
		return currentSocial;
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
	
	//Naming? Should return the lowest value, but its the mostly needed one since needs are negative
	public float getMostNeed(){
		return UtilClass.findMin(currentHunger, currentThirst, currentSleepiness, currentLaziness);
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
			pcs.firePropertyChange("status", currentHunger, "hunger");
			pcs.firePropertyChange("status", currentThirst, "thirst");
			pcs.firePropertyChange("status", currentSleepiness, "sleepiness");
			pcs.firePropertyChange("status", currentSocial, "social");

			pcs.firePropertyChange("status", currentAction, "action");
			pcs.firePropertyChange("status", currentPlan, "plan");
		}
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

		Villager copy = new Villager(new Point(x,y),age, home);
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
	
	public HashMap<Point, Villager> getNearbyVillagers(){
		return nearbyVillagers;
	}
	
	public HashMap<Point, Agent> getNearbyAgents(){
		return nearbyAgents;
	}

	@Override
	public AgentWorld getAgentWorld() {
		return world;
	}
	
	public void makeElder(){
		isElder = true;
		pcs.firePropertyChange("status", true, "elder");
	}
	
	/**
	 * A method to check whether or not a point is inside the villagers village.
	 * 
	 * @param pos the point to be checked.
	 * @return true if inside, false otherwise.
	 */
	public boolean isInsideVillage(Point pos){		
		return pos.x>=northwestVillageCorner.x && pos.x<=southeastVillageCorner.x
				&& pos.y>=northwestVillageCorner.y && pos.y<=southeastVillageCorner.y;
	}
	
	public int getHome(){
		return home;
	}
}
