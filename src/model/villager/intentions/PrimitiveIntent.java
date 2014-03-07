package model.villager.intentions;

import model.villager.Villager;

/**
 * An intent that should always be present in the Agent's mind.
 * 
 * @author Niklas
 */
public abstract class PrimitiveIntent extends Intent {

	public PrimitiveIntent(int cost, Villager villager) {
		super(cost, villager);
	}

}
