package model.villager.util;

import util.RandomClass;

public class NameGen {

	private static String[] nameParts = {"le", "me", "ne", "pa", "ka", "pi", "he", "ha", "wa",
			"cha", "wo", "lo", "la", "ti", "ni", "wi", "mi", "si", "bi", "ki", "ri", "po",
			"ge", "ga", "no", "mo", "ho", "ha", "chi", "jo", "je", "ja", "ya", "yi", "ye",
			"li", "mu", "lu", "nu", "fe", "fa", "fo"};
	private static String[] femaleEnding = {"la", "ma", "fa", "na", "nea", "lea", "lia", "jia", "ja",
			"wa", "wia", "loa", "joa", "mea", "ka", "kia", "cha", "nih", "lih", "pih", "fih", "vih",
			"wih"};
	private static String[] maleEnding = {"loh", "moh", "foh", "noh", "neoh", "leoh", "lioh", "jioh", "joh",
			"woh", "wioh", "peoh", "hoh", "meoh", "koh", "kioh", "choh", "ner", "ler", "per", "fer", "ver",
			"wer"};
	
	
	public static String newName(boolean female){
		String nameBase = "";
		
		int length = RandomClass.getRandomInt(3, 1);
		
		for(int i = 0; i < length; i++)
			nameBase = nameBase + nameParts[RandomClass.getRandomInt(nameParts.length, 0)];
		
		if(female)
			nameBase = nameBase + femaleEnding[RandomClass.getRandomInt(femaleEnding.length, 0)];
		else
			nameBase = nameBase + maleEnding[RandomClass.getRandomInt(maleEnding.length, 0)];
		
		System.out.println(nameBase);
		
		return null;
	}
	
	public static void nameTest(){
		NameGen.newName(true);
		NameGen.newName(true);
		NameGen.newName(true);
		NameGen.newName(true);
		NameGen.newName(true);
		NameGen.newName(true);
		NameGen.newName(true);
		NameGen.newName(true);
		
		System.out.println("");
		System.out.println("");
		
		NameGen.newName(false);
		NameGen.newName(false);
		NameGen.newName(false);
		NameGen.newName(false);
		NameGen.newName(false);
		NameGen.newName(false);
		NameGen.newName(false);
		NameGen.newName(false);
		
		
		System.exit(0);
	}
	
}
