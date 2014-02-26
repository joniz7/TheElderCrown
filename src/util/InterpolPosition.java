package util;

/**
 * A class containing position, direction and progress information. 
 *  
 * @author Niklas
 */
public class InterpolPosition {

	// Our originating position
	private int x, y;
	// In which direction we're going
	private int dx, dy;
	// How far we've come (is [0,1])
	private double progress;

	public InterpolPosition(int x, int y, int dx, int dy, double progress) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.progress = progress;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getDx() {
		return dx;
	}
	public int getDy() {
		return dy;
	}
	
	public double getProgress() {
		return progress;
	}
	
}
