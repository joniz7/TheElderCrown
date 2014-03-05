package model.path.criteria;

import model.entity.Entity;
import model.villager.Villager;

public class isSocial implements Criteria{

	@Override
	public boolean match(Entity ge){
		Villager villager = (Villager) ge;

		return (villager.getSocial()<-15);

	}

}
