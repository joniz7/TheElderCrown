package model.intent;
import model.World;
/**
 * Represents an agents intent to do something.
 * @author Jonathan Orrö
 *
 */

public interface Intent {
	/**
	 * Lets the world decide wether to allow the agent to do its intent or not.
	 * @param world - The world that decides whether the intent is acceptable or not.
	 */
	public void tick(World world);
}
