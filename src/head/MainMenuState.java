package head;

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

import view.MainMenuView;

public class MainMenuState implements GameState {
	
	private MainMenuView view = new MainMenuView();
	// Our parent, the state manager 
	private static StatedGame game;
	private static AppGameContainer container;
	private boolean isFullscreen = false;


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
			// Tell parent to change from main menu state to game state
			game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			break;
		case Input.KEY_2:
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
			
			break;
		case Input.KEY_3:
			((StatedGame)game).exit();
			break;
		default:
			break;
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
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = (StatedGame) game;
		this.container = (AppGameContainer) container;

	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

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
