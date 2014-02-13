package model.infrastructure;

import model.entity.TopLayerGraphicalEntity;

public class House extends TopLayerGraphicalEntity {
	
	public static final int UP_ENTRANCE = 0, RIGHT_ENTRANCE = 1, DOWN_ENTRANCE = 2, LEFT_ENTRANCE = 3; 
	private int orientation;

	public House(String name, int x, int y, int orientation) {
		super(name, x, y);
		this.orientation = orientation;
		updatePos(x-1, y-1);
	}
	
}
