package model.path.criteria;

import model.entity.Entity;
import model.entity.mid.Bed;
import model.villager.Villager;

public class IsUnclaimed implements Criteria {
	
	private Villager villager;
	
	public IsUnclaimed(Villager villager){
		villager = this.villager;
	}

	@Override
	public boolean match(Entity ge) {
		Bed bed = (Bed) ge;
		if(villager.isMale() && bed.isClaimedByFemale()){
			return true;
		}else if(villager.isFemale() && bed.isClaimedByMale()){
			return true;
		}
		return false;
	}

}
