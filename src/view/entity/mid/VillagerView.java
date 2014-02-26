package view.entity.mid;

import java.awt.Point;
import java.beans.PropertyChangeEvent;

import util.ImageLoader;
import view.entity.EntityView;

/**
 * The view representation of a villager.
 * 
 * @author Niklas
 */
public class VillagerView extends EntityView {

	/**
	 * Creates a new VillagerView.
	 * @param x - the world's x coordinate
	 * @param y - the world's y coordinate
	 */
	public VillagerView(int x, int y) {
		super("villager", x, y);
	}
	
	
	@Override
	/**
	 * Is called when our associated model changes in any way.
	 * 
	 * @author Tux
	 */
	public void propertyChange(PropertyChangeEvent event) {
		String name = event.getPropertyName();
		if(name.compareTo("status")==0){
			String status = (String)event.getNewValue();
			if(status.compareTo("sleeping")==0){
				setImage("villagersleeping");
			}
			if(status.compareTo("awake")==0){
				setImage("villager");
			}
		}else{
			super.propertyChange(event);
		}
	}
}
