package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Timer;

import model.MapWorld;
import model.RandomWorld;
import model.World;
import model.WorldMap;
import model.entity.Agent;
import model.entity.Entity;
import model.entity.top.Tree;
import model.entity.top.house.DrinkStorage;
import model.entity.top.house.FoodStorage;
import model.entity.bottom.*;
import model.villager.Villager;
import model.villager.intentions.MoveIntent;
import model.villager.order.Order;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import util.EntityType;
import util.ImageLoader;
import util.NoPositionFoundException;
import view.WorldView;

public class WorldController implements GameState {

	private boolean isWDown, isADown, isSDown, isDDown;
	// How strong the desire to move on mouse click should be
	public static final int MOVE_ORDER_DESIRE = 900;
	
	protected PropertyChangeSupport pcs;
	
	private World world;
	private WorldView view;
	private boolean isExit;
	private GameContainer appgc;
	private Game game;
	private Timer timer;
	ActionListener action;
	//Delay in ms between ticks
	private int tickDelay;
	private int simSpeed;
	
	// The currently selected entity. Is changed by mouse clicking
	private Villager selectedVillager;
	private Tree selectedTree;
	private FoodStorage selectedFoodStorage;
	private DrinkStorage selectedDrinkStorage;
	private Bed selectedBed;
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		Point windowPos = new Point(x,y);
		if (button == 0) {
			selectEntity(windowPos);
			selectTree(windowPos);
			selectFoodStorage(windowPos);
			selectDrinkStorage(windowPos);
		}else if (button == 1){
			sendMoveOrder(windowPos);
		}
	}
	
	/**
	 * Send a move order to the specified position
	 */
	private void sendMoveOrder(Point windowPos) {
		// Figure out where we want to go
		Point modelPos = WorldView.windowToModelCoordinates(windowPos);
		
		// Select receiving villager
		Villager receiver;
		if (selectedVillager == null) {
			// Get the first villager from world
			receiver = (Villager) world.getAgents().values().toArray()[0]; 
		} else {
			receiver = selectedVillager;
		}
		 
		// Create order for this villager to move to the clicked position
		MoveIntent i = new MoveIntent(0, MOVE_ORDER_DESIRE, receiver, modelPos);
		Order o = new Order(0, receiver.getId(), i);

		// Add order to world
		world.addOrder(o);
	}
	
	/**
	 * Marks the entity at the clicked position as the currently selected entity.
	 * Maybe shows a popup containing info about a villager
	 * 
	 * @param windowPos - the window coordinates
	 */
	@SuppressWarnings("unchecked")
	private void selectEntity(Point windowPos){
		Point modelPos = WorldView.windowToModelCoordinates(windowPos);
		
		HashMap<Point, Agent> temp = (HashMap<Point, Agent>)world.getAgents().clone();
		Iterator<Map.Entry<Point, Agent>> it = temp.entrySet().iterator();
		
		// Hide all villagers' UIs
		while(it.hasNext()){
			Map.Entry<Point, Agent> e = (Map.Entry<Point, Agent>) it.next();
			Agent agent = e.getValue();
			if(agent instanceof Villager){
				Villager v = (Villager) agent;
				v.setShowUI(false);
			}
		}
		
		// Change current selection maybe show Villager UI
		Entity e = world.getMidEntities().get(modelPos);
		if(e instanceof Villager){
			selectedVillager = (Villager) e;
			selectedVillager.setShowUI(true);
		} else {
			selectedVillager = null;
		}
		
		//same for beds
		HashMap<Point, BottomEntity> temp2 = (HashMap<Point, BottomEntity>) world.getBotEntities().clone();
		Iterator<Map.Entry<Point, BottomEntity>> iterator = temp2.entrySet().iterator();
		
		//hide all beds UIs
		while(iterator.hasNext()){
			Map.Entry<Point, BottomEntity> b = iterator.next();
			BottomEntity bot = b.getValue();
			if(bot.getType() == EntityType.BED){
				selectedBed = (Bed) bot;
				selectedBed.setShowUI(false);
			}
		}
		
		//Maybe show a UI
		Entity tile = world.getBotEntities().get(modelPos);
		if(tile != null && tile.getType() == EntityType.BED){
			selectedBed = (Bed) tile;
			selectedBed.setShowUI(true);
		}
	}
	
	/**
	 * Marks the entity at the clicked position as the currently selected entity.
	 * Maybe shows a popup containing info about a tree
	 * 
	 * @param windowPos - the window coordinates
	 */
	@SuppressWarnings("unchecked")
	private void selectTree(Point windowPos){
		Point modelPos = WorldView.windowToModelCoordinates(windowPos);
		
		HashMap<Point, Entity> temp = (HashMap<Point, Entity>)world.getTopEntities().clone();
		Iterator<Map.Entry<Point, Entity>> it = temp.entrySet().iterator();
		
		// Hide all Trees' UIs
		while(it.hasNext()){
			Map.Entry<Point, Entity> e = (Map.Entry<Point, Entity>) it.next();
			//System.out.println();
			Entity agent = e.getValue();
			if(agent instanceof Tree){
				Tree t = (Tree) agent;
				t.setShowUI(false);
			}
		}
		// Change current selection maybe show Villager UI
		Entity e = world.getTopEntities().get(modelPos);
		if(e instanceof Tree){
			selectedTree = (Tree) e;
			selectedTree.setShowUI(true);
		} else {
			selectedTree = null;
		}
	}
	
	/**
	 * Marks the entity at the clicked position as the currently selected entity.
	 * Maybe shows a popup containing info about a tree
	 * 
	 * @param windowPos - the window coordinates
	 */
	@SuppressWarnings("unchecked")
	private void selectFoodStorage(Point windowPos){
		Point modelPos = WorldView.windowToModelCoordinates(windowPos);
		
		HashMap<Point, Entity> temp = (HashMap<Point, Entity>)world.getTopEntities().clone();
		Iterator<Map.Entry<Point, Entity>> it = temp.entrySet().iterator();
		
		// Hide all Trees' UIs
		while(it.hasNext()){
			Map.Entry<Point, Entity> e = (Map.Entry<Point, Entity>) it.next();
			//System.out.println("");
			Entity agent = e.getValue();
			if(agent instanceof FoodStorage){
				FoodStorage t = (FoodStorage) agent;
				t.setShowUI(false);
			}
		}
		// Change current selection maybe show Villager UI
		Entity e = world.getTopEntities().get(modelPos);
		if(e instanceof FoodStorage){
			selectedFoodStorage = (FoodStorage) e;
			selectedFoodStorage.setShowUI(true);
		} else {
			selectedFoodStorage = null;
		}

	}	



	@SuppressWarnings("unchecked")
	private void selectDrinkStorage(Point windowPos){
		Point modelPos = WorldView.windowToModelCoordinates(windowPos);
		
		HashMap<Point, Entity> temp = (HashMap<Point, Entity>)world.getTopEntities().clone();
		Iterator<Map.Entry<Point, Entity>> it = temp.entrySet().iterator();
		
		// Hide all Trees' UIs
		while(it.hasNext()){
			Map.Entry<Point, Entity> e = (Map.Entry<Point, Entity>) it.next();
			Entity agent = e.getValue();
			if(agent instanceof DrinkStorage){
				DrinkStorage t = (DrinkStorage) agent;
				t.setShowUI(false);
			}
		}
		// Change current selection maybe show Villager UI
		Entity e = world.getTopEntities().get(modelPos);
		if(e instanceof DrinkStorage){
			selectedDrinkStorage = (DrinkStorage) e;
			selectedDrinkStorage.setShowUI(true);
		} else {
			selectedDrinkStorage = null;
		}
	}
	
	

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		world.setcPos(new Point(arg2, arg3));
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * Sets booleans to 'true' if the corresponding button is pressed.
	 */
	public void keyPressed(int key, char e) {
		if(e == 'w' || e == 'W')
			isWDown = true;
		if(e == 's' || e == 'S')
			isSDown = true;
		if(e == 'a' || e == 'A')
			isADown = true;
		if(e == 'd' || e == 'D')
			isDDown = true;
		if(e == '+' ){
			simSpeed++;
			time();
		}
		if(e=='-'){
			simSpeed--;
			time();
		}			
			
		if(key == Input.KEY_ESCAPE){
			timer.stop();
			getGame().enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		if (key == Input.KEY_SPACE) {
			centerCameraOnEntity();
		}
	}


	@Override
	/**
	 * Sets booleans to 'false' if the corresponding button is released.
	 */
	public void keyReleased(int key, char e) {
		if(e == 'w' || e == 'W')
			isWDown = false;
		if(e == 's' || e == 'S')
			isSDown = false;
		if(e == 'a' || e == 'A')
			isADown = false;
		if(e == 'd' || e == 'D')
			isDDown = false;		
	}
	
	/**
	 * Moves the camera to the selected entity
	 */
	private void centerCameraOnEntity() {
		if (selectedVillager != null) {
			try {
				Point p = world.getPosition(selectedVillager);
				view.centerCamera(p);
			} catch (NoPositionFoundException e) {
				System.out.println("Error: No position found while centering camera");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void controllerButtonPressed(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerButtonReleased(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerDownPressed(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerDownReleased(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerLeftPressed(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerLeftReleased(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerRightPressed(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerRightReleased(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerUpPressed(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void controllerUpReleased(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		int h = container.getHeight();
		int w = container.getWidth();
		view.setSize(w, h);

	}

	@Override
	public int getID() {
		return 2;
	}

	@Override
	/**
	 * Called when the state first in initialized by StatedGame
	 * Note: Do not forget to initialize a world as well!
	 */
	public void init(GameContainer appgc, StateBasedGame game)
			throws SlickException {
		this.game = (Game)game;
		this.appgc = appgc;
		pcs = new PropertyChangeSupport(this);
		new ImageLoader();
		tickDelay=90;
		simSpeed=5;
		
		//Actionlistener that listens to timer with tickDelay
        action = new ActionListener(){   
            @Override
            public void actionPerformed(ActionEvent event) {
               world.tick();
            }
        };
        timer=new Timer(tickDelay, action);
        timer.setInitialDelay(0);
	}
	
	/**
	 * Creates a new RandomWorld.
	 * Also creates its view and sets up view bindings
	 */
	public void initRandomWorld() {
		world = new RandomWorld();
		//timer.start();
		view = new WorldView(appgc.getWidth(), appgc.getHeight());
		addPropertyChangeListener(view);
		// Set up View listening to World
		world.addPropertyChangeListener(view);
		// Initialize with no map
		world.initialize();
		isExit = false;
		
		// Allow the user to see the game
		game.setGameInitialized(true);
	}
	
	/**
	 * Creates a new MapWorld using the specified map.
	 * Also creates its view and sets up view bindings
	 * 
	 * @param name - the name of the map.
	 * 				"name.map" must exist in the maps/ directory!
	 */
	public void initMapWorld(String name) {
		
		File f = Paths.get("maps/"+name+".map").toFile();		
		WorldMap map = loadWorldMap(f);
		
		world = new MapWorld(map);
		timer.start();
		view = new WorldView(appgc.getWidth(), appgc.getHeight());
		// Set up View listening to World
		world.addPropertyChangeListener(view);
		world.initialize();
		isExit = false;
		// Allow the user to see the game
		game.setGameInitialized(true);
	}

	@Override
	/**
	 * Called every time the state is exited in favour of another state
	 */
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * Draws the game
	 */
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		try{
			view.render(g);
		}catch(Exception e){
			if(e instanceof ConcurrentModificationException){
				System.out.println("CONCURRENT MODIFICATION ERROR!!!");
			}
		}

	}

	@Override
	/**
	 * Updates logic for the state. Runs continuously while state is entered
	 */
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		moveCamera();
		if(isExit){
			getGame().exit();
		}
		if(!timer.isRunning()){
			timer.start();
		}

	}
	
	/**
	 * The method that, so far, pans the view.
	 */
	private void moveCamera() {
		// multiple 'ifs' and not 'if else' due to that many should be active at once.
		if(isWDown)
			view.decY();
		if(isSDown)
			view.incY();
		if(isADown)
			view.decX();
		if(isDDown)
			view.incX();
	}
		
	public boolean closeRequested() {
		isExit = true;
		return isExit;
	}

	public Graphics getGraphics(){
		return appgc.getGraphics();
	}
	
	public GameContainer getGameContainer(){
		return appgc;
	}
	
	public Game getGame(){
		return game;
	}
	
	/**
	 * Saves the map of the current word to the specified file.
	 * 
	 * @param f - the file to save to
	 * @return true if save succeeded, false if not
	 * @author Niklas
	 */
	public boolean saveWorldMap(File f) {
		System.out.println("Dumping world map...");
		WorldMap wm = world.getWorldMap();
		System.out.println("Saving world map to file...");
		
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);          
			oos.writeObject(wm);
			oos.flush();
			oos.close();
			fos.close();
			System.out.println("Successfully saved world map! File: "+f.getAbsolutePath().toString());
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Encountered FileNotFoundException in saveWorldMap(): "+e.toString());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Encountered IOException in saveWorldMap(): "+e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Loads a WorldMap object from file
	 * 
	 * @param f - a File containing a serialized WorldMap
	 * @return null if reading of file failed.
	 * @author Niklas
	 */
	public WorldMap loadWorldMap(File f) {
		
		WorldMap map = null;
		System.out.println("Loading world map...");
		try {
			FileInputStream fos = new FileInputStream(f);
			ObjectInputStream oos = new ObjectInputStream(fos);          
			map = (WorldMap) oos.readObject();
			oos.close();
			fos.close();
			System.out.println("Successfully loaded world map!");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("loadWorldMap() failed! file: "+f.toString());
			e.printStackTrace();
		}

		return map;
		
	}
	
	public void addListener(){

	}
	
	public void time(){
		double rate=1;
		if(simSpeed <=1){
			tickDelay=900;
			simSpeed =1;
			rate=0.1;
		}else if(simSpeed ==2){
			tickDelay=360;
			rate=0.25;
		}else if(simSpeed ==3){
			tickDelay=180;
			rate=0.5;
		}else if(simSpeed ==4){
			tickDelay=120;
			rate=0.75;
		}else if(simSpeed ==5){
			tickDelay=90;
			rate=1.0;
		}else if(simSpeed ==6){
			tickDelay=60;
			rate=1.5;
		}else if(simSpeed ==7){
			tickDelay=45;
			rate=2.0;
		}else if(simSpeed ==8){
			tickDelay=36;
			rate=2.5;
		}else if(simSpeed ==9){
			tickDelay=30;
			rate=3.0;
		}else if(simSpeed ==10){
			tickDelay=25;
			rate=3.5;
		}else if(simSpeed ==11){
			tickDelay=21;
			rate=4.0;
		}else if(simSpeed ==12){
			tickDelay=20;
			rate=4.5;
		}else if(simSpeed ==13){
			tickDelay=18;
			rate=5.0;
		}else if(simSpeed ==12){
			tickDelay=16;
			rate=5.5;
		}else if(simSpeed ==13){
			tickDelay=15;
			rate=6.0;
		}else if(simSpeed ==14){
			tickDelay=13;
			rate=6.5;
		}else if(simSpeed ==15){
			tickDelay=12;
			rate=7.0;
		}else if(simSpeed >=16){
			tickDelay = 1;
			simSpeed=16;
			rate=20.0;
		}
		pcs.firePropertyChange("simSpeed", null, rate);
		timer.stop();
		timer=new Timer(tickDelay, action);
		timer.setInitialDelay(0);
		timer.start();
		
	}
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

}
