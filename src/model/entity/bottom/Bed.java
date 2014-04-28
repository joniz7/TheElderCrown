package model.entity.bottom;

import java.awt.Point;

import model.World;
import model.path.FindEntity;
import model.villager.Villager;
import util.Copyable;
import util.EntityId;
import util.EntityType;
import util.Tickable;

public class Bed extends BottomEntity implements Tickable {
	
	private static final long serialVersionUID = 1L;
	public static final int UNOCCUPIED_ID = EntityId.nextId();
	//Claimants are identities saved permanently
	private int maleClaimantID, femaleClaimantID;
	//female/male is the villager currently in bed
	private Villager female, male;
	private float sleepValue = 0.2f;
	private Villager savedVillager;
	private World w;
	private int x,y;

	public Bed(int x, int y, World w) {
		super(x, y, EntityType.BED, false);
		this.male = null;
		this.female = null;
		this.maleClaimantID = UNOCCUPIED_ID;
		this.femaleClaimantID = UNOCCUPIED_ID;
		this.w = w;
		this.x = x;
		this.y = y;
		//this.UsedBy=0;
	}

	public Copyable copy() {
		Bed copy = new Bed(x,y,w);
		copy.maleClaimantID = maleClaimantID;
		copy.femaleClaimantID = femaleClaimantID;
		copy.female = female;
		copy.male = male;
		//copy.UsedBy = UsedBy;
		copy.savedVillager = savedVillager;
		return copy;
	}
	
	@Override
	public float getSleepValue(){
		return sleepValue;
	}

	public boolean isClaimedByMale() {
		return !(maleClaimantID == UNOCCUPIED_ID);
	}

	public boolean isClaimedByFemale() {
		return !(femaleClaimantID == UNOCCUPIED_ID);
	}
	
	/**
	 * A method to see whether the male claimant is currently in bed.
	 * @return true if the male spot is occupied, false otherwise.
	 */
	public boolean isMaleInBed(){
		return male != null;
	}
	
	/**
	 * A method to see whether the female claimant is currently in bed.
	 * @return true if the female spot is occupied, false otherwise.
	 */
	public boolean isFemaleInBed(){
		return female != null;
	}

	public Villager getFemale() {
		return female;
	}
	
	public Villager getMale() {
		return male;
	}
	
	/*public Villager getOther(){
		if(female != null){
			return female;
		}else if (male != null){
			return male;
		}
		return null;
	}*/
	
	/**
	 * Retrieves the partner of a specific Villager if that Villager is in bed right now.
	 * @param first The Villager that you want to find the partner of.
	 * @return the partner of the specified Villager, IF it is in bed right now.
	 */
	public Villager getOther(Villager first){
		if(female != null && !female.equals(first)){
			return female;
		}else if(male !=null && !male.equals(first)){
			return male;
		}else{
			return null;
		}
	}

	public void setFemaleClaimant(Villager female) {
		this.femaleClaimantID = female.getId();
	}

	public void setMaleClaimant(Villager male) {
		this.maleClaimantID = male.getId();
	}

	public void removeFemaleClaimant(){
		femaleClaimantID = 0;
	}
	
	public void removeMaleClaimant(){
		maleClaimantID = 0;
	}
	
	public void removeMe(Villager vill){
		if(female.equals(vill)){
			this.female = null;
			this.femaleClaimantID = UNOCCUPIED_ID;
		}else if(male.equals(vill)){
			this.male = null;
			this.maleClaimantID = UNOCCUPIED_ID;
		}
	}


	/*public int UsedBy() {
		return UsedBy;
	}

	public void setUsed() {
		UsedBy = UsedBy+1;
	}
	*/
	
	
	/**
	 * A method to check whether or not the bed is totally free;
	 * @return
	 */
	public boolean isFree() {
		return (maleClaimantID == UNOCCUPIED_ID && femaleClaimantID == UNOCCUPIED_ID);
		
	}
	
	/**
	 * Gets a villager into this bed and claims it if it wasn't claimed by anyone else.
	 * @param v the Villager to get into bed.
	 * @return True if the Villager got into bed, false otherwise.
	 */
	public boolean use(Villager v){
		if(v.isMale() && (maleClaimantID == UNOCCUPIED_ID || maleClaimantID == v.getId())){
			maleClaimantID = v.getId();
			v.setBlocking(false);
			male = v;
			if(isFemaleInBed()){
				w.addSleeping(female, new Point(x,y));
			}
			return true;
		}else if(v.isFemale() && (femaleClaimantID == UNOCCUPIED_ID || femaleClaimantID == v.getId())){
			femaleClaimantID = v.getId();
			v.setBlocking(false);
			female = v;
			if(isMaleInBed()){
				w.addSleeping(male, new Point(x,y));
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Makes the Villager get out of bed and into the world.
	 * 
	 * @param v the Villager to get out of bed.
	 * @return true if the Villager could get out of this bed, and was in it. False otherwise.
	 */
	public boolean stopUsing(Villager v){
		Point p = new Point(x,y);
		Point t = FindEntity.FreeTile(v.getWorld(), x, y);
		if(!w.blocked(null, x, y)){
			v.setBlocking(true);
			//System.out.println("Bed: Used by: "+UsedBy());
			if(v.equals(male)){
				male = null;
				//w.addEntity(p, v);
				//v.updatePos(v.getX(), v.getY());
				if(!isFemaleInBed() && w.getSleeping().containsKey(p)){
					//w.addEntity(t, v);
					//v.updatePos(v.getX(), v.getY());
					w.removeSleeping(p);
				}else if(isFemaleInBed()){
					w.addSleeping(female, p);
				}
				w.reconnectVillager(p, v);
				System.out.println("------------Removed male: "+v.getName() );
				v.setExplore();
			}else if(v.equals(female)){
				female = null;
				//w.addEntity(p, v);
				//v.updatePos(v.getX(), v.getY());
				if(!isMaleInBed() && w.getSleeping().containsKey(p)){
					//w.addEntity(t, v);
					//v.updatePos(v.getX(), v.getY());
					w.removeSleeping(p);
				}else if(isMaleInBed()){
					w.addSleeping(male, p);
				}
				w.reconnectVillager(p, v);
				System.out.println("----------Removed female: "+v.getName() );
				v.setExplore();
			}/*else{
				
			}*/
			return true;
		}
		return false;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	

}
