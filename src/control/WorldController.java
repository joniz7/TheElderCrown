package control;

import head.GameSlick;
import model.World;

import org.newdawn.slick.Input;

import view.View;

public class WorldController extends Controller{

	private boolean isWDown, isADown, isSDown, isDDown;
	private GameSlick game;
	
	public WorldController(GameSlick game, World gameState, View view) {
		super(gameState, view);
		this.game = game;
	}

	@Override
	public void tick() {
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
	public void inputEnded() {
		
		
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

	

}
