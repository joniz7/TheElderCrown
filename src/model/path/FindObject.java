package model.path;

import java.awt.Point;
import java.util.HashMap;

import model.TestWorld;
import model.entity.BottomLayerGraphicalEntity;
import model.entity.MiddleLayerGraphicalEntity;
import model.entity.TopLayerGraphicalEntity;
import model.path.criteria.Criteria;

import org.newdawn.slick.util.pathfinding.Path;

import resource.ObjectID;

public class FindObject {
	
	public static Point findTile(TestWorld world, ObjectID id, int startX, int startY){
		HashMap<Point, BottomLayerGraphicalEntity> tiles = world.getTiles();
		
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
				if(tiles.get(new Point(i, (int) upperLeft.getY())).getObjectID() == id)
					if(PathFinder.getPathToAdjacent(startX, startY, i, (int) upperLeft.getY()) != null)
						return new Point(i, (int) upperLeft.getY());
			
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++)
				if(tiles.get(new Point(i, (int) lowerRight.getY())).getObjectID() == id)
					if(PathFinder.getPathToAdjacent(startX, startY, i, (int) lowerRight.getY()) != null)
						return new Point(i, (int) lowerRight.getY());
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++)
				if(tiles.get(new Point((int) upperLeft.getX(), i)).getObjectID() == id)
					if(PathFinder.getPathToAdjacent(startX, startY, (int) upperLeft.getX(), i) != null)
						return new Point((int) upperLeft.getX(), i);
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++)
				if(tiles.get(new Point((int) lowerRight.getX(), i)).getObjectID() == id)
					if(PathFinder.getPathToAdjacent(startX, startY, (int) lowerRight.getX(), i) != null)
						return new Point((int) lowerRight.getX(), i);
			
			System.out.println("FindObject: Searching for " + id + " - " + upperLeft.toString());
			
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
	
	public static Point findTileNeighbour(TestWorld world, ObjectID id, int startX, int startY){
		Point p = findTile(world, id, startX, startY);
		
		HashMap<Point, BottomLayerGraphicalEntity> tiles = world.getTiles();
		
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
		
		
		
		int finalX = bestPath.getStep(bestPath.getLength() - 1).getX();
		int finalY = bestPath.getStep(bestPath.getLength() - 1).getY();
		
		return new Point(finalX, finalY);
	}
	
	public static boolean isAdjacentTile(TestWorld world, ObjectID id, int startX, int startY){
		HashMap<Point, BottomLayerGraphicalEntity> tiles = world.getTiles();
		
		try{
			if(tiles.get(new Point((int) startX - 1, (int) startY)).getObjectID() == id)
				return true;
			if(tiles.get(new Point((int) startX + 1, (int) startY)).getObjectID() == id)
				return true;
			if(tiles.get(new Point((int) startX, (int) startY - 1)).getObjectID() == id)
				return true;
			if(tiles.get(new Point((int) startX, (int) startY + 1)).getObjectID() == id)
				return true;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		return false;
	}
	
	public static Point findObject(TestWorld world, Criteria crit, ObjectID id, int startX, int startY){
		HashMap<Point, MiddleLayerGraphicalEntity> mids = world.getMidObjects();
		HashMap<Point, TopLayerGraphicalEntity> tops = world.getTopObjects();
		
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
					if(mids.get(new Point(i, (int) upperLeft.getY())).getObjectID() == id && 
							mids.get(new Point(i, (int) upperLeft.getY())).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(startX, startY, i, (int) upperLeft.getY()) != null)
							return new Point(i, (int) upperLeft.getY());
				if(tops.get(new Point(i, (int) upperLeft.getY())) != null)
					if(tops.get(new Point(i, (int) upperLeft.getY())).getObjectID() == id &&
							tops.get(new Point(i, (int) upperLeft.getY())).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(startX, startY, i, (int) upperLeft.getY()) != null)
							return new Point(i, (int) upperLeft.getY());
			}
			
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++){
				if(mids.get(new Point(i, (int) lowerRight.getY())) != null)
					if(mids.get(new Point(i, (int) lowerRight.getY())).getObjectID() == id &&
							mids.get(new Point(i, (int) lowerRight.getY())).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(startX, startY, i, (int) lowerRight.getY()) != null)
							return new Point(i, (int) lowerRight.getY());
				if(tops.get(new Point(i, (int) lowerRight.getY())) != null)
					if(tops.get(new Point(i, (int) lowerRight.getY())).getObjectID() == id &&
							tops.get(new Point(i, (int) lowerRight.getY())).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(startX, startY, i, (int) lowerRight.getY()) != null)
							return new Point(i, (int) lowerRight.getY());
			}
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++){
				if(mids.get(new Point((int) upperLeft.getX(), i)) != null)
					if(mids.get(new Point((int) upperLeft.getX(), i)).getObjectID() == id &&
							mids.get(new Point((int) upperLeft.getX(), i)).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(startX, startY, (int) upperLeft.getX(), i) != null)
							return new Point((int) upperLeft.getX(), i);
				if(tops.get(new Point((int) upperLeft.getX(), i)) != null)
					if(tops.get(new Point((int) upperLeft.getX(), i)).getObjectID() == id &&
							tops.get(new Point((int) upperLeft.getX(), i)).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(startX, startY, (int) upperLeft.getX(), i) != null)
							return new Point((int) upperLeft.getX(), i);
			}
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++){
				if(mids.get(new Point((int) lowerRight.getX(), i)) != null)
					if(mids.get(new Point((int) lowerRight.getX(), i)).getObjectID() == id &&
							mids.get(new Point((int) lowerRight.getX(), i)).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(startX, startY, (int) lowerRight.getX(), i) != null)
							return new Point((int) lowerRight.getX(), i);
				if(tops.get(new Point((int) lowerRight.getX(), i)) != null)
					if(tops.get(new Point((int) lowerRight.getX(), i)).getObjectID() == id &&
							tops.get(new Point((int) lowerRight.getX(), i)).meetCriteria(crit))
						if(PathFinder.getPathToAdjacent(startX, startY, (int) lowerRight.getX(), i) != null)
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
	
	public static Point findObjectNeighbour(TestWorld world, Criteria crit, ObjectID id, int startX, int startY){
		Point p = findObject(world, crit, id, startX, startY);
		
		HashMap<Point, BottomLayerGraphicalEntity> tiles = world.getTiles();
		
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
	
	public static boolean isAdjacentObject(TestWorld world, ObjectID id, int startX, int startY){
		HashMap<Point, MiddleLayerGraphicalEntity> mids = world.getMidObjects();
		HashMap<Point, TopLayerGraphicalEntity> tops = world.getTopObjects();
		
		try{
			if(mids.get(new Point(startX - 1, startY)) != null &&
					mids.get(new Point(startX - 1, startY)).getObjectID() == id)
				return true;
			if(mids.get(new Point(startX + 1, startY)) != null && 
					mids.get(new Point(startX + 1, startY)).getObjectID() == id)
				return true;
			if(mids.get(new Point(startX, startY - 1)) != null &&
					mids.get(new Point(startX, startY - 1)).getObjectID() == id)
				return true;
			if(mids.get(new Point(startX, startY + 1)) != null &&
					mids.get(new Point(startX, startY + 1)).getObjectID() == id)
				return true;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		try{
			if(tops.get(new Point(startX - 1, startY)) != null &&
					tops.get(new Point(startX - 1, startY)).getObjectID() == id)
				return true;
			if(tops.get(new Point(startX + 1, startY)) != null &&
					tops.get(new Point(startX + 1, startY)).getObjectID() == id)
				return true;
			if(tops.get(new Point(startX, startY - 1)) != null &&
					tops.get(new Point(startX, startY - 1)).getObjectID() == id)
				return true;
			if(tops.get(new Point(startX, startY + 1)) != null &&
					tops.get(new Point(startX, startY + 1)).getObjectID() == id)
				return true;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		return false;
	}
	
	
}
