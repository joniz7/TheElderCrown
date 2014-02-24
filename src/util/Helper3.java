package util;

import model.entity.top.TopEntity;
import view.entity.EntityView;

public class Helper3 extends TopEntity{

	public Helper3(int x, int y) {
		super(x * 20, y * 20, null, false);
		updatePos(x, y);
	}
	
	/**
	 * Creates and returns a new helper view.
	 * Registers the view as our listener.
	 */
	@Override
	public EntityView createView() {
		// TODO use "helper3" graphic
		throw new UnsupportedOperationException();
	}

}
