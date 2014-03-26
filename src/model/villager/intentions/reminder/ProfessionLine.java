package model.villager.intentions.reminder;

import model.villager.Villager;
import model.villager.intentions.routine.FreeTimeRoutine;
import model.villager.intentions.routine.Routine;

public abstract class ProfessionLine extends ReminderLine{

	public enum WorkLevel{HARD, MEDIUM, EASY};
	
	public ProfessionLine(Villager villager, Routine routine, WorkLevel level) {
		super(villager);
		FreeTimeRoutine spareTime = new FreeTimeRoutine();
		
		if(level == WorkLevel.HARD){
			routines.put(8, routine);
			routines.put(12, spareTime);
			routines.put(13, routine);
			routines.put(17, spareTime);
		}else if(level == WorkLevel.MEDIUM){
			routines.put(8, routine);
			routines.put(11, spareTime);
			routines.put(12, routine);
			routines.put(15, spareTime);
		}else if(level == WorkLevel.EASY){
			routines.put(8, routine);
			routines.put(10, spareTime);
			routines.put(11, routine);
			routines.put(13, spareTime);
		}
	}

	
	
}
