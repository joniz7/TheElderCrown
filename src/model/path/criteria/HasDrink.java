package model.path.criteria;

import model.entity.Entity;
import model.entity.top.Tree;
import model.item.food.FoodSource;
import model.item.liquid.DrinkSource;

public class HasDrink implements Criteria{

	@Override
	public boolean match(Entity ge){
		if(ge instanceof DrinkSource){
			DrinkSource fs = (DrinkSource) ge;

			if(fs.hasDrink()){
				return true;
			}else
				return false;	
		}
		return false;
	}

}
