package util;

/**
 * A collection of all the values that are constant in the game and more than one class might need.
 * @author Teodor O
 *
 */
public class Constants {
	public static final int 
	WORLD_WIDTH = 200, WORLD_HEIGHT = 200,
	DOWN_ENTRANCE = 0, LEFT_ENTRANCE = 90,
	UP_ENTRANCE = 180, RIGHT_ENTRANCE = 270,
	ROTATE_CLOCKWISE = 90, ROTATE_HALF_TURN = 180, ROTATE_COUNTERCLOCKWISE = 270,
	MAX_HUNGER = 80, MAX_THIRST = 80, MAX_SLEEP = 80, MAX_LAZINESS = 80, MAX_SOCIAL = 80,
	NIGHT_HOUR = 22, DAY_HOUR = 8, TICKS_HOUR = 750,
	ORDER_DESIRE = 9000, INIT_SOCIALISE_DESIRE = 4000, JOIN_SOCIALISE_DESIRE = 4001,
	NEGATIVE_SOCIAL_IMPACT = 1; // How much we should be offended when ignored 
}
