package resource;

import model.entity.TopLayerEntity;

public class Helper extends TopLayerEntity{

	public Helper(int x, int y) {
		super("helper", x * 20, y * 20, null);
		updatePos(x, y);
	}
	
	

}
