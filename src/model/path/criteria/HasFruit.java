package model.path.criteria;

import model.entity.GraphicalEntity;
import model.entity.Tree;

public class HasFruit implements Criteria{

	@Override
	public boolean match(GraphicalEntity ge){
		Tree tree = (Tree) ge;
		
		if(tree.isFruit())
			return true;
		else
			return false;	
	}

}
