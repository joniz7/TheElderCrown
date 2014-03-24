package model.villager.intentions.plan;

import java.awt.Point;

import model.path.PathFinder;
import model.villager.Villager;
import model.villager.intentions.action.MoveAction;

import org.newdawn.slick.util.pathfinding.Path;

public class MovePlan extends Plan {

	public MovePlan(Villager villager, Point pos) {
		super(villager);
		name = "Needs to move";
		
		Path movePath = PathFinder.getPath(villager.getX(), villager.getY(), pos.x, pos.y);
				
		actionQueue.add(new MoveAction(villager, movePath));
	}

}
