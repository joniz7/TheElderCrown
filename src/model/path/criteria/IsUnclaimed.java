package model.path.criteria;

import model.entity.Entity;
import model.entity.bottom.Bed;
import model.villager.Villager;

public class IsUnclaimed implements Criteria {
	
	private Villager villager;


	public IsUnclaimed(Villager villager){
		this.villager = villager;
	}

	@Override
	public boolean match(Entity ge) {
		if(ge instanceof Bed){
			Bed bed = (Bed) ge;
			if(villager.isMale() &&
					(!bed.isClaimedByMale() ||
							bed.getMaleClaimantID() ==
							villager.getId())){
				return true;
			}else if(villager.isFemale() && (!bed.isClaimedByFemale() || bed.getFemaleClaimantID() == villager.getId())){
				return true;
			}
		}
		return false;
		//return true;
	}
	
	public String toString(){
		return "Villager sex: " +villager.getSex();
	}

}
