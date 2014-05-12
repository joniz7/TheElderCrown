package model.villager.intentions.plan;

import java.awt.Point;
import java.util.LinkedList;

import model.path.FindEntity;
import model.path.PathFinder;
import model.path.criteria.HasDrink;
import model.villager.Villager;
import model.villager.intentions.action.Action;
import model.villager.intentions.action.DrinkAction;
import model.villager.intentions.action.MoveAction;

import org.newdawn.slick.util.pathfinding.Path;

import util.EntityType;

public class DrinkPlan extends Plan{

	public DrinkPlan(Villager villager){
		super(villager, "Wants to drink");
		actionQueue = new LinkedList<Action>();
		
		if(FindEntity.isAdjacentTile(villager.getWorld(), EntityType.WATER_TILE, 
			villager.getX(), villager.getY())){
			
			addAction(new DrinkAction(villager));

			System.out.println("ALREADY ADJACENT WATER");
			return;
		}
		
		Point p = FindEntity.findBotEntityNeighbour(villager.getWorld(), EntityType.WATER_TILE, 

				villager.getX(), villager.getY());
		Point p2 = null;
		if(p == null) {
			p2 = FindEntity.findTopMidEntityNeighbour(villager.getWorld(), new HasDrink(), null,
					villager.getX(), villager.getY());
		}
		
		if(p!=null){
			Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p.x, p.y);
			Path movePath2;
			if(p2 != null){
				movePath2 = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p2.x, p2.y);
				if(movePath2.getLength() >= movePath.getLength()){
					addAction(new MoveAction(villager, movePath));
				}else if(movePath.getLength() > movePath2.getLength()){
					addAction(new MoveAction(villager, movePath2));
				}
				addAction(new DrinkAction(villager));
				return;
			} else {
				addAction(new MoveAction(villager, movePath));
				addAction(new DrinkAction(villager));
			}
		}else if(p2 != null){
			Path movePath = PathFinder.getPathToAdjacent(villager.getX(), villager.getY(), p2.x, p2.y);
			
			addAction(new MoveAction(villager, movePath));
			
			
			addAction(new DrinkAction(villager));
		}
		else{
			villager.setExplore();
			isFinished=true;
		}
	}
	
	@Override
	public void retryAction() {
		// TODO Auto-generated method stub
		
	}
}
