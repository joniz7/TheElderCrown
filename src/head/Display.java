package head;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Display {

	private GraphicsDevice gd;
	private GraphicsEnvironment env;
	private int displayWidth, displayHeight;
	
	private int drawCount;
	
	public Display(){
		env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = env.getDefaultScreenDevice();
	}

	public DisplayMode findFirstCompatibleMode(DisplayMode[] modes){
		DisplayMode[] goodModes = gd.getDisplayModes();
		for(int i = 0; i < modes.length; i++)
			for(int j = 0; j < goodModes.length; j++)
				if(displayModesMatch(modes[i], goodModes[j]))
					return modes[i];	
		return null;
	}
	
	public boolean displayModesMatch(DisplayMode dm1, DisplayMode dm2){
		if(dm1.getWidth() != dm2.getWidth() || dm1.getHeight() != dm2.getHeight())
			return false;
		
		if(dm1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && dm2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI 
				&& dm1.getBitDepth() != dm2.getBitDepth())
			return false;
		
		if(dm1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && dm2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
				&& dm1.getRefreshRate() != dm2.getRefreshRate())
			return false;
		
		return true;
	}
	
	public GraphicsDevice getGraphicsDevice() {
		return gd;
	}

	public GraphicsEnvironment getEnvironment() {
		return env;
	}

	public int getDisplayWidth() {
		return displayWidth;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}
	
}
