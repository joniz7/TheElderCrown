package model.path;

import model.TestWorld;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

public class PathFinder {

	private static Path path;
	private static AStarPathFinder pathFinder;
	
	public PathFinder(TestWorld world){
		pathFinder = new AStarPathFinder(world, 10000, false);
	}
	
	public static Path getPath(int startX, int startY, int goalX, int goalY){
		path = pathFinder.findPath(null, startX, startY, goalX, goalY);
		return path;
	}
	
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
