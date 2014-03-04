package model.villager.intentions;

import java.awt.Point;
import java.util.LinkedList;

import model.TestWorld;
import model.path.FindObject;
import model.path.PathFinder;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class SocialPlan extends Plan{

	public SocialPlan(Villager villager) {
		super(villager);
		
		Point p = FindObject.findTileNeighbour((TestWorld)villager.getWorld(), EntityType.Villager, 
				villager.getX(), villager.getY());
		Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
		
		actionQueue.add(new MoveAction(villager, movePath));
		
		actionQueue.addLast(new SocialAction(villager));
	}

}
