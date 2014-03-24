package model.villager.intentions.reminder;

import java.util.HashMap;

import util.Constants;
import model.villager.Villager;
import model.villager.intentions.routine.FreeTimeRoutine;
import model.villager.intentions.routine.Routine;

/**
 * Reminder time-lines is a system for giving villagers a sense of
 * routine in their thinking. They affect villagers during the time
 * period specified in different ways.
 * 
 * @author Simon E
 */
public abstract class ReminderLine {

	protected HashMap<Integer, Routine> routines = new HashMap<Integer, Routine>();
	protected Villager villager;
	protected Routine activeRoutine;
	
	public ReminderLine(Villager villager){
		this.villager = villager;
	}
	
	public void update(int time){
		int hours = time / Constants.TICKS_HOUR;
		if(routines.containsKey(hours))
			activeRoutine = routines.get(hours);
		if(activeRoutine != null)
			activeRoutine.imposeRoutine(villager);
	}
	
	
	
}
