package model.villager.relations;

import java.util.HashMap;

/**
 * @author Simon E
 */
public class RelationHandler {

	private HashMap<Integer, Float> relations = new HashMap<Integer, Float>();
	private final float MAX_RELATION = 100f, MIN_RELATION = -100f;
	
	/**
	 * Increase a relation. If the relation is not present, a new one
	 * is created with value 0 + amount.
	 * 
	 * @param villagerID - The ID of the villager
	 * @param amount
	 */
	public void increaseRelation(Integer villagerID, float amount){
		if(relations.containsKey(villagerID)){
			float oldF = relations.get(villagerID);
			float newF = oldF + amount;
			if(newF > MAX_RELATION)
				relations.put(villagerID, MAX_RELATION);
			else
				relations.put(villagerID, newF);
		}else{
			relations.put(villagerID, 0 + amount);
		}
	}
	
	/**
	 * Decrease a relation. If the relation is not present, a new one
	 * is created with value 0 - amount.
	 * 
	 * @param villagerID - The ID of the villager
	 * @param amount
	 */
	public void decreaseRelation(Integer villagerID, float amount){
		if(relations.containsKey(villagerID)){
			float oldF = relations.get(villagerID);
			float newF = oldF - amount;
			if(newF < MIN_RELATION)
				relations.put(villagerID, MIN_RELATION);
			else
				relations.put(villagerID, newF);
		}else{
			relations.put(villagerID, 0 - amount);
		}
	}
	
	/**
	 * Remove a relation
	 * 
	 * @param villagerID - the villager to remove
	 */
	public void removeRelation(Integer villagerID){
		relations.remove(villagerID);
	}
	
	/**
	 * Add a new relation with value 0
	 * 
	 * @param villagerID - the villager to add
	 */
	public void addNewRelation(Integer villagerID){
		relations.put(villagerID, 0f);
	}
	
	/**
	 * Add a new relation with the specified value
	 * 
	 * @param villagerID - the villager to add
	 */
	public void addNewRelation(Integer villagerID, float amount){
		relations.put(villagerID, amount);
	}
}
