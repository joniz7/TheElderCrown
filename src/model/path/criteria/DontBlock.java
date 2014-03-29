package model.path.criteria;

import model.entity.Entity;
import model.entity.top.Tree;
import model.entity.top.house.FoodStorage;
import model.entity.top.house.HouseDoor;
import model.item.liquid.DrinkSource;

public class DontBlock implements Criteria {

	@Override
	public boolean match(Entity ge) {
		if(ge instanceof FoodStorage || 
				ge instanceof DrinkSource || 
				ge instanceof Tree || 
				ge instanceof HouseDoor){
			return true;
		}
		return false;
	}

}
