package model.path;

import head.Game;

import java.awt.Point;

import org.newdawn.slick.util.pathfinding.Path;

import resource.Hit;
import resource.Miss;
import model.TestWorld;
import model.tile.Tile;

public class FindObject {
	
	public static Point findTile(TestWorld world, int tileID, int startX, int startY){
		Tile[][] tiles = world.getTiles();
		
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
				if(tiles[i][(int) upperLeft.getY()].getTypeID() == tileID)
					return new Point(i, (int) upperLeft.getY());
//				else
//					new Miss(i * 20, (int) upperLeft.getY() * 20);
			
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++)
				if(tiles[i][(int) lowerRight.getY()].getTypeID() == tileID)
					return new Point(i, (int) lowerRight.getY());
//				else
//					new Miss(i * 20, (int) lowerRight.getY() * 20);
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++)
				if(tiles[(int) upperLeft.getX()][i].getTypeID() == tileID)
					return new Point((int) upperLeft.getX(), i);
//				else
//					new Miss((int) upperLeft.getX() * 20, (int) i * 20);
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++)
				if(tiles[(int) lowerRight.getX()][i].getTypeID() == tileID)
					return new Point((int) lowerRight.getX(), i);
//				else
//					new Miss((int) lowerRight.getX() * 20, (int) i * 20);
			
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
	
	public static Point findTileNeighbour(TestWorld world, int tileID, int startX, int startY){
		Point p = findTile(world, tileID, startX, startY);
		
		Tile[][] tiles = world.getTiles();
		
		Path p1 = null;
		Path p2 = null;
		Path p3 = null;
		Path p4 = null;
		
		if(tiles[(int) p.getX() - 1][(int) p.getY()].getTypeID() == 0)
			p1 = PathFinder.getPath(startX, startY, (int) p.getX() - 1, (int) p.getY());
		if(tiles[(int) p.getX() + 1][(int) p.getY()].getTypeID() == 0)
			p2 = PathFinder.getPath(startX, startY, (int) p.getX() + 1, (int) p.getY());
		if(tiles[(int) p.getX()][(int) p.getY() - 1].getTypeID() == 0)
			p3 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() - 1);
		if(tiles[(int) p.getX()][(int) p.getY() + 1].getTypeID() == 0)
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
	
	public static boolean isAdjacentTile(TestWorld world, int tileID, int startX, int startY){
		Tile[][] tiles = world.getTiles();
		
		try{
			if(tiles[(int) startX - 1][(int) startY].getTypeID() == tileID)
				return true;
			if(tiles[(int) startX + 1][(int) startY].getTypeID() == tileID)
				return true;
			if(tiles[(int) startX][(int) startY - 1].getTypeID() == tileID)
				return true;
			if(tiles[(int) startX][(int) startY + 1].getTypeID() == tileID)
				return true;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		return false;
	}
	
	public static Point findObject(TestWorld world, int tileID, int startX, int startY){
		int[][] tiles = world.getObjectTiles();
		
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
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++)
				if(tiles[i][(int) upperLeft.getY()] == tileID)
					return new Point(i, (int) upperLeft.getY());
//				else
//					new Miss(i * 20, (int) upperLeft.getY() * 20);
			
			for(int i = (int) upperLeft.getX(); i <= lowerRight.getX(); i++)
				if(tiles[i][(int) lowerRight.getY()] == tileID)
					return new Point(i, (int) lowerRight.getY());
//				else
//					new Miss(i * 20, (int) lowerRight.getY() * 20);
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++)
				if(tiles[(int) upperLeft.getX()][i] == tileID)
					return new Point((int) upperLeft.getX(), i);
//				else
//					new Miss((int) upperLeft.getX() * 20, (int) i * 20);
			
			for(int i = (int) upperLeft.getY(); i <= lowerRight.getY(); i++)
				if(tiles[(int) lowerRight.getX()][i] == tileID)
					return new Point((int) lowerRight.getX(), i);
//				else
//					new Miss((int) lowerRight.getX() * 20, (int) i * 20);
			
			
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
	
	public static Point findObjectNeighbour(TestWorld world, int tileID, int startX, int startY){
		Point p = findObject(world, tileID, startX, startY);
//		new Hit(p.x * 20, p.y * 20);
		
		
		int[][] tiles = world.getObjectTiles();
		
		Path p1 = null;
		Path p2 = null;
		Path p3 = null;
		Path p4 = null;
		
		if(tiles[(int) p.getX() - 1][(int) p.getY()] == 0)
			p1 = PathFinder.getPath(startX, startY, (int) p.getX() - 1, (int) p.getY());
		if(tiles[(int) p.getX() + 1][(int) p.getY()] == 0)
			p2 = PathFinder.getPath(startX, startY, (int) p.getX() + 1, (int) p.getY());
		if(tiles[(int) p.getX()][(int) p.getY() - 1] == 0)
			p3 = PathFinder.getPath(startX, startY, (int) p.getX(), (int) p.getY() - 1);
		if(tiles[(int) p.getX()][(int) p.getY() + 1] == 0)
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
	
	public static boolean isAdjacentObject(TestWorld world, int tileID, int startX, int startY){
		int[][] tiles = world.getObjectTiles();
		
		try{
			if(tiles[(int) startX - 1][(int) startY] == tileID)
				return true;
			if(tiles[(int) startX + 1][(int) startY] == tileID)
				return true;
			if(tiles[(int) startX][(int) startY - 1] == tileID)
				return true;
			if(tiles[(int) startX][(int) startY + 1] == tileID)
				return true;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
		return false;
	}
	
	
}
