package model.villager.intentions.action;

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
	public void tick(AgentWorld world){
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

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
