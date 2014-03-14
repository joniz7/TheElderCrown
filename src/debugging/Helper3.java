package debugging;

import model.entity.top.TopEntity;

public class Helper3 extends TopEntity{

	public Helper3(int x, int y) {
		super(x * 20, y * 20, null);
		updatePos(x, y);
	}
	
	@Override
	public Helper3 copy() {
		return new Helper3(x, y);
	}
	
}
