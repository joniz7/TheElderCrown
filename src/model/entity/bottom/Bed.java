package model.entity.bottom;

import java.awt.Point;

import model.RandomWorld;
import model.World;
import model.villager.Villager;
import util.Copyable;
import util.EntityType;
import util.Tickable;

public class Bed extends BottomEntity implements Tickable {
	
	private static final long serialVersionUID = 1L;
	private boolean isClaimedByMale;
	private boolean isClaimedByFemale;
	private Villager female;
	private Villager male;
	private int UsedBy;
	private float sleepValue = 0.2f;
	private Villager savedVillager;
	private World w;
	private int x,y;

	public Bed(int x, int y, World w) {
		super(x, y, EntityType.BED, false);
		this.male = null;
		this.female = null;
		this.isClaimedByFemale = false;
		this.isClaimedByMale = false;
		this.w = w;
		this.x = x;
		this.y = y;
		this.UsedBy=0;
	}

	public Copyable copy() {
		Bed copy = new Bed(x,y,w);
		copy.isClaimedByFemale = isClaimedByFemale;
		copy.isClaimedByMale = isClaimedByMale;
		copy.female = female;
		copy.male = male;
		copy.UsedBy = UsedBy;
		copy.savedVillager = savedVillager;
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
		UsedBy = UsedBy+1;
	}
	
	public void removeUsed() {
		UsedBy = UsedBy-1;
	}

	public boolean isFree() {
		return (isClaimedByMale == false && isClaimedByFemale == false);
		
	}
	
	public void setSaved(Villager v){
		this.savedVillager = v;
	}
	
	public Villager getSaved(){
		return savedVillager;
	}
	
	public void use(Villager v){
		v.setBlocking(false);
		if(v.isMale() && !isClaimedByMale){
			setMale(v);
			setClaimedByMale(true);
		}else if(v.isFemale() && !isClaimedByFemale){
			setFemale(v);
			setClaimedByFemale(true);
		}
		setUsed();
		System.out.println("----------"+UsedBy+"-----------");
		if(UsedBy() > 1){
			w.addSleeping(getOther(v),new Point(x,y));
			System.out.println(getOther(v));
			savedVillager = getOther(v);
		}
		if(savedVillager == null)
			savedVillager = v;
	}
	
	public void stopUsing(Villager v){
		removeUsed();
		v.setBlocking(true);
		//System.out.println("Bed: Used by: "+UsedBy());
		if(savedVillager == v && UsedBy() > 0){
			savedVillager=getOther(v);
			if(savedVillager != null){
				w.removeSleeping(new Point(x,y));
				w.reconnectVillager(new Point(x,y),savedVillager);
				savedVillager.updatePos(x,y);
				
			}
		}else if(savedVillager == v){
			savedVillager = null;
		}else if(savedVillager != v && savedVillager != null){
			w.removeSleeping(new Point(x,y));
			w.reconnectVillager(new Point(x,y),savedVillager);
			savedVillager.updatePos(x,y);
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	

}
