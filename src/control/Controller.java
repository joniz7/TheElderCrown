package control;

import head.Tickable;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.GameState;

public abstract class Controller implements KeyListener, MouseListener, MouseMotionListener, Tickable{

	protected GameState gameState;
	
	public Controller(GameState gameState){
		this.gameState = gameState;
	}
	
}
