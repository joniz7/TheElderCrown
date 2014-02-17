package head;

import model.World;
import model.TestWorld;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import resource.ImageLoader;

import view.View;

import control.Controller;
import control.WorldController;

/**
 * The main class to keep track of everything in the game.
 * 
 * Implemented with Slick2D.
 * 
 * @author Teodor O, Simon E
 * @version 0.0.05
 */
public class GameSlick implements Game{

	private static AppGameContainer appgc;
	private Controller controller;
	private World world;
	private View view;
	private static String title = "The Elder Crown";
	private static boolean isExit;
	private static final int TICK_INTERVAL = 20;
	
	@Override
	public boolean closeRequested() {
		isExit = true;
		return isExit;
	}

	@Override
	/**
	 * So far hard coded to return the name 'The Elder Crown'
	 */
	public String getTitle() {
		return title;
	}

	@Override
	/**
	 * The initialising method that creates the necessary components and 
	 * connects them properly.
	 */
	public void init(GameContainer arg0) throws SlickException {
		new ImageLoader();
		
		world = new TestWorld();
		
		appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), true);
		appgc.setTitle(getTitle());
		
		view = new View(appgc.getWidth(), appgc.getHeight());
		controller = new WorldController(this, world, view);
		appgc.getInput().addKeyListener(controller);
		
		// Set up View listening to World
		world.addPropertyChangeListener(view);
		
		world.initialize();
		
		isExit = false;
	}

	@Override
	/**
	 * This function simply tells the 'View' to render all the things!!!
	 */
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		View.render(g);
	}

	@Override
	/**
	 * In this method we simply tell the active world of the game to 'tick' one step ahead.
	 * Also if any part of the game has requested a shutdown, that is executed here.
	 */
	public void update(GameContainer arg0, int arg1) throws SlickException {
		world.tick();
		controller.tick();
		if(isExit){
			appgc.destroy();
		}
	}
	
	/**
	 * A method to be called whenever someone wants to close down the program.
	 */
	public static void requestClose(){
		isExit = true;
	}
	
	/**
	 * Just the starting point of the game. Creates the 'Frame' and sets it free.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		GameSlick game = new GameSlick();
		try {
			appgc = new AppGameContainer(game);
			appgc.setMaximumLogicUpdateInterval(TICK_INTERVAL);
			appgc.setMinimumLogicUpdateInterval(TICK_INTERVAL);
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static Graphics getG(){
		return appgc.getGraphics();
	}
	
	public static AppGameContainer getAppGC(){
		return appgc;
	}

}
