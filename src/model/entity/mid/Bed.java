package model.entity.mid;

import util.Copyable;
import util.EntityType;
import model.villager.*;

public class Bed extends MidEntity {
	
	private boolean isClaimedByMale;
	private boolean isClaimedByFemale;
	private Villager female;
	private Villager male;
	private boolean isUsed;

	public Bed(int x, int y, EntityType id) {
		super(x, y, id, false);
		this.male = null;
		this.female = null;
	}

	@Override
	public Copyable copy() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isClaimedByMale() {
		return isClaimedByMale;
	}

	public void setClaimedByMale(boolean isClaimedByMale) {
		this.isClaimedByMale = isClaimedByMale;
	}

	public boolean isClaimedByFemale() {
		return isClaimedByFemale;
	}

	public void setClaimedByFemale(boolean isClaimedByFemale) {
		this.isClaimedByFemale = isClaimedByFemale;
	}

	public Villager getFemale() {
		return female;
	}
	
	/*public Villager getOther(){
		if(female != null){
			return female;
		}else if (male != null){
			return male;
		}
		return null;
	}*/
	
	public Villager getOther(Villager first){
		if(!female.equals(first)){
			return female;
		}else if(!male.equals(first)){
			return male;
		}else{
			return null;
		}
	}

	public void setFemale(Villager female) {
		this.female = female;
	}

	public Villager getMale() {
		return male;
	}

	public void setMale(Villager male) {
		this.male = male;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	

}
