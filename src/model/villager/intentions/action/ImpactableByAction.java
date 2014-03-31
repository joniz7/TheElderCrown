package model.villager.intentions.action;

import java.awt.Point;
import java.util.HashMap;

import org.newdawn.slick.util.pathfinding.PathFindingContext;

import model.entity.Entity;

/**
 * An interface to be implemented by 'real world' to enable the
 * actions to affect it without dependency issues.
 * 
 * @author Karl-Agnes
 *
 */
public interface ImpactableByAction {

	boolean blocked(PathFindingContext pfc, int x, int y);
	
	boolean blocked(PathFindingContext pfc, Point p);

	int getWidthInTiles();

	int getHeightInTiles();

	HashMap<Point, Entity> getBotEntities();

	HashMap<Point, Entity> getMidEntities();

	HashMap<Point, Entity> getTopEntities();

}
