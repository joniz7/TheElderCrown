package control;

import head.Tickable;
import model.World;

import org.newdawn.slick.KeyListener;

import view.View;

/**
 * The general (abstract) Controller class which is set up with slick2D
 *
 */
public abstract class Controller implements KeyListener/*, MouseListener, MouseMotionListener*/, Tickable{

	protected World gameState;
	protected View view;
	
	public Controller(World gameState, View view){
		this.gameState = gameState;
		this.view = view;
	}
	
}
