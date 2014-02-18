package control;

import head.GameSlick;
import model.GamePhase;
import org.newdawn.slick.Input;

public class WorldController extends Controller{

	private boolean isWDown, isADown, isSDown, isDDown;
	private GameSlick game;
	
	public WorldController(GameSlick game, GamePhase gameState) {
		super(gameState);
		this.game = game;
	}

	@Override
	/**
	 * The method that, so far, pans the view.
	 */
	public void tick() {
		// multiple 'ifs' and not 'if else' due to that many should be active at once.
		if(isWDown)
			gameState.decViewY();
		if(isSDown)
			gameState.incViewY();
		if(isADown)
			gameState.decViewX();
		if(isDDown)
			gameState.incViewX();
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
			System.exit(0);
			game.closeRequested();
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
