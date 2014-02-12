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
	
}
