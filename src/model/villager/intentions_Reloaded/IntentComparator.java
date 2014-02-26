package model.villager.intentions_Reloaded;

import java.util.Comparator;

public class IntentComparator implements Comparator<Intent>{

	@Override
	public int compare(Intent i1, Intent i2) {
		int i1Total = i1.getCost() + i1.getDesire();
		int i2Total = i2.getCost() + i2.getDesire();
		
		return i1Total - i2Total;
	}

}
