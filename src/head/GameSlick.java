package head;

import model.GamePhase;
import model.TestWorld;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import view.View;

import control.Controller;
import control.WorldController;

public class GameSlick implements Game{

	private static AppGameContainer appgc;
	private Controller controller;
	private GamePhase phase;
	private View view;
	private static boolean isExit;

	@Override
	public boolean closeRequested() {
		return isExit;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		phase = new TestWorld();
		controller = new WorldController(this, phase);
		appgc.setFullscreen(true);
		appgc.getInput().addKeyListener(controller);
		view = new View(appgc.getWidth(), appgc.getHeight());
		isExit = false;
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		view.render(g, phase.getViewX(), phase.getViewY());
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		phase.tick();
		if(isExit){
			appgc.destroy();
		}
	}
	
	public static void requestClose(){
		isExit = true;
	}
	
	public static void main(String[] args){
		GameSlick game = new GameSlick();
		try {
			appgc = new AppGameContainer(game);
			appgc.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
