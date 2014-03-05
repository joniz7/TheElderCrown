package controller;

import head.MainGameState;

import java.awt.Point;

import model.World;
import model.entity.Agent;
import model.villager.Villager;
import model.villager.intentions.MoveIntent;
import model.villager.order.Order;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import view.View;

public class WorldController extends Controller{

	private boolean isWDown, isADown, isSDown, isDDown;

	private MainGameState game;
	
	public WorldController(MainGameState mainGameState, World gameState, View view) {
		super(gameState, view);
		this.game = mainGameState;
	}
	
	public WorldController(World gameState, View view) {
		super(gameState, view);
	}

	@Override
	/**
	 * The method that, so far, pans the view.
	 */
	public void tick() {
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
		if(key == Input.KEY_ESCAPE){
			game.getGame().enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
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
	/**
	 * A method that triggers when input ends.
	 */
	public void inputEnded() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		
		
	}

	
	// Mouse methods
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// Debugging order system
		if (button == 0) {
			Point windowPos = new Point(x,y);
			sendMoveOrder(windowPos);
		}
	}
	
	/**
	 * Send a move order to the specified position
	 */
	private void sendMoveOrder(Point windowPos) {
		// Figure out where we want to go
		Point modelPos = View.windowToModelCoordinates(windowPos);
		// Get the first villager from world
		Villager v = (Villager) world.getAgents().values().toArray()[0];
		// Create order for this villager to move to the clicked position
		MoveIntent i = new MoveIntent(0, 900, v, modelPos);
		Order o = new Order(0, v.getId(), i);
		// Add order to world
		world.addOrder(o);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub
//		System.out.println("mouseDragged");
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub
//		System.out.println("mouseMoved");
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// TODO Auto-generated method stub
//		System.out.println("mousepressed");
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
//		System.out.println("mouseReleased");
	}

	@Override
	public void mouseWheelMoved(int change) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
