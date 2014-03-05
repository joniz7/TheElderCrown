package model.villager.intentions;

import java.awt.Point;

import model.path.PathFinder;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

public class MovePlan extends Plan {

	public MovePlan(Villager villager, Point pos) {
		super(villager);
		
		Path movePath = PathFinder.getPath(villager.getX(), villager.getY(), pos.x, pos.y);
				
		actionQueue.add(new MoveAction(villager, movePath));
	}

}
