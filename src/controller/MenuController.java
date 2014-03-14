package controller;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import view.MenuView;

public class MenuController implements GameState {
	
	// Our parent, the state manager 
	private Game game;
	
	private AppGameContainer container;
	private MenuView view = new MenuView();
	
	private boolean isFullscreen = false;

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		System.out.println("helo");
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
	public void keyPressed(int key, char e) {
		// TODO: Anything to do here?
	}

	@Override
	public void keyReleased(int key, char e) {
		switch(key){
		case Input.KEY_1:
			unpause();
			break;
		case Input.KEY_2:
			initRandomWorld();
			break;
		case Input.KEY_3:
			initMapWorld();
			break;
		case Input.KEY_4:
			toggleFullscreen();
			break;
		case Input.KEY_5:
			game.exit();
			break;
		case Input.KEY_6:
			saveWorldMap();
			break;
		default:
			break;
		}

	}

	private void initMapWorld() {
		view.setMessage("Creating world...");
		game.getWorldController().initMapWorld("test");
		view.setMessage("Created world! You can now play.");
	}

	private void initRandomWorld() {
		view.setMessage("Creating random world...");
		game.getWorldController().initRandomWorld();
		view.setMessage("Created world! You can now play.");
	}

	/**
	 * Unpause the game, if possible.
	 * If game hasn't been started, shows error message
	 */
	private void unpause() {
		if (game.isGameInitialized()) {
			// Tell parent to change from main menu state to game state
			game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		else {
			view.setMessage("Create a game first!");
		}
	}
	
	
	/**
	 * Toggles fullscreen mode on/off
	 */
	private void toggleFullscreen() {
		if(!isFullscreen){
			int w = game.getNativeWidth();
			int h = game.getNativeHeight();
			try {
				container.setDisplayMode(w, h, true);
				isFullscreen = true;
			} catch (SlickException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			try {
				container.setDisplayMode(800, 600, false);
				isFullscreen = false;
			} catch (SlickException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * Creates a map from the current world,
	 * and saves it to disc.
	 */
	private void saveWorldMap() {
				
		if (game.isGameInitialized()) {
			// Save map
		    File f = null;
			try {
				f = File.createTempFile("temp", null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Tell user how it went
			if (game.getWorldController().saveWorldMap(f)) {
				view.setMessage("Successfully saved map to %TEMP%/"+f.getName()+"!");
			} else {
				view.setMessage("Failed to save map");
			}
		}
		// World needs to be initialized first
		else {
			view.setMessage("Cannot save map, as the world doesn't exist. Nothing. Exists.");
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
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// Shows the "paused" label
		if (game.isGameInitialized()) {
			view.setMessage("Paused");
		}
		else {
			view.resetMessage();
		}

	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = (Game) game;
		this.container = (AppGameContainer) container;

	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// Hide message text
		view.resetMessage();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		view.render(g);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

}
