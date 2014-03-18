package model.path.criteria;

import model.entity.Entity;
import model.entity.top.house.FoodStorage;

public class IsFoodStorage implements Criteria{

	@Override
	public boolean match(Entity ge){
		if(ge instanceof FoodStorage)
			return true;
		return false;
	}
}
