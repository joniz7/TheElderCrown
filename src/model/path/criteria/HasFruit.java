package model.path.criteria;

import model.entity.GraphicalEntity;
import model.objects.Tree;

public class HasFruit implements Criteria{

	@Override
	public boolean match(GraphicalEntity ge){
		Tree tree = (Tree) ge;
		
		return tree.hasFruit();
	}

}
