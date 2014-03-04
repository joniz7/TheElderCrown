package model.villager.intentions;

import util.EntityType;
import model.World;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFruit;
import model.villager.Villager;

public class SocialAction extends Action{

	private int stacks, stacksToSocial;
	
	public SocialAction(Villager villager) {
		super(villager);
	}

	@Override
	public void tick(World world) {
		if(FindObject.getAdjacentObject(world, new HasFruit(), EntityType.VILLAGER, villager.getX(),
				villager.getY()) != null) {
			Tree tree = (Tree) FindObject.getAdjacentObject(world, new HasFruit(), EntityType.VILLAGER, villager.getX(),
					villager.getY());
			villager.updateStatus("speaking");
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
