package debugging;

import model.entity.top.TopEntity;

public class Helper2 extends TopEntity{

	public Helper2(int x, int y) {
		super(x * 20, y * 20, null);
		updatePos(x, y);
	}
}
