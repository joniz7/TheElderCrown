package debugging;

import model.entity.top.TopEntity;

public class Helper1 extends TopEntity{

	public Helper1(int x, int y) {
		super(x * 20, y * 20, null);
		updatePos(x, y);
	}

}
