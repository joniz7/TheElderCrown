package model.villager.intentions_Reloaded;

import java.util.Comparator;

public class IntentComparator implements Comparator<Intent>{

	@Override
	public int compare(Intent i1, Intent i2) {
		float i1Total = i1.getCost() + i1.getDesire();
		float i2Total = i2.getCost() + i2.getDesire();
		
		if(i1Total < i2Total)
			return 1;
		else if(i1Total == i2Total)
			return 0;
		else
			return -1;
		
	}

}
