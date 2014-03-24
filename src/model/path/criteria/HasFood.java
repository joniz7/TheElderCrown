package model.path.criteria;

import model.entity.Entity;
import model.entity.top.Tree;

public class HasFood implements Criteria{

	@Override
	public boolean match(Entity ge){
		Tree tree = (Tree) ge;

		if(tree.hasFruit())
			return true;
		else
			return false;	
	}

}
