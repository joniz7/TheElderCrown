package model.entity.bottom;

import model.villager.Villager;
import util.Copyable;
import util.EntityType;

public class Bed extends BottomEntity {
	
	private static final long serialVersionUID = 1L;
	private boolean isClaimedByMale;
	private boolean isClaimedByFemale;
	private Villager female;
	private Villager male;
	private int UsedBy = 0;
	private float sleepValue = 0.2f;

	public Bed(int x, int y) {
		super(x, y, EntityType.BED, false);
		this.male = null;
		this.female = null;
		this.isClaimedByFemale = false;
		this.isClaimedByMale = false;
	}

	public Copyable copy() {
		Bed copy = new Bed(x,y);
		copy.isClaimedByFemale = isClaimedByFemale;
		copy.isClaimedByMale = isClaimedByMale;
		copy.female = female;
		copy.male = male;
		copy.UsedBy = UsedBy;
		return copy;
	}
	
	@Override
	public float getSleepValue(){
		return sleepValue;
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
		if(female != null && !female.equals(first)){
			return female;
		}else if(male !=null && !male.equals(first)){
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
	
	public void removeFemale(){
		this.female = null;
	}
	
	public void removeMale(){
		this.male = null;
	}
	
	public void removeMe(Villager vill){
		if(female == vill){
			this.female=null;
		}else if(male == vill){
			this.male = null;
		}
	}


	public int UsedBy() {
		return UsedBy;
	}

	public void setUsed() {
		UsedBy+=1;
	}
	
	public void removeUsed() {
		UsedBy-=1;
	}

	public boolean isFree() {
		return (isClaimedByMale == false && isClaimedByFemale == false);
		
	}

	

}
