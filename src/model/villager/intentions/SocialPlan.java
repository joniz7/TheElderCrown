package model.villager.intentions;

import java.awt.Point;
import java.util.LinkedList;

import model.TestWorld;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.PathFinder;
import model.path.criteria.HasFruit;
import model.path.criteria.isSocial;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class SocialPlan extends Plan{

	public SocialPlan(Villager villager) {
		super(villager);
		
		System.out.println("Starting to find another villager \n");
		Villager otherVillager = (Villager) FindObject.getAdjacentObject(villager.getWorld(), new isSocial(), 
				EntityType.VILLAGER, villager.getX(), villager.getY());
		if(otherVillager != null){
			actionQueue.addLast(new SocialAction(villager));
			otherVillager.setAction(new SocialPlan(otherVillager));
		}else{
			Point p = FindObject.findObjectNeighbour(villager.getWorld(), new isSocial(), EntityType.VILLAGER, 
					villager.getX(), villager.getY());
			Path movePath = null;
			if(p != null)
				movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);

			actionQueue.add(new MoveAction(villager, movePath));
			actionQueue.addLast(new SocialAction(villager));
		}
	}

}
