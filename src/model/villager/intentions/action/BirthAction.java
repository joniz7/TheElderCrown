package model.villager.intentions.action;

import java.awt.Point;

import util.Constants;
import util.EntityType;
import util.UtilClass;
import model.World;
import model.path.FindEntity;
import model.villager.Villager;

public class BirthAction extends Action {
	
	private boolean firstTick;
	private int time, stacks;
	private Villager villager;
	private World w;
	
	public BirthAction(Villager villager) {
		super(villager);
		name = "Giving birth";
		this.firstTick=true;
		this.time = UtilClass.getRandomInt(5, 1);
		this.stacks = 0;
		this.villager = villager;

	}

	@Override
	public void tick(ImpactableByAction world) {
		if(firstTick){
			villager.updateStatus("giving birth");
			villager.setBlocking(false);
			firstTick = false; 
			w = (World) world;
		}
		stacks++;
		//if(stacks >= time*Constants.TICKS_HOUR){
			Point p = FindEntity.FreeTile(world, villager.getX(), villager.getY());
			if(p != null){
				villager.setBlocking(true);
				w.newVillager(p, 0);
				villager.setPregnant(false);
				villager.updateStatus("statusEnd");
				actionFinished();
			}else
				actionFailed();
		//}

	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
