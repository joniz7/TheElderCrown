package model.villager.intentions;

import model.villager.Villager;

/**
 * An intent that should always be present in the Agent's mind.
 * 
 * @author Niklas
 */
public abstract class PrimitiveIntent extends Intent {

	public PrimitiveIntent(Villager villager) {
		super(villager);
	}

}
