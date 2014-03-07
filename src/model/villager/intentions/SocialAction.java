package model.villager.intentions;

import util.EntityType;
import model.World;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFruit;
import model.path.criteria.isSocial;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;

public class SocialAction extends Action{

	private int stacks, stacksToSocial;
	
	public SocialAction(Villager villager) {
		super(villager);
	}

	@Override
	public void tick(VillagersWorldPerception world) {
		if(FindObject.getAdjacentObject(world, new isSocial(), EntityType.VILLAGER, villager.getX(),
				villager.getY()) != null) {
			villager.updateStatus("talking");
			villager.satisfySocial(0.1f);
			stacks++;
			if(stacks > stacksToSocial){
				villager.updateStatus("statusEnd");
				actionFinished();
			}
				
		}else{
			villager.updateStatus("statusEnd");
			actionFailed();
		}
		
	}

}
