package model.path.criteria;

import model.entity.Entity;
<<<<<<< HEAD
import model.entity.bottom.WaterTile;
import model.entity.top.Tree;
import model.item.food.FoodSource;
=======
>>>>>>> c816b2f94d89ee8b2c60b6bfd042a4fae9f04566
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
		}else if(ge instanceof WaterTile){
			return true;
		}
		return false;
	}

}
