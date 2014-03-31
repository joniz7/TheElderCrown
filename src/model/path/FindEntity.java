package model.path;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import model.entity.Entity;
import model.path.criteria.Criteria;
import model.villager.AgentWorld;
import model.villager.intentions.action.ImpactableByAction;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;
import view.entity.top.Helper3View;

/**
 * FindEntity is part of the entity path finding. FindEntity uses
 * a world representation and start points in order to find the closest objects
 * possible to that location.
 * 
 * @author Simon Eliasson
 *
 */
public class FindEntity {
		
	public FindEntity(){
		
	}

	/**
	 * FindTile finds the nearest tile in the bottom layer of the specified type that also
	 * has a possible path to it.
	 * 
	 * Note that this method does NOT find a path, it simply finds the object. The
	 * path is then calculated later using the point given by this method.
	 * 
	 * @param world - The world representation
	 * @param type - the type of the object looked for
	 * @param startX - the villager X coordinate
	 * @param startY - the villager y coordinate
	 * @return The point representing the coordinates of the found tile
	 */
	public static Point findBotEntity(ImpactableByAction world, EntityType type, int startX, int startY){
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
		HashMap<Point, Entity> tiles = world.getBotEntities();
		
		if(tiles.get(new Point(startX, startY)).getType() == type)
			return new Point(startX, startY);
		
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
		
		int stacks = 0;
		boolean found = false;
		while(!found){
			stacks++;
//			System.out.println("findtile2 loop");
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
				if(tiles.get(p) != null && tiles.get(p).getType() == type && PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else{
					visited.add(p);
//					View.addTopGraphic(new Helper1View(p.x, p.y));
				}
			
			for(Point p : toCheck)
				if(tiles.get(p) != null && tiles.get(p).getType() == type && PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else{
					visited.add(p);
//					View.addTopGraphic(new Helper1View(p.x, p.y));
				}
			
			if(stacks > 100 || visited.size() == 0){
//				System.out.println("FindObject: " + visited.size());
				//Exception e = new Exception();
				//e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public static boolean standingOnTile(ImpactableByAction world, EntityType type, int startX, int startY){
		HashMap<Point, Entity> tiles = world.getBotEntities();
		
		if(tiles.get(new Point(startX, startY)) != null && tiles.get(new Point(startX, startY)).getType() == type)
			return true;
		
		return false;
	}
	
	public static boolean standingOnObject(AgentWorld world, EntityType type, int startX, int startY){
		HashMap<Point, Entity> objects = world.getMidEntities();

		if(objects.get(new Point(startX, startY)) != null && objects.get(new Point(startX, startY)).getType() == type)
			return true;

		return false;
	}

	/**
	 * In order to create a correct path, the villager wants to move to a unblocked tile adjacent
	 * to the object. This method checks all 4 neighbors of
	 * the object and returns the point with the shortest path.
	 * 
	 * @param world - the game world
	 * @param type - the type of the object
	 * @param startX - the villager x position
	 * @param startY - the villager y position
	 * @return - The Point to walk to in order to interact with object
	 */
	public static Point findBotEntityNeighbour(ImpactableByAction world, EntityType type, int startX, int startY){
		long startTime = System.currentTimeMillis();
		
		if(isStuck(world, startX, startY)){
//			System.out.println("STUCK");
			return null;
		}
		
		Point startPos = new Point(startX, startY);
		Point p = findBotEntity(world, type, startX, startY);
		
		return findTileNeighbour(world, p, startPos);
	}
	
	public static boolean isAdjacentTile(ImpactableByAction world, EntityType type, int startX, int startY){
		HashMap<Point, Entity> tiles = world.getBotEntities();
		
		try{
			if(tiles.get(new Point((int) startX - 1, (int) startY)).getType() == type)
				return true;
			if(tiles.get(new Point((int) startX + 1, (int) startY)).getType() == type)
				return true;
			if(tiles.get(new Point((int) startX, (int) startY - 1)).getType() == type)
				return true;
			if(tiles.get(new Point((int) startX, (int) startY + 1)).getType() == type)
				return true;
		}catch(NullPointerException e){
			
		}
		
		return false;
	}
	
	/**
	 * Finds the closest neighbouring tile to p.
	 * 
	 * @param world - the world representation to search
	 * @param endPos - the point we want to stand next to
	 * @param startPos - where we are right now (needed for choosing shortest path)
	 * 
	 * @return the neighbouring Point closest to p
	 */
	public static Point findTileNeighbour(ImpactableByAction world, Point endPos, Point startPos) {
		if(endPos==null){
			return null;
		}
		
		int startX = startPos.x;
		int startY = startPos.y;
		
		HashMap<Point, Entity> tiles = world.getBotEntities();
		
		Path p1 = null;
		Path p2 = null;
		Path p3 = null;
		Path p4 = null;
		
		if(tiles.get(new Point((int) endPos.getX() - 1, (int) endPos.getY())).getType() == EntityType.GRASS_TILE)
			p1 = PathFinder.getPath(startX, startY, (int) endPos.getX() - 1, (int) endPos.getY());
		if(tiles.get(new Point((int) endPos.getX() + 1, (int) endPos.getY())).getType() == EntityType.GRASS_TILE)
			p2 = PathFinder.getPath(startX, startY, (int) endPos.getX() + 1, (int) endPos.getY());
		if(tiles.get(new Point((int) endPos.getX(), (int) endPos.getY() - 1)).getType() == EntityType.GRASS_TILE)
			p3 = PathFinder.getPath(startX, startY, (int) endPos.getX(), (int) endPos.getY() - 1);
		if(tiles.get(new Point((int) endPos.getX(), (int) endPos.getY() + 1)).getType() == EntityType.GRASS_TILE)
			p4 = PathFinder.getPath(startX, startY, (int) endPos.getX(), (int) endPos.getY() + 1);
		
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
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * FindObject finds the nearest mid/top layer object of the specified type that also
	 * has a possible path to it. A criteria can also be applied to the search.
	 * 
	 * Note that this method does NOT find a path, it simply finds the object. The
	 * path is then calculated later using the point given by this method.
	 * 
	 * @author Simon Eliasson
	 * 
	 * @param world - The world representation
	 * @param crit - The criteria to be fulfilled by the object
	 * @param type - the type of the object looked for
	 * @param startX - the villager X coordinate
	 * @param startY - the villager y coordinate
	 * @return The point representing the coordinates of the found tile
	 */
	public static Point findTopMidEntity(ImpactableByAction world, Criteria crit, EntityType type, int startX, int startY){
		/*
		 * These lists and hash-maps are essentially the same as the ones in the
		 * findTile2 method. The algorithm works in the same way, except that it
		 * also takes the criteria into account
		 */
		HashMap<Point, Boolean> visitedHash = new HashMap<Point, Boolean>();
		HashMap<Point, Entity> mids = world.getMidEntities();
		HashMap<Point, Entity> tops = world.getTopEntities();
		HashMap<Point, Entity> bots = world.getBotEntities();
		
		ArrayList<Point> visited = new ArrayList<Point>();
		ArrayList<Point> toVisit = new ArrayList<Point>();
		ArrayList<Point> toCheck = new ArrayList<Point>();
		
		visited.add(new Point(startX, startY));
		int stacks = 0;
		boolean found = false;
		while(!found){
			//System.out.println("findobject2 loop");
			stacks++;
			//Add neighbors to visit
			toVisit = new ArrayList<Point>();
			toCheck = new ArrayList<Point>();
			for(Point p : visited){
				if(visitedHash.get(new Point(p.x + 1, p.y)) == null){
					if(!world.blocked(null, p.x + 1, p.y)){
						toVisit.add(new Point(p.x + 1, p.y));
					}else{
						toCheck.add(new Point(p.x + 1, p.y));
					}
					visitedHash.put(new Point(p.x + 1, p.y), true);
					
				}
				if(visitedHash.get(new Point(p.x - 1, p.y)) == null){
					if(!world.blocked(null, p.x - 1, p.y)){
						toVisit.add(new Point(p.x - 1, p.y));
					}else{
						toCheck.add(new Point(p.x - 1, p.y));
					}
					visitedHash.put(new Point(p.x - 1, p.y), true);
				
				}
				if(visitedHash.get(new Point(p.x, p.y + 1)) == null){
					if(!world.blocked(null, p.x, p.y + 1)){
						toVisit.add(new Point(p.x, p.y + 1));
					}else{
						toCheck.add(new Point(p.x, p.y + 1));
					}
					visitedHash.put(new Point(p.x, p.y + 1), true);
					
				}
				if(visitedHash.get(new Point(p.x, p.y - 1)) == null){
					if(!world.blocked(null, p.x, p.y - 1)){
						toVisit.add(new Point(p.x, p.y - 1));
					}else{
						toCheck.add(new Point(p.x, p.y - 1));
					}
					visitedHash.put(new Point(p.x, p.y - 1), true);
				}
			}
						
			visited = new ArrayList<Point>();
			
			for(Point p : toVisit){
				if(mids.get(p) != null && (mids.get(p).getType() == type || type == null) && mids.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else if(tops.get(p) != null && (tops.get(p).getType() == type || type == null) && tops.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else if(bots.get(p) != null && (bots.get(p).getType() == type || type == null) && bots.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else {
					visited.add(p);
//					View.addTopGraphic(new Helper1View(p.x, p.y));
				}
			}
			
			for(Point p : toCheck){
				if(mids.get(p) != null && (mids.get(p).getType() == type || type == null) && mids.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else if(tops.get(p) != null && (tops.get(p).getType() == type || type == null) && tops.get(p).meetCriteria(crit) && 
						PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
//				else
//					View.addTopGraphic(new Helper1View(p.x, p.y));
			}
			
			if(stacks > 100 || visited.size() == 0){
				System.out.println("FindObject: " + visited.size());
				System.out.println("FindObject: " + crit);
				System.out.println("FindObject: " + type);
				Exception e = new Exception();
				e.printStackTrace();
				return null;
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
	 * @param type - the type of the object
	 * @param startX - the villager x position
	 * @param startY - the villager y position
	 * @return - The Point to walk to in order to interact with object
	 */
	public static Point findTopMidEntityNeighbour(ImpactableByAction world, Criteria crit, EntityType type, int startX, int startY){
//		long startTime = System.currentTimeMillis();
		
		if(isStuck(world, startX, startY)){
			System.out.println("STUCK");
			return null;
		}
		
		Point startPos = new Point(startX, startY);
		Point p = findTopMidEntity(world, crit, type, startX, startY);
		
		return findTileNeighbour(world, p, startPos);

		/*
		 * TODO remove this, we use findTileNeighbour instead?
		 * Should boundary checks (p.getX() > 0 and so on)
		 * be included in findTileNeighbour?
		 * 
		if(p == null){
			System.out.println("CANT FIND");
			return null;
		}

//		long endTime = System.currentTimeMillis();
//		System.out.println("FindObject, find Tree tile: " + (endTime - startTime));
		
		HashMap<Point, Entity> tiles = world.getBotEntities();
		
		Path p1 = null;
		Path p2 = null;
		Path p3 = null;
		Path p4 = null;
		

			
		if(p.getX() > 0 && tiles.get(new Point((int) p.getX() - 1, (int) p.getY())).getType() == EntityType.GRASS_TILE)
			p1 = PathFinder.getPath(startX, startY, (int) p.getX() - 1, (int) p.getY());
		if(p.getX() < world.getWidthInTiles() - 1 && tiles.get(new Point((int) p.getX() + 1, (int) p.getY())).getType() == EntityType.GRASS_TILE)
			p2 = PathFinder.getPath(startX, startY, (int) p.getX() + 1, (int) p.getY());
		if(p.getY() > 0 && tiles.get(new Point((int) p.getX(), (int) p.getY() - 1)).getType() == EntityType.GRASS_TILE)
			p3 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() - 1);
		if(p.getY() < world.getHeightInTiles() - 1 && tiles.get(new Point((int) p.getX(), (int) p.getY() + 1)).getType() == EntityType.GRASS_TILE)
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
		*/
	}
	

	public static Entity getAdjacentObject(ImpactableByAction world, Criteria crit, EntityType type, int startX, int startY){
		HashMap<Point, Entity> mids = world.getMidEntities();
		HashMap<Point, Entity> tops = world.getTopEntities();
		
		try{
			if(mids.get(new Point(startX - 1, startY)) != null &&
					(mids.get(new Point(startX - 1, startY)).getType() == type || type == null) &&
					mids.get(new Point(startX - 1, startY)).meetCriteria(crit))
				return mids.get(new Point(startX - 1, startY));
			if(mids.get(new Point(startX + 1, startY)) != null && 
					(mids.get(new Point(startX + 1, startY)).getType() == type || type == null) &&
					mids.get(new Point(startX + 1, startY)).meetCriteria(crit))
				return mids.get(new Point(startX + 1, startY));
			if(mids.get(new Point(startX, startY - 1)) != null &&
					(mids.get(new Point(startX, startY - 1)).getType() == type || type == null) &&
					mids.get(new Point(startX, startY - 1)).meetCriteria(crit))
				return mids.get(new Point(startX, startY - 1));
			if(mids.get(new Point(startX, startY + 1)) != null &&
					(mids.get(new Point(startX, startY + 1)).getType() == type || type == null) &&
					mids.get(new Point(startX, startY + 1)).meetCriteria(crit))
				return mids.get(new Point(startX, startY + 1));
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		try{
			if(tops.get(new Point(startX - 1, startY)) != null &&
					(tops.get(new Point(startX - 1, startY)).getType() == type || type == null) &&
					tops.get(new Point(startX - 1, startY)).meetCriteria(crit))
				return tops.get(new Point(startX - 1, startY));
			if(tops.get(new Point(startX + 1, startY)) != null &&
					(tops.get(new Point(startX + 1, startY)).getType() == type || type == null) &&
					tops.get(new Point(startX + 1, startY)).meetCriteria(crit))
				return tops.get(new Point(startX + 1, startY));
			if(tops.get(new Point(startX, startY - 1)) != null &&
					(tops.get(new Point(startX, startY - 1)).getType() == type || type == null) &&
					tops.get(new Point(startX, startY - 1)).meetCriteria(crit))
				return tops.get(new Point(startX, startY - 1));
			if(tops.get(new Point(startX, startY + 1)) != null &&
					(tops.get(new Point(startX, startY + 1)).getType() == type || type == null) &&
					tops.get(new Point(startX, startY + 1)).meetCriteria(crit))
				return tops.get(new Point(startX, startY + 1));
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		return null;
	}
	
//	public static boolean isStuck(VillagersWorldPerception world, int startX, int startY){
//		HashMap<Point, Entity> midEnts = world.getMidEntities();
//		Point p = new Point(startX+1,startY);
//		if(world.blocked(null, startX + 1, startY) || midEnts.containsKey(p))
//			p.move(startX-1, startY);
//			if(world.blocked(null, startX - 1, startY) || midEnts.containsKey(p))
//				p.move(startX, startY+1);
//				if(world.blocked(null, startX, startY + 1) || midEnts.containsKey(p))
//					p.move(startX, startY-1);
//					if(world.blocked(null, startX, startY - 1) || midEnts.containsKey(p))
//						return true;
//		return false;
//	}
	
	public static boolean isStuck(ImpactableByAction world, int startX, int startY){
		if(world.blocked(null, startX + 1, startY))
			if(world.blocked(null, startX - 1, startY))
				if(world.blocked(null, startX, startY + 1))
					if(world.blocked(null, startX, startY - 1))
						return true;
		return false;
	}
}
