package model.path.criteria;

import model.entity.GraphicalEntity;
import model.objects.Tree;

public class HasFruit implements Criteria{

	@Override
	public boolean match(GraphicalEntity ge){
		Tree tree = (Tree) ge;
		
<<<<<<< HEAD
		if(tree.hasFruit())
			return true;
		else
			return false;	
=======
		return tree.hasFruit();
>>>>>>> 38cd94345c41f3e04e83ce9593c6bde859705bce
	}

}
