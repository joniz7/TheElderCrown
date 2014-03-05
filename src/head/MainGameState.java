package head;

import model.TestWorld;
import model.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import util.ImageLoader;
import view.View;
import controller.Controller;
import controller.WorldController;

public class MainGameState implements GameState {

	private Controller controller;
	private World world;
	private View view;
	private static boolean isExit;
	private static GameContainer appgc;
	private static StatedGame game;
	
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

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
		return false;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		controller.keyPressed(arg0, arg1);

	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		controller.keyReleased(arg0, arg1);

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
		this.game = (StatedGame)game;
		this.appgc = appgc;
		new ImageLoader();
		
		world = new TestWorld();
		view = new View(appgc.getWidth(), appgc.getHeight());
		controller = new WorldController(this, world, view);
		appgc.getInput().addKeyListener(controller);
		appgc.getInput().addMouseListener(controller);
		// Set up View listening to World
		world.addPropertyChangeListener(view);
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
		View.render(g);

	}

	@Override
	/**
	 * Updates logic for the state. Runs continuously while state is entered
	 */
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		world.tick();
		controller.tick();
		if(isExit){
			getGame().exit();
		}

	}
		
	public boolean closeRequested() {
		isExit = true;
		return isExit;
		}

	public static Graphics getG(){
		return appgc.getGraphics();
	}
	
	public static GameContainer getGC(){
		return appgc;
	}
	
	public StatedGame getGame(){
		return game;
	}
	
}
