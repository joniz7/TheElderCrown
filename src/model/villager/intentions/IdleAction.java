package model.villager.intentions;

import util.EntityType;
import model.World;
import model.entity.top.Tree;
import model.path.FindObject;
import model.path.criteria.HasFruit;
import model.villager.Villager;
import model.villager.VillagersWorldPerception;

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
	public void tick(VillagersWorldPerception world){
		if(firstTick) {
			System.out.println("Idling");
			villager.updateStatus("idling");
			firstTick = false;
		}
		stacks++;
		if(stacks > idleTime){
			villager.updateStatus("statusEnd");
			actionFinished();
		}
	}
}
