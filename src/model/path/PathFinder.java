package model.path;

import model.villager.VillagersWorldPerception;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

/**
 * PathFinder is 
 * 
 * 
 * @author Simon Eliasson
 *
 */
public class PathFinder {

	private static Path path;
	private static AStarPathFinder pathFinder;
	
	/**
	 * This constructor will not exist in the future, when villagers have their own
	 * individual world model.
	 * 
	 * @param world - the game world
	 */
	public PathFinder(VillagersWorldPerception world){
		pathFinder = new AStarPathFinder(world, 10000, false);
	}
	
	/**
	 * Simply returns a path from start-point to goal-point.
	 * 
	 * @param startX
	 * @param startY
	 * @param goalX
	 * @param goalY
	 * @return - the path from start-point to goal-point
	 */
	public static Path getPath(int startX, int startY, int goalX, int goalY){
		try{
			path = pathFinder.findPath(null, startX, startY, goalX, goalY);
		}catch(NullPointerException e){
			System.out.println("");
			System.out.println("PathFinder Error: Path is null. Make sure point is reachable");
			System.out.println("");
			
			e.printStackTrace();
			System.exit(0);
		}
		return path;
	}
	
	/**
	 * This method is mainly used in FindObject in order to identify points that are
	 * Unreachable.
	 * 
	 * @param startX
	 * @param startY
	 * @param goalX
	 * @param goalY
	 * @return - null if no adjacent points can be reached
	 */
	public static Path getPathToAdjacent(int startX, int startY, int goalX, int goalY){
		path = pathFinder.findPath(null, startX, startY, goalX, goalY);
		if(path != null)
			return path;
		
		if(goalX < 79)
			path = pathFinder.findPath(null, startX, startY, goalX + 1, goalY);
		if(path != null)
			return path;
		
		if(goalX > 0)
			path = pathFinder.findPath(null, startX, startY, goalX - 1, goalY);
		if(path != null)
			return path;
		
		if(goalY < 79)
			path = pathFinder.findPath(null, startX, startY, goalX, goalY + 1);
		if(path != null)
			return path;
		
		if(goalY > 0)
			path = pathFinder.findPath(null, startX, startY, goalX, goalY - 1);
		if(path != null)
			return path;
		
		return null;
	}
	
}
