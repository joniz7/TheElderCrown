package model.path;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import model.TestWorld;
import model.World;
import model.entity.Entity;
import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.top.TopEntity;
import model.path.criteria.Criteria;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;
import view.entity.top.Helper3View;
import debugging.Helper1;
import debugging.Helper2;

/**
 * FindObject is part of the entity path finding. FindObject uses
 * a world representation and start points in order to find the closest objects
 * possible to that location.
 * 
 * @author Simon Eliasson
 *
 */
public class FindObject {
	
	/*
	 * Funderar p� om man ska sm�lla ihop alla tre lager
	 * i en enda algoritm. Har undvikit detta hittills eftersom
	 * att just bottenlagret �r ganska speciellt, d� det enbart best�r av tiles.
	 */
	
	/**
	 * This did the job that findTile2 does now, saves this code, just in case
	 */
	public static Point findTile(TestWorld world, EntityType id, int startX, int startY){
		HashMap<Point, BottomEntity> tiles = world.getTiles();
		
		Point upperLeft = new Point(startX - 1, startY - 1);
		Point lowerRight  = new Point(startX + 1, startY + 1);
		
		if(upperLeft.x < 0)
			upperLeft.x = 0;
		if(lowerRight.x > world.getWidthInTiles() - 1)
			lowerRight.x = world.getWidthInTiles() - 1;
		if(upperLeft.y < 0)
			upperLeft.y = 0;
		if(lowerRight.y > world.getHeightInTiles() - 1)
			lowerRight.y = world.getHeightInTiles() - 1;
		
		boolean found = false;
		while(!found){
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++)
				if(tiles.get(new Point(i, (int) upperLeft.getY())).getEntityType() == id)
					if(PathFinder.getPathToAdjacent(i, (int) upperLeft.getY(), startX, startY) != null)
						return new Point(i, (int) upperLeft.getY());
			
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++)
				if(tiles.get(new Point(i, (int) lowerRight.getY())).getEntityType() == id)
					if(PathFinder.getPathToAdjacent(i, (int) lowerRight.getY(), startX, startY) != null)
						return new Point(i, (int) lowerRight.getY());
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++)
				if(tiles.get(new Point((int) upperLeft.getX(), i)).getEntityType() == id)
					if(PathFinder.getPathToAdjacent((int) upperLeft.getX(), i, startX, startY) != null)
						return new Point((int) upperLeft.getX(), i);
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++)
				if(tiles.get(new Point((int) lowerRight.getX(), i)).getEntityType() == id)
					if(PathFinder.getPathToAdjacent((int) lowerRight.getX(), i, startX, startY) != null)
						return new Point((int) lowerRight.getX(), i);
						
			if(upperLeft.getX() > 0)
				upperLeft.translate(-1, 0);
			if(upperLeft.getY() > 0)
				upperLeft.translate(0, -1);
			if(lowerRight.getX() < world.getWidthInTiles() - 1)
				lowerRight.translate(1, 0);
			if(lowerRight.getY() < world.getHeightInTiles() - 1)
				lowerRight.translate(0, 1);
		}

		return null;
	}
	
	/**
	 * FindTile 2 finds the nearest tile in the bottom layer of the specified type that also
	 * has a possible path to it.
	 * 
	 * Note that this method does NOT find a path, it simply finds the object. The
	 * path is then calculated later using the point given by this method.
	 * 
	 * @param world - The world representation
	 * @param id - the type of the object looked for
	 * @param startX - the villager X coordinate
	 * @param startY - the villager y coordinate
	 * @return The point representing the coordinates of the found tile
	 */
