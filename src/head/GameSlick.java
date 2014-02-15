package head;

import model.GamePhase;
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
	private GamePhase phase;
	private View view;
	private static String title = "The Elder Crown";
	private static boolean isExit;

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
		
		phase = new TestWorld();
		controller = new WorldController(this, phase);
		
		appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), true);
		appgc.getInput().addKeyListener(controller);
		appgc.setTitle(getTitle());
		view = new View(appgc.getWidth(), appgc.getHeight());
		
		isExit = false;
	}

	@Override
	/**
	 * This function simply tells the 'View' to render all the things!!!
	 */
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		view.render(g, phase.getViewX(), phase.getViewY());
		
	}

	@Override
	/**
	 * In this method we simply tell the active phase of the game to 'tick' one step ahead.
	 * Also if any part of the game has requested a shutdown, that is executed here.
	 */
	public void update(GameContainer arg0, int arg1) throws SlickException {
		phase.tick();
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
			appgc.setMaximumLogicUpdateInterval(5);
			appgc.setMinimumLogicUpdateInterval(5);
			appgc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
