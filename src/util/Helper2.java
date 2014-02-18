package util;

import view.entity.EntityView;
import model.entity.TopEntity;

public class Helper2 extends TopEntity{

	public Helper2(int x, int y) {
		super(x * 20, y * 20, null);
		updatePos(x, y);
	}
	
	/**
	 * Creates and returns a new helper view.
	 * Registers the view as our listener.
	 */
	@Override
	public EntityView createView() {
		// TODO use "helper2" graphic
		throw new UnsupportedOperationException();
	}
	

}