public static Point findTile2(TestWorld world, EntityType id, int startX, int startY){
		/*
		 * Understanding what these lists and hash-maps are used for is crucial in
		 * order to understand the find-object-algorithm
		 */
		
		/* visitedHash - Stores a boolean for each point in the world. it is true
		 * if that point has been checked already, this map is used to make sure that
		 * a point is never checked more than once.
		 */
		HashMap<Point, Boolean> visitedHash = new HashMap<Point, Boolean>();
		/*
		 * tiles - this is just the map of all the points in the world, this is what we
		 * search through
		 */
		HashMap<Point, BottomEntity> tiles = world.getTiles();
		
		/*
		 * visited - These are the points that we already visited. We loop over this
		 * list in order to check all neighbors of already visited points.
		 */
		ArrayList<Point> visited = new ArrayList<Point>();
		/*
		 * toVisit - These are the points that are being checked for the object type
		 * we search for. They are neighbors of previously visited points. and are
		 * added to the list of visited points when checked. note that visited points
		 * must be walkable
		 */
		ArrayList<Point> toVisit = new ArrayList<Point>();
		/*
		 * toCheck - These points are the same as toVisit, except that they are not
		 * added to visited tiles when checked. These tiles are not walkable, for
		 * example water tiles end up here.
		 */
		ArrayList<Point> toCheck = new ArrayList<Point>();
		
		visited.add(new Point(startX, startY));
		
		boolean found = false;
		while(!found){
			toVisit = new ArrayList<Point>();
			toCheck = new ArrayList<Point>();
			for(Point p : visited){
				if(visitedHash.get(new Point(p.x + 1, p.y)) == null){
					if(!world.blocked(null, p.x + 1, p.y))
						toVisit.add(new Point(p.x + 1, p.y));
					else
						toCheck.add(new Point(p.x + 1, p.y));
					visitedHash.put(new Point(p.x + 1, p.y), true);
				}
				if(visitedHash.get(new Point(p.x - 1, p.y)) == null){
					if(!world.blocked(null, p.x - 1, p.y))
						toVisit.add(new Point(p.x - 1, p.y));
					else
						toCheck.add(new Point(p.x - 1, p.y));
					visitedHash.put(new Point(p.x - 1, p.y), true);
				}
				if(visitedHash.get(new Point(p.x, p.y + 1)) == null){
					if(!world.blocked(null, p.x, p.y + 1))
						toVisit.add(new Point(p.x, p.y + 1));
					else
						toCheck.add(new Point(p.x, p.y + 1));
					visitedHash.put(new Point(p.x, p.y + 1), true);
				}
				if(visitedHash.get(new Point(p.x, p.y - 1)) == null){
					if(!world.blocked(null, p.x, p.y - 1))
						toVisit.add(new Point(p.x, p.y - 1));
					else
						toCheck.add(new Point(p.x, p.y - 1));
					visitedHash.put(new Point(p.x, p.y - 1), true);
				}
			}
			
			visited = new ArrayList<Point>();
			
			for(Point p : toVisit)
				if(tiles.get(p) != null && tiles.get(p).getEntityType() == id && PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else{
					visited.add(p);
//					View.addTopGraphic(new Helper1View(p.x, p.y));
				}
			
			for(Point p : toCheck)
				if(tiles.get(p) != null && tiles.get(p).getEntityType() == id && PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else{
					visited.add(p);
//					View.addTopGraphic(new Helper1View(p.x, p.y));
				}
		}
		return null;
	}
	
	/**
	 * In order to create a correct path, the villager wants to move to a unblocked tile adjacent
	 * to the object. This method checks all 4 neighbors of
	 * the object and returns the point with the shortest path.
	 * 
	 * @param world - the game world
	 * @param id - the id of the object
	 * @param startX - the villager x position
	 * @param startY - the villager y position
	 * @return - The Point to walk to in order to interact with object
	 */
	public static Point findTileNeighbour(TestWorld world, EntityType id, int startX, int startY){

		long startTime = System.currentTimeMillis();
		
		Point p = findTile2(world, id, startX, startY);
		
		long endTime = System.currentTimeMillis();
		System.out.println("FindObject, find water tile: " + (endTime - startTime));
		
		HashMap<Point, BottomEntity> tiles = world.getTiles();
		
		Path p1 = null;
		Path p2 = null;
		Path p3 = null;
		Path p4 = null;
		
		if(tiles.get(new Point((int) p.getX() - 1, (int) p.getY())).getEntityType() == EntityType.GRASS_TILE)
			p1 = PathFinder.getPath(startX, startY, (int) p.getX() - 1, (int) p.getY());
		if(tiles.get(new Point((int) p.getX() + 1, (int) p.getY())).getEntityType() == EntityType.GRASS_TILE)
			p2 = PathFinder.getPath(startX, startY, (int) p.getX() + 1, (int) p.getY());
		if(tiles.get(new Point((int) p.getX(), (int) p.getY() - 1)).getEntityType() == EntityType.GRASS_TILE)
			p3 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() - 1);
		if(tiles.get(new Point((int) p.getX(), (int) p.getY() + 1)).getEntityType() == EntityType.GRASS_TILE)
			p4 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() + 1);
		
		Path bestPath = null;
		
		if(p1 != null)
			bestPath = p1;
		else if(p2 != null)
			bestPath = p2;
		else if(p3 != null)
			bestPath = p3;
		else if(p4 != null)
			bestPath = p4;
		
		if(p2 != null && bestPath.getLength() > p2.getLength())
			bestPath = p2;
		if(p3 != null && bestPath.getLength() > p3.getLength())
			bestPath = p3;
		if(p4 != null && bestPath.getLength() > p4.getLength())
			bestPath = p4;
		
		try{
			int finalX = bestPath.getStep(bestPath.getLength() - 1).getX();
			int finalY = bestPath.getStep(bestPath.getLength() - 1).getY();
			return new Point(finalX, finalY);
		}catch(NullPointerException e){
			new Helper3View(startX, startY);
			world.setPaused(true);
			
			e.printStackTrace();
		}
		
		return null;
	}
	
