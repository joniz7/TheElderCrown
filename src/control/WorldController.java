package control;

import head.Tickable;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import model.GamePhase;

public class WorldController extends Controller{

	private boolean isWDown, isADown, isSDown, isDDown;
	
	public WorldController(GamePhase gameState) {
		super(gameState);
	}

	@Override
	public void tick() {
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
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
			isWDown = true;
		if(e.getKeyChar() == 's' || e.getKeyChar() == 'S')
			isSDown = true;
		if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
			isADown = true;
		if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
			isDDown = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
			isWDown = false;
		if(e.getKeyChar() == 's' || e.getKeyChar() == 'S')
			isSDown = false;
		if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A')
			isADown = false;
		if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D')
			isDDown = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	

}
