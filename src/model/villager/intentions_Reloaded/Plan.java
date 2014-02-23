package model.villager.intentions_Reloaded;

import model.villager.Villager;

public class Plan {

	protected Villager villager;
	protected Action activeAction;
	
	public Plan(Villager villager) {
		super();
		this.villager = villager;
	}

	public Action getActiveAction() {
		return activeAction;
	}
	
	
}
