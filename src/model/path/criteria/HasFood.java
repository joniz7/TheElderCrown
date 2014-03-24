package model.path.criteria;

import model.entity.Entity;
import model.entity.top.Tree;
import model.item.food.FoodSource;

public class HasFood implements Criteria{

	@Override
	public boolean match(Entity ge){
		if(ge instanceof FoodSource){
			FoodSource fs = (FoodSource) ge;

			if(fs.hasFood()){
				return true;
			}else
				return false;	
		}
		return false;
	}

}
