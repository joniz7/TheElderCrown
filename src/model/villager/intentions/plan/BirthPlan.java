package model.villager.intentions.plan;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.util.pathfinding.Path;

import model.path.PathFinder;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.BirthAction;
import model.villager.intentions.action.MoveAction;

public class BirthPlan extends Plan {
	
	private Point bedPos;

	public BirthPlan(Villager villager) {
		super(villager,"Needs to give birth");
		actionQueue = new LinkedList<Action>();
		villager.updateStatus("pregnant");
		this.bedPos = villager.getBedPos();
	

	
	Path movePath = PathFinder.getPath(villager.getX(), villager.getY(), bedPos.x, bedPos.y);
	actionQueue.add(new MoveAction(villager, movePath));
	actionQueue.add(new BirthAction(villager));
	
	}
	
}
