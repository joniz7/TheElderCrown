package control;

import org.newdawn.slick.KeyListener;

import head.Tickable;

import model.GamePhase;

public abstract class Controller implements KeyListener/*, MouseListener, MouseMotionListener*/, Tickable{

	protected GamePhase gameState;
	
	public Controller(GamePhase gameState){
		this.gameState = gameState;
	}
	
}
