package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import model.entity.Agent;
import model.entity.Entity;
import util.Tickable;

/**
 * A data container for a map.
 * May contain entities, agents and other tickables.
 * 
 * Shouldn't be used as storage while simulation is running -
 * should only be used as a container when saving/loading maps.
 *  
 * @author Niklas
 */
public class WorldMap implements Serializable {

	private static final long serialVersionUID = 2702152764549447664L;
	
	// Tickable objects (e.g. trees)
	public List<Tickable> tickables;
	// Agents (e.g. villagers)
	public HashMap<Point, Agent> agents;
	
	public HashMap<Point, Entity> botEntities;
	public HashMap<Point, Entity> midEntities;
	public HashMap<Point, Entity> topEntities;
	
}
