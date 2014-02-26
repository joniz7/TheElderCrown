package model.villager.intentions_Reloaded;

import util.EntityType;
import model.World;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFruit;
import model.villager.Villager;

public class EatAction extends Action{

	private int stacks, stacksToEat;
	
	public EatAction(Villager villager) {
		super(villager);
		stacksToEat = 500;
	}

	@Override
	public void tick(World world){
		if(FindObject.getAdjacentObject(world, new HasFruit(), EntityType.TREE, villager.getX(),
				villager.getY()) != null) {
			Tree tree = (Tree) FindObject.getAdjacentObject(world, new HasFruit(), EntityType.TREE, villager.getX(),
					villager.getY());
			tree.eaten();
			villager.satisfyHunger(0.1f);
			stacks++;
			System.out.println("EatAction: stacks " + stacks);
			if(stacks > stacksToEat)
				actionFinished();
		}else
			actionFailed();

	}

 }