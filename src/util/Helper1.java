package util;

import view.entity.EntityView;
import model.entity.top.TopEntity;

public class Helper1 extends TopEntity{

	public Helper1(int x, int y) {
		super(x * 20, y * 20, null);
		updatePos(x, y);
	}
	
	/**
	 * Creates and returns a new helper view.
	 * Registers the view as our listener.
	 */
	@Override
	public EntityView createView() {
		// TODO use "helper" graphic 
		throw new UnsupportedOperationException();
	}
	

}
