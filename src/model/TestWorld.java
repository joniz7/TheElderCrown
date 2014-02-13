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

public class TestWorld extends GamePhase implements TileBasedMap{
	
	private final int WIDTH = 80, HEIGHT = 80;
	
//	private Tile[][] tiles = new Tile[WIDTH][HEIGHT];
	private HashMap<Point, BottomLayerGraphicalEntity> tiles;
	private int[][] objectTiles = new int[WIDTH][HEIGHT];
	private ArrayList<Tree> trees = new ArrayList<Tree>();
	
	private Random rnd = new Random();
	
	public TestWorld(){
		tiles = new HashMap<Point, BottomLayerGraphicalEntity>();
		for(int i = 0; i < WIDTH; i++)
			for(int j = 0; j < HEIGHT; j++){
				GrassTile gt = new GrassTile(i, j);
				gt.setTileID(0);
				tiles.put(new Point(i, j), gt);
			}
		
		createLakes();

//		for(int i = 0; i < WIDTH - 1; i++)
//			for(int j = 0; j < HEIGHT - 1; j++)
//				if(rnd.nextInt(140) == 0 && tiles.get(new Point(i + 1, j + 1)) instanceof GrassTile){
//					Tree tree = new Tree(i + 1, j + 1, this);
//					trees.add(tree);
//					tickables.add(tree);
//					objectTiles[i + 1][j + 1] = 101;
//				}

		
//		WorldController vpl = new WorldController(this);
//		Frame.getCanvas().addKeyListener(vpl);
//		tickables.add(vpl);
		
		new PathFinder(this);
		
		for(int i = 18; i < 23; i++)
			for(int j = 18; j < 23; j++)
				tiles.put(new Point(i, j), new GrassTile(i * 20, j * 20));
		
		Villager villager = new Villager(this, 20, 20);
		tickables.add(villager);
		villager = new Villager(this, 20, 21);
		tickables.add(villager);
		villager = new Villager(this, 21, 20);
		tickables.add(villager);
	}
	
	private void createLakes(){
		ArrayList<Point> centers = new ArrayList<Point>();
		
		//Create random centerpoints for lakes
		for(int i = 0; i < 15; i++){
			int x = rnd.nextInt(WIDTH);
			int y = rnd.nextInt(HEIGHT);
			centers.add(new Point(x, y));
			tiles.put(new Point(x, y), new GrassTile(x * 20, y * 20));
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
						tiles.put(new Point(p.x-1, p.y), new WaterTile((p.x-1) * 20, p.y * 20));
						newWater.add(new Point(p.x-1, p.y));
					}
					if(rnd.nextFloat() < weight  && p.x != 79 && tiles.get(new Point(p.x + 1, p.y)) instanceof GrassTile){
						tiles.put(new Point(p.x+1, p.y), new WaterTile((p.x+1) * 20, p.y * 20));
						newWater.add(new Point(p.x+1, p.y));
					}	
					if(rnd.nextFloat() < weight  && p.y != 0 && tiles.get(new Point(p.x, p.y - 1)) instanceof GrassTile){
						tiles.put(new Point(p.x, p.y-1), new WaterTile(p.x * 20, (p.y-1) * 20));
						newWater.add(new Point(p.x, p.y-1));
					}
					if(rnd.nextFloat() < weight  && p.y != 79 && tiles.get(new Point(p.x, p.y + 1)) instanceof GrassTile){
						tiles.put(new Point(p.x, p.y+1), new WaterTile(p.x * 20, (p.y+1) * 20));
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

	public int[][] getObjectTiles() {
		return objectTiles;
	}

	public void setObjectIndexID(int tileX, int tileY, int ID){
		objectTiles[tileX][tileY] = ID;
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
