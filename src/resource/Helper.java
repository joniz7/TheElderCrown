package resource;

import model.entity.TopLayerGraphicalEntity;

public class Helper extends TopLayerGraphicalEntity{

	public Helper(int x, int y) {
		super("helper", x * 20, y * 20, null);
		updatePos(x, y);
	}
	
	

}