<<<<<<< HEAD
	/**
	 * This method returns true if an object of the specified is adjacent to the point
	 * specified
	 * 
	 * @param world - the game world
	 * @param id - the id of the object
	 * @param startX - the x position
	 * @param startY - the y position
	 * @return - true if object of type id is adjacent
	 */
	public static boolean isAdjacentTile(TestWorld world, EntityType id, int startX, int startY){

		HashMap<Point, BottomEntity> tiles = world.getTiles();
=======
	public static boolean isAdjacentTile(World world, EntityType id, int startX, int startY){
		HashMap<Point, BottomEntity> tiles = world.getBotEntities();
>>>>>>> origin/new-villager
		
		try{
			if(tiles.get(new Point((int) startX - 1, (int) startY)).getEntityType() == id)
				return true;
			if(tiles.get(new Point((int) startX + 1, (int) startY)).getEntityType() == id)
				return true;
			if(tiles.get(new Point((int) startX, (int) startY - 1)).getEntityType() == id)
				return true;
			if(tiles.get(new Point((int) startX, (int) startY + 1)).getEntityType() == id)
				return true;
		}catch(NullPointerException e){
			
		}
		
		return false;
	}
	
	
	/**
	 * This did the job that findObject2 does now, saves this code, just in case
	 */
	public static Point findObject(TestWorld world, Criteria crit, EntityType id, int startX, int startY){
		HashMap<Point, MidEntity> mids = world.getMidObjects();
		HashMap<Point, TopEntity> tops = world.getTopObjects();

		Point upperLeft = new Point(startX - 1, startY - 1);
		Point lowerRight = new Point(startX + 1, startY + 1);
				
		if(upperLeft.x < 0)
			upperLeft.x = 0;
		if(lowerRight.x > world.getWidthInTiles() - 1)
			lowerRight.x = world.getWidthInTiles() - 1;
		if(upperLeft.y < 0)
			upperLeft.y = 0;
		if(lowerRight.y > world.getHeightInTiles() - 1)
			lowerRight.y = world.getHeightInTiles() - 1;
		
		boolean found = false;
		while(!found){
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++){
				if(mids.get(new Point(i, (int) upperLeft.getY())) != null)
					if(mids.get(new Point(i, (int) upperLeft.getY())).getEntityType() == id && 
							mids.get(new Point(i, (int) upperLeft.getY())).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(i, (int) upperLeft.getY(), startX, startY) != null)
							return new Point(i, (int) upperLeft.getY());
				if(tops.get(new Point(i, (int) upperLeft.getY())) != null)
					if(tops.get(new Point(i, (int) upperLeft.getY())).getEntityType() == id &&
							tops.get(new Point(i, (int) upperLeft.getY())).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(i, (int) upperLeft.getY(), startX, startY) != null)
							return new Point(i, (int) upperLeft.getY());
			}
			
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++){
				if(mids.get(new Point(i, (int) lowerRight.getY())) != null)
					if(mids.get(new Point(i, (int) lowerRight.getY())).getEntityType() == id &&
							mids.get(new Point(i, (int) lowerRight.getY())).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(i, (int) lowerRight.getY(), startX, startY) != null)
							return new Point(i, (int) lowerRight.getY());
				if(tops.get(new Point(i, (int) lowerRight.getY())) != null)
					if(tops.get(new Point(i, (int) lowerRight.getY())).getEntityType() == id &&
							tops.get(new Point(i, (int) lowerRight.getY())).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(i, (int) lowerRight.getY(), startX, startY) != null)
							return new Point(i, (int) lowerRight.getY());
			}
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++){
				if(mids.get(new Point((int) upperLeft.getX(), i)) != null)
					if(mids.get(new Point((int) upperLeft.getX(), i)).getEntityType() == id &&
							mids.get(new Point((int) upperLeft.getX(), i)).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent((int) upperLeft.getX(), i, startX, startY) != null)
							return new Point((int) upperLeft.getX(), i);
				if(tops.get(new Point((int) upperLeft.getX(), i)) != null)
					if(tops.get(new Point((int) upperLeft.getX(), i)).getEntityType() == id &&
							tops.get(new Point((int) upperLeft.getX(), i)).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent((int) upperLeft.getX(), i, startX, startY) != null)
							return new Point((int) upperLeft.getX(), i);
			}
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++){
				if(mids.get(new Point((int) lowerRight.getX(), i)) != null)
					if(mids.get(new Point((int) lowerRight.getX(), i)).getEntityType() == id &&
							mids.get(new Point((int) lowerRight.getX(), i)).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent((int) lowerRight.getX(), i, startX, startY) != null)
							return new Point((int) lowerRight.getX(), i);
				if(tops.get(new Point((int) lowerRight.getX(), i)) != null)
					if(tops.get(new Point((int) lowerRight.getX(), i)).getEntityType() == id &&
							tops.get(new Point((int) lowerRight.getX(), i)).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent((int) lowerRight.getX(), i, startX, startY) != null)
							return new Point((int) lowerRight.getX(), i);
			}
			
			
			if(upperLeft.getX() > 0)
				upperLeft.translate(-1, 0);
			if(upperLeft.getY() > 0)
				upperLeft.translate(0, -1);
			if(lowerRight.getX() < world.getWidthInTiles() - 1)
				lowerRight.translate(1, 0);
			if(lowerRight.getY() < world.getHeightInTiles() - 1)
				lowerRight.translate(0, 1);
		}
		return null;
	}
	
	/**
	 * FindObject2 finds the nearest mid/top layer object of the specified type that also
	 * has a possible path to it. A criteria can also be applied to the search.
	 * 
	 * Note that this method does NOT find a path, it simply finds the object. The
	 * path is then calculated later using the point given by this method.
	 * 
	 * @author Simon Eliasson
	 * 
	 * @param world - The world representation
	 * @param crit - The criteria to be fulfilled by the object
	 * @param id - the type of the object looked for
	 * @param startX - the villager X coordinate
	 * @param startY - the villager y coordinate
	 * @return The point representing the coordinates of the found tile
	 */
	public static Point findObject2(TestWorld world, Criteria crit, EntityType id, int startX, int startY){
		/*
		 * These lists and hash-maps are essentially the same as the ones in the
		 * findTile2 method. The algorithm works in the same way, except that it
		 * also takes the criteria into account
		 */
		HashMap<Point, Boolean> visitedHash = new HashMap<Point, Boolean>();
		HashMap<Point, MidEntity> mids = world.getMidObjects();
		HashMap<Point, TopEntity> tops = world.getTopObjects();
		
		ArrayList<Point> visited = new ArrayList<Point>();
		ArrayList<Point> toVisit = new ArrayList<Point>();
		ArrayList<Point> toCheck = new ArrayList<Point>();
		
		visited.add(new Point(startX, startY));
		
		boolean found = false;
		while(!found){
			//Add neighbors to visit
			toVisit = new ArrayList<Point>();
			toCheck = new ArrayList<Point>();
			for(Point p : visited){
				if(visitedHash.get(new Point(p.x + 1, p.y)) == null){
					if(!world.blocked(null, p.x + 1, p.y))
						toVisit.add(new Point(p.x + 1, p.y));
					else
						toCheck.add(new Point(p.x + 1, p.y));
					visitedHash.put(new Point(p.x + 1, p.y), true);
				}
				if(visitedHash.get(new Point(p.x - 1, p.y)) == null){
					if(!world.blocked(null, p.x - 1, p.y))
						toVisit.add(new Point(p.x - 1, p.y));
					else
						toCheck.add(new Point(p.x - 1, p.y));
					visitedHash.put(new Point(p.x - 1, p.y), true);
				}
				if(visitedHash.get(new Point(p.x, p.y + 1)) == null){
					if(!world.blocked(null, p.x, p.y + 1))
						toVisit.add(new Point(p.x, p.y + 1));
					else
						toCheck.add(new Point(p.x, p.y + 1));
					visitedHash.put(new Point(p.x, p.y + 1), true);
				}
				if(visitedHash.get(new Point(p.x, p.y - 1)) == null){
					if(!world.blocked(null, p.x, p.y - 1))
						toVisit.add(new Point(p.x, p.y - 1));
					else
						toCheck.add(new Point(p.x, p.y - 1));
					visitedHash.put(new Point(p.x, p.y - 1), true);
				}
			}
						
			visited = new ArrayList<Point>();
			
			for(Point p : toVisit){
				if(mids.get(p) != null && mids.get(p).getEntityType() == id && mids.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else if(tops.get(p) != null && tops.get(p).getEntityType() == id && tops.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else{
					visited.add(p);
//					View.addTopGraphic(new Helper1View(p.x, p.y));
				}
			}
			
			for(Point p : toCheck){
				if(mids.get(p) != null && mids.get(p).getEntityType() == id && mids.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else if(tops.get(p) != null && tops.get(p).getEntityType() == id && tops.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
//				else
//					View.addTopGraphic(new Helper1View(p.x, p.y));
			}
		}
		return null;
	}
	
	/**
	 * Same as findTileNeighbor. The difference is that findObject also uses a criteria
	 * in order to find objects.
	 * 
	 * @param world - the game world
	 * @param crit - a criteria to be matched by the object
	 * @param id - the id of the object
	 * @param startX - the villager x position
	 * @param startY - the villager y position
	 * @return - The Point to walk to in order to interact with object
	 */
	public static Point findObjectNeighbour(TestWorld world, Criteria crit, EntityType id, int startX, int startY){

		long startTime = System.currentTimeMillis();
		Point p = findObject2(world, crit, id, startX, startY);
		
		long endTime = System.currentTimeMillis();
		System.out.println("FindObject, find Tree tile: " + (endTime - startTime));
		
		HashMap<Point, BottomEntity> tiles = world.getTiles();
		
		Path p1 = null;
		Path p2 = null;
		Path p3 = null;
		Path p4 = null;
		
		if(p.getX() > 0 && tiles.get(new Point((int) p.getX() - 1, (int) p.getY())).getEntityType() == EntityType.GRASS_TILE)
			p1 = PathFinder.getPath(startX, startY, (int) p.getX() - 1, (int) p.getY());
		if(p.getX() < world.getWidthInTiles() - 1 && tiles.get(new Point((int) p.getX() + 1, (int) p.getY())).getEntityType() == EntityType.GRASS_TILE)
			p2 = PathFinder.getPath(startX, startY, (int) p.getX() + 1, (int) p.getY());
		if(p.getY() > 0 && tiles.get(new Point((int) p.getX(), (int) p.getY() - 1)).getEntityType() == EntityType.GRASS_TILE)
			p3 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() - 1);
		if(p.getY() < world.getHeightInTiles() - 1 && tiles.get(new Point((int) p.getX(), (int) p.getY() + 1)).getEntityType() == EntityType.GRASS_TILE)
			p4 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() + 1);
		
//		if(p.getX() > 0 && tiles.get(new Point((int) p.getX() - 1, (int) p.getY())).getTileID() == 0)
//			p1 = PathFinder.getPath(startX, startY, (int) p.getX() - 1, (int) p.getY());
//		if(p.getX() < world.getWidthInTiles() - 1 && tiles.get(new Point((int) p.getX() + 1, (int) p.getY())).getTileID() == 0)
//			p2 = PathFinder.getPath(startX, startY, (int) p.getX() + 1, (int) p.getY());
//		if(p.getY() > 0 && tiles.get(new Point((int) p.getX(), (int) p.getY() - 1)).getTileID() == 0)
//			p3 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() - 1);
//		if(p.getY() < world.getHeightInTiles() - 1 && tiles.get(new Point((int) p.getX(), (int) p.getY() + 1)).getTileID() == 0)
//			p4 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() + 1);
		
		Path bestPath = null;
		
		if(p1 != null)
			bestPath = p1;
		else if(p2 != null)
			bestPath = p2;
		else if(p3 != null)
			bestPath = p3;
		else if(p4 != null)
			bestPath = p4;
		
		if(p2 != null && bestPath.getLength() > p2.getLength())
			bestPath = p2;
		if(p3 != null && bestPath.getLength() > p3.getLength())
			bestPath = p3;
		if(p4 != null && bestPath.getLength() > p4.getLength())
			bestPath = p4;
		
		int finalX = bestPath.getStep(bestPath.getLength() - 1).getX();
		int finalY = bestPath.getStep(bestPath.getLength() - 1).getY();
		
		return new Point(finalX, finalY);
	}
	
<<<<<<< HEAD
	/**
	 * This method returns true if an object of the specified is adjacent to the point
	 * specified
	 * 
	 * @param world - the game world
	 * @param id - the id of the object
	 * @param startX - the x position
	 * @param startY - the y position
	 * @return - true if object of type id is adjacent
	 */
	public static boolean isAdjacentObject(TestWorld world, EntityType id, int startX, int startY){

		HashMap<Point, MidEntity> mids = world.getMidObjects();
		HashMap<Point, TopEntity> tops = world.getTopObjects();
=======
	public static Entity getAdjacentObject(World world, Criteria crit, EntityType id, int startX, int startY){
		HashMap<Point, MidEntity> mids = world.getMidEntities();
		HashMap<Point, TopEntity> tops = world.getTopEntities();
>>>>>>> origin/new-villager
		
		try{
			if(mids.get(new Point(startX - 1, startY)) != null &&
					mids.get(new Point(startX - 1, startY)).getEntityType() == id &&
					mids.get(new Point(startX - 1, startY)).meetCriteria(crit))
				return mids.get(new Point(startX - 1, startY));
			if(mids.get(new Point(startX + 1, startY)) != null && 
					mids.get(new Point(startX + 1, startY)).getEntityType() == id &&
					mids.get(new Point(startX + 1, startY)).meetCriteria(crit))
				return mids.get(new Point(startX + 1, startY));
			if(mids.get(new Point(startX, startY - 1)) != null &&
					mids.get(new Point(startX, startY - 1)).getEntityType() == id &&
					mids.get(new Point(startX, startY - 1)).meetCriteria(crit))
				return mids.get(new Point(startX, startY - 1));
			if(mids.get(new Point(startX, startY + 1)) != null &&
					mids.get(new Point(startX, startY + 1)).getEntityType() == id &&
					mids.get(new Point(startX, startY + 1)).meetCriteria(crit))
				return mids.get(new Point(startX, startY + 1));
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		try{
			if(tops.get(new Point(startX - 1, startY)) != null &&
					tops.get(new Point(startX - 1, startY)).getEntityType() == id &&
					tops.get(new Point(startX - 1, startY)).meetCriteria(crit))
				return tops.get(new Point(startX - 1, startY));
			if(tops.get(new Point(startX + 1, startY)) != null &&
					tops.get(new Point(startX + 1, startY)).getEntityType() == id &&
					tops.get(new Point(startX + 1, startY)).meetCriteria(crit))
				return tops.get(new Point(startX + 1, startY));
			if(tops.get(new Point(startX, startY - 1)) != null &&
					tops.get(new Point(startX, startY - 1)).getEntityType() == id &&
					tops.get(new Point(startX, startY - 1)).meetCriteria(crit))
				return tops.get(new Point(startX, startY - 1));
			if(tops.get(new Point(startX, startY + 1)) != null &&
					tops.get(new Point(startX, startY + 1)).getEntityType() == id &&
					tops.get(new Point(startX, startY + 1)).meetCriteria(crit))
				return tops.get(new Point(startX, startY + 1));
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		return null;
	}
	
	
}
