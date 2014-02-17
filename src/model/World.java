package model;

import head.Tickable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public abstract class World implements Tickable{

	protected ArrayList<Tickable> tickables = new ArrayList<Tickable>();
	protected boolean paused;
	protected final PropertyChangeSupport pcs;
	
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
    
    public World() {
    	pcs = new PropertyChangeSupport(this);
    }
	
	@Override
	public void tick(){
		if(!paused) {
			for(Tickable t : tickables){
				t.tick();
			}
		}
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	/**
	 * Initializes the world.
	 */
	public abstract void initialize();
	
	
	

}
