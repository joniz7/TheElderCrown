package model.path;

import head.GameSlick;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

import model.TestWorld;
import model.entity.MidEntity;
import model.entity.bottom.BottomEntity;
import model.entity.top.TopEntity;
import model.path.criteria.Criteria;

import org.newdawn.slick.util.pathfinding.Path;

import util.Helper1;
import util.Helper2;
import util.EntityType;
import view.View;

public class FindObject {
	
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
	
	public static Point findTile2(TestWorld world, EntityType id, int startX, int startY){
		HashMap<Point, Boolean> visitedHash = new HashMap<Point, Boolean>();
		HashMap<Point, BottomEntity> tiles = world.getTiles();
		
		ArrayList<Point> visited = new ArrayList<Point>();
		ArrayList<Point> toVisit = new ArrayList<Point>();
		
		visited.add(new Point(startX, startY));
		
		boolean found = false;
		while(!found){
			//Add neighbors to visit
			toVisit = new ArrayList<Point>();
			for(Point p : visited){
				if(visitedHash.get(new Point(p.x + 1, p.y)) == null){
					toVisit.add(new Point(p.x + 1, p.y));
					visitedHash.put(new Point(p.x + 1, p.y), true);
				}
				if(visitedHash.get(new Point(p.x - 1, p.y)) == null){
					toVisit.add(new Point(p.x - 1, p.y));
					visitedHash.put(new Point(p.x - 1, p.y), true);
				}
				if(visitedHash.get(new Point(p.x, p.y + 1)) == null){
					toVisit.add(new Point(p.x, p.y + 1));
					visitedHash.put(new Point(p.x, p.y + 1), true);
				}
				if(visitedHash.get(new Point(p.x, p.y - 1)) == null){
					toVisit.add(new Point(p.x, p.y - 1));
					visitedHash.put(new Point(p.x, p.y - 1), true);
				}
			}
			
			visited = new ArrayList<Point>();
			
			for(Point p : toVisit)
				if(tiles.get(p) != null && tiles.get(p).getEntityType() == id && PathFinder.getPathToAdjacent(p.x, p.y, startX, startY) != null)
					return p;
				else
					visited.add(p);
				
		}
		return null;
	}
	
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
		
		if(tiles.get(new Point((int) p.getX() - 1, (int) p.getY())).getTileID() == 0)
			p1 = PathFinder.getPath(startX, startY, (int) p.getX() - 1, (int) p.getY());
		if(tiles.get(new Point((int) p.getX() + 1, (int) p.getY())).getTileID() == 0)
			p2 = PathFinder.getPath(startX, startY, (int) p.getX() + 1, (int) p.getY());
		if(tiles.get(new Point((int) p.getX(), (int) p.getY() - 1)).getTileID() == 0)
			p3 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() - 1);
		if(tiles.get(new Point((int) p.getX(), (int) p.getY() + 1)).getTileID() == 0)
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
			new Helper1(p.x, p.y);
			new Helper2(startX, startY);
			world.setPaused(true);
		}
		
		return null;
	}
	
	public static boolean isAdjacentTile(TestWorld world, EntityType id, int startX, int startY){
		HashMap<Point, BottomEntity> tiles = world.getTiles();
		
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
	
	public static Point findObject2(TestWorld world, Criteria crit, EntityType id, int startX, int startY){
		HashMap<Point, Boolean> visitedHash = new HashMap<Point, Boolean>();
		HashMap<Point, MidEntity> mids = world.getMidObjects();
		HashMap<Point, TopEntity> tops = world.getTopObjects();
		
		ArrayList<Point> visited = new ArrayList<Point>();
		ArrayList<Point> toVisit = new ArrayList<Point>();
		
		visited.add(new Point(startX, startY));
		
		boolean found = false;
		while(!found){
			//Add neighbors to visit
			toVisit = new ArrayList<Point>();
			for(Point p : visited){
				if(visitedHash.get(new Point(p.x + 1, p.y)) == null){
					toVisit.add(new Point(p.x + 1, p.y));
					visitedHash.put(new Point(p.x + 1, p.y), true);
				}
				if(visitedHash.get(new Point(p.x - 1, p.y)) == null){
					toVisit.add(new Point(p.x - 1, p.y));
					visitedHash.put(new Point(p.x - 1, p.y), true);
				}
				if(visitedHash.get(new Point(p.x, p.y + 1)) == null){
					toVisit.add(new Point(p.x, p.y + 1));
					visitedHash.put(new Point(p.x, p.y + 1), true);
				}
				if(visitedHash.get(new Point(p.x, p.y - 1)) == null){
					toVisit.add(new Point(p.x, p.y - 1));
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
				else
					visited.add(p);
			}
		}
		return null;
	}
	
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
		
		if(p.getX() > 0 && tiles.get(new Point((int) p.getX() - 1, (int) p.getY())).getTileID() == 0)
			p1 = PathFinder.getPath(startX, startY, (int) p.getX() - 1, (int) p.getY());
		if(p.getX() < world.getWidthInTiles() - 1 && tiles.get(new Point((int) p.getX() + 1, (int) p.getY())).getTileID() == 0)
			p2 = PathFinder.getPath(startX, startY, (int) p.getX() + 1, (int) p.getY());
		if(p.getY() > 0 && tiles.get(new Point((int) p.getX(), (int) p.getY() - 1)).getTileID() == 0)
			p3 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() - 1);
		if(p.getY() < world.getHeightInTiles() - 1 && tiles.get(new Point((int) p.getX(), (int) p.getY() + 1)).getTileID() == 0)
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
		
		int finalX = bestPath.getStep(bestPath.getLength() - 1).getX();
		int finalY = bestPath.getStep(bestPath.getLength() - 1).getY();
		
		return new Point(finalX, finalY);
	}
	
	public static boolean isAdjacentObject(TestWorld world, EntityType id, int startX, int startY){
		HashMap<Point, MidEntity> mids = world.getMidObjects();
		HashMap<Point, TopEntity> tops = world.getTopObjects();
		
		try{
			if(mids.get(new Point(startX - 1, startY)) != null &&
					mids.get(new Point(startX - 1, startY)).getEntityType() == id)
				return true;
			if(mids.get(new Point(startX + 1, startY)) != null && 
					mids.get(new Point(startX + 1, startY)).getEntityType() == id)
				return true;
			if(mids.get(new Point(startX, startY - 1)) != null &&
					mids.get(new Point(startX, startY - 1)).getEntityType() == id)
				return true;
			if(mids.get(new Point(startX, startY + 1)) != null &&
					mids.get(new Point(startX, startY + 1)).getEntityType() == id)
				return true;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		try{
			if(tops.get(new Point(startX - 1, startY)) != null &&
					tops.get(new Point(startX - 1, startY)).getEntityType() == id)
				return true;
			if(tops.get(new Point(startX + 1, startY)) != null &&
					tops.get(new Point(startX + 1, startY)).getEntityType() == id)
				return true;
			if(tops.get(new Point(startX, startY - 1)) != null &&
					tops.get(new Point(startX, startY - 1)).getEntityType() == id)
				return true;
			if(tops.get(new Point(startX, startY + 1)) != null &&
					tops.get(new Point(startX, startY + 1)).getEntityType() == id)
				return true;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		return false;
	}
	
	
}
