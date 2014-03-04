package controller;

import model.World;

import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

import util.Tickable;
import view.View;


/**
 * The general (abstract) Controller class which is set up with slick2D
 *
 */
public abstract class Controller implements KeyListener, MouseListener /*, MouseMotionListener*/, Tickable{

	protected World gameState;
	protected View view;
	
	public Controller(World gameState, View view){
		this.gameState = gameState;
		this.view = view;
	}
	
}
