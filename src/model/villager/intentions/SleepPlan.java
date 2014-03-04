package model.villager.intentions;

import java.awt.Point;
import java.util.LinkedList;

import model.entity.bottom.HouseFloor;
import model.path.FindObject;
import model.path.PathFinder;
import model.villager.Villager;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class SleepPlan extends Plan {

	public SleepPlan(Villager villager){
		super(villager);
		actionQueue = new LinkedList<Action>();
		
		Point floorPos = FindObject.findTile2(villager.getWorld(),EntityType.HOUSE_FLOOR, villager.getX(),villager.getY());

		if(floorPos != null){
			Point villPos = new Point(villager.getX(),villager.getY());
			if(villager.getWorld().getBotEntities().get(villPos) instanceof HouseFloor){
				actionQueue.addLast(new SleepAction(villager));
			}else{
			Path movePath = PathFinder.getPath(villager.getX(), villager.getY(), floorPos.x, floorPos.y);

			actionQueue.add(new MoveAction(villager, movePath));
			actionQueue.addLast(new SleepAction(villager));
			}
		
		}
	
		
	}
}