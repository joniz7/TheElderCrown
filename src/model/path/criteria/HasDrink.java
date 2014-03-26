package model.path.criteria;

import model.entity.Entity;
import model.entity.bottom.WaterTile;
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
