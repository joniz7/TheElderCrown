package controller;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.RandomWorld;
import model.World;
import model.WorldMap;
import model.entity.Agent;
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

import util.ImageLoader;
import view.WorldView;

public class WorldController implements GameState {

	private boolean isWDown, isADown, isSDown, isDDown;
	
	private World world;
	private WorldView view;
	private boolean isExit;
	private GameContainer appgc;
	private Game game;
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// Debugging order system
		if (button == 0) {
			Point windowPos = new Point(x,y);
			sendMoveOrder(windowPos);
		}else if (button == 1){
			Point windowPos = new Point(x,y);
			showVillagerInfo(windowPos);
		}
	}
	
	/**
	 * Send a move order to the specified position
	 */
	private void sendMoveOrder(Point windowPos) {
		// Figure out where we want to go
		Point modelPos = WorldView.windowToModelCoordinates(windowPos);
		// Get the first villager from world
		Villager v = (Villager) world.getAgents().values().toArray()[0];
		// Create order for this villager to move to the clicked position
		MoveIntent i = new MoveIntent(0, 900, v, modelPos);
		Order o = new Order(0, v.getId(), i);
		// Add order to world
		world.addOrder(o);
	}
	
	/**
	 * Maybe show a popup containing info about a villager
	 * TODO document
	 * @param windowPos - ?
	 */
	private void showVillagerInfo(Point windowPos){
		Point modelPos = WorldView.windowToModelCoordinates(windowPos);
		Villager v;
		
		HashMap<Point, Agent> temp = (HashMap<Point, Agent>)world.getAgents().clone();
		Iterator<Map.Entry<Point, Agent>> it = temp.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<Point, Agent> e = (Map.Entry<Point, Agent>) it.next();
			
			Agent agent = e.getValue();
			if(agent instanceof Villager){
				v = (Villager) agent;
				v.setShowUI(false);
			}
		}
		
		if(world.getMidEntities().get(modelPos) instanceof Villager){
			
			v = (Villager) world.getMidEntities().get(modelPos);
			v.setShowUI(true);
		}
	}

	

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

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
		System.out.println("worldController keypresid");
		if(e == 'w' || e == 'W')
			isWDown = true;
		if(e == 's' || e == 'S')
			isSDown = true;
		if(e == 'a' || e == 'A')
			isADown = true;
		if(e == 'd' || e == 'D')
			isDDown = true;
		if(key == Input.KEY_ESCAPE){
			getGame().enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
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
	 */
	public void init(GameContainer appgc, StateBasedGame game)
			throws SlickException {
		this.game = (Game)game;
		this.appgc = appgc;
		new ImageLoader();
		
		RandomWorld gen = new RandomWorld(); 
		
		world = new RandomWorld();
		view = new WorldView(appgc.getWidth(), appgc.getHeight());
		// Set up View listening to World
		world.addPropertyChangeListener(view);
		// Initialize with no map
		world.initialize();
		isExit = false;

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
		WorldView.render(g);

	}

	@Override
	/**
	 * Updates logic for the state. Runs continuously while state is entered
	 */
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		world.tick();
		moveCamera();
		if(isExit){
			getGame().exit();
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
	 * @param f - the file to save to
	 */
	public void saveWorldMap(File f) {
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
		} catch (FileNotFoundException e) {
			System.out.println("Encountered FileNotFoundException in saveWorldMap(): "+e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Encountered IOException in saveWorldMap(): "+e.toString());
			e.printStackTrace();
		}
		
	}
	
}
