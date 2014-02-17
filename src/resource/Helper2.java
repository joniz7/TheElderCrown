package resource;

import model.entity.TopLayerEntity;

public class Helper2 extends TopLayerEntity{

	public Helper2(int x, int y) {
		super("helper2", x * 20, y * 20, null);
		updatePos(x, y);
	}
	
	

}
