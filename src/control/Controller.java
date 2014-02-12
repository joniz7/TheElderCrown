package control;

import head.Tickable;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.GamePhase;

public abstract class Controller implements KeyListener, MouseListener, MouseMotionListener, Tickable{

	protected GamePhase gameState;
	
	public Controller(GamePhase gameState){
		this.gameState = gameState;
	}
	
}
