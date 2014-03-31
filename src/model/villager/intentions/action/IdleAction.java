package model.villager.intentions.action;

import util.Constants;
import util.EntityType;
import model.World;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFood;
import model.villager.Villager;
import model.villager.AgentWorld;

/**
 * Class for handling the actual "action" to perform when idle.
 * Currently only sets a new status and a timeout.
 * 
 * @author Tux
 *
 */
public class IdleAction extends Action {

	private int stacks, idleTime;
	private boolean firstTick = true;
	
	public IdleAction(Villager villager) {
		super(villager);
		name = "Idling";
		idleTime = 300;
	}

	@Override
	public void tick(ImpactableByAction world){
		if(firstTick) {
			villager.updateStatus("idling");
			this.firstTick = false;
		}
		villager.satisfyLazy(0.5f);
		if(villager.getLaziness() >= Constants.MAX_LAZINESS){
			villager.updateStatus("statusEnd");
			actionFinished();
		}
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
