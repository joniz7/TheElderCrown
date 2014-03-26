package model.path.criteria;

import model.entity.Entity;
import model.entity.top.house.DrinkStorage;

public class IsDrinkStorage implements Criteria{

	@Override
	public boolean match(Entity ge){
		if(ge instanceof DrinkStorage)
			return true;
		return false;
	}
}
