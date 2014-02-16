package model.villager.brain;

import model.villager.intention.Intention;

public class Instinct extends BrainInput{

	protected Intention intent;

	public Instinct(Intention intent) {
		super();
		this.intent = intent;
	}

	public Intention getIntent() {
		return intent;
	}
}
