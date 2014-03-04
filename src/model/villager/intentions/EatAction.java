package model.villager.intentions;

import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFruit;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;
import util.EntityType;

public class EatAction extends Action{

	private int stacks, stacksToEat;
	
	public EatAction(Villager villager) {
		super(villager);
		stacksToEat = 500;
	}

	@Override
	public void tick(VillagersWorldPerception world){
		if(FindObject.getAdjacentObject(world, new HasFruit(), EntityType.TREE, villager.getX(),
				villager.getY()) != null) {
			Tree tree = (Tree) FindObject.getAdjacentObject(world, new HasFruit(), EntityType.TREE, villager.getX(),
					villager.getY());
			villager.updateStatus("eating");
			tree.eaten();
			villager.satisfyHunger(0.1f);
			stacks++;
			if(stacks > stacksToEat){
				villager.updateStatus("statusEnd");
				actionFinished();
			}else if(!tree.hasFruit()){
				villager.updateStatus("statusEnd");
			}
				
		}else{
			villager.updateStatus("statusEnd");
			actionFailed();
		}
			

	}

 }