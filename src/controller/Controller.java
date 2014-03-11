package controller;

import model.World;

import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;

import util.Tickable;
import view.MainGameView;


/**
 * The general (abstract) Controller class which is set up with slick2D
 *
 */
public abstract class Controller implements KeyListener, MouseListener /*, MouseMotionListener*/, Tickable{

	protected World world;
	protected MainGameView view;
	
	public Controller(World world, MainGameView view){
		this.world = world;
		this.view = view;
	}
	
}
