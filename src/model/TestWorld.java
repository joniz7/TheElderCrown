package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.objects.Tree;
import model.path.PathFinder;
import model.tile.GrassTile;
import model.tile.WaterTile;
import model.villager.Villager;

import model.entity.*;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import resource.ObjectID;

public class TestWorld extends GamePhase implements TileBasedMap{
	
	private final int WIDTH = 256, HEIGHT = 256;
	
	private HashMap<Point, BottomLayerGraphicalEntity> tiles;
	private HashMap<Point, MiddleLayerGraphicalEntity> midObjects;
	private HashMap<Point, TopLayerGraphicalEntity> topObjects;
	private ArrayList<Tree> trees = new ArrayList<Tree>();
	
	private Random rnd = new Random();
	
	public TestWorld(){
		tiles = new HashMap<Point, BottomLayerGraphicalEntity>();
		midObjects = new HashMap<Point, MiddleLayerGraphicalEntity>();
		topObjects = new HashMap<Point, TopLayerGraphicalEntity>();
		
		for(int i = 0; i < WIDTH; i++)
			for(int j = 0; j < HEIGHT; j++){
				GrassTile gt = new GrassTile(i, j);
				gt.setTileID(0);
				tiles.put(new Point(i, j), gt);
			}
		
		createLakes();
		
		for(int i = 0; i < WIDTH - 1; i++)
			for(int j = 0; j < HEIGHT - 1; j++)
				if(rnd.nextInt(140) == 0 && tiles.get(new Point(i + 1, j + 1)).getObjectID()==ObjectID.GRASS_TILE){
					Tree tree = new Tree(i + 1, j + 1);
					trees.add(tree);
					tickables.add(tree);
					topObjects.put(new Point(i + 1, j + 1), tree);
				}
		
		new PathFinder(this);
		
		for(int i = 118; i < 123; i++)
			for(int j = 118; j < 123; j++)
				tiles.put(new Point(i, j), new GrassTile(i, j));
		
		viewX = 100 * 20;
		viewY = 100 * 20;
		
		for(int i = 0; i < 30; i++){
			Villager villager = new Villager(this, 120, 120);
			tickables.add(villager);
		}
//		villager = new Villager(this, 20, 21);
//		tickables.add(villager);
//		villager = new Villager(this, 21, 20);
//		tickables.add(villager);
	}
	
	private void createLakes(){
		ArrayList<Point> centers = new ArrayList<Point>();
		
		//Create random centerpoints for lakes
		for(int i = 0; i < 150; i++){
			int x = rnd.nextInt(WIDTH);
			int y = rnd.nextInt(HEIGHT);
			centers.add(new Point(x, y));
			tiles.put(new Point(x, y), new WaterTile(x, y));
		}
		
		//weight and loss defines the sizes of lakes
		float weight = 1f, loss = 0.17f;
		ArrayList<Point> oldWater = new ArrayList<Point>();
		for(Point c : centers){
			boolean lakeDone = false;
			weight = 1f;
			
			oldWater.add(c);
			
			while(!lakeDone){
				ArrayList<Point> newWater = new ArrayList<Point>();
				for(Point p : oldWater){
					if(rnd.nextFloat() < weight && p.x != 0 && tiles.get(new Point(p.x - 1, p.y)) instanceof GrassTile){
						tiles.put(new Point(p.x-1, p.y), new WaterTile((p.x-1), p.y));
						newWater.add(new Point(p.x-1, p.y));
					}
					if(rnd.nextFloat() < weight  && p.x != 79 && tiles.get(new Point(p.x + 1, p.y)) instanceof GrassTile){
						tiles.put(new Point(p.x+1, p.y), new WaterTile((p.x+1), p.y));
						newWater.add(new Point(p.x+1, p.y));
					}	
					if(rnd.nextFloat() < weight  && p.y != 0 && tiles.get(new Point(p.x, p.y - 1)) instanceof GrassTile){
						tiles.put(new Point(p.x, p.y-1), new WaterTile(p.x, (p.y-1)));
						newWater.add(new Point(p.x, p.y-1));
					}
					if(rnd.nextFloat() < weight  && p.y != 79 && tiles.get(new Point(p.x, p.y + 1)) instanceof GrassTile){
						tiles.put(new Point(p.x, p.y+1), new WaterTile(p.x, (p.y+1)));
						newWater.add(new Point(p.x, p.y+1));
					}
				}
				
				oldWater = newWater;
				
				
				weight = weight - 0.1f;
				if(weight <= 0)
					lakeDone = true;
			}
		}
		
	}

	@Override
	public boolean blocked(PathFindingContext pfc, int x, int y){
		return tiles.get(new Point(x, y)) instanceof WaterTile;
	}

	@Override
	public float getCost(PathFindingContext pfc, int x, int y){
		return 1.0f;
	}

	@Override
	public int getHeightInTiles(){
		return HEIGHT;
	}

	@Override
	public int getWidthInTiles(){
		return WIDTH;
	}

	@Override
	public void pathFinderVisited(int x, int y){
		
	}

	public HashMap<Point, BottomLayerGraphicalEntity> getTiles() {
		return tiles;
	}
	
	public HashMap<Point, MiddleLayerGraphicalEntity> getMidObjects(){
		return midObjects;
	}
	
	public HashMap<Point, TopLayerGraphicalEntity> getTopObjects(){
		return topObjects;
	}
	
	public Tree getTree(int tileX, int tileY){
		for(Tree x : trees)
			if(x.getTileX() == tileX && x.getTileY() == tileY){
				System.out.println("World: Tree found");
				return x;
			}
		System.out.println("World: Tree not found");
		return null;
	}
	
	
}
