package head;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StatedGame extends StateBasedGame {
	
	
	private static AppGameContainer app; 

    // Game state identifiers
    public static final int MAINMENU     = 1;
    public static final int GAME         = 2;

    // Application Properties
    public static int WIDTH   = 800;
    public static int HEIGHT  = 600;
    private static int nativeHeight, nativeWidth;
    
    public static final int FPS     = 60;
    public static final double VERSION = 0.01;
	private static String title = "The Elder Crown";
	private static final int TICK_INTERVAL = 5;
 
	
    
	// Class Constructor
	public StatedGame() {
		super(title +" v"+ VERSION);
	}
	


    // Initialize the game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) throws SlickException {
        // The first state added will be the one that is loaded first, when the application is launched
        this.addState(new MainMenu());
        this.addState(new MainGameState());
    }
    
    // Method to quit the game completely
    public void exit(){
    	app.exit();
    }

    // Main Method
    public static void main(String[] args) {
        try {
        	
            try {
                for(DisplayMode displayMode : Display.getAvailableDisplayModes()) {
                  if (displayMode.getBitsPerPixel() == 32 || displayMode.getBitsPerPixel() == 24
                      && (displayMode.getWidth() > WIDTH
                          || displayMode.getHeight() > HEIGHT)) {
                	nativeWidth = displayMode.getWidth();
                	nativeHeight = displayMode.getHeight();
                    
                  }
                } 
              } catch(Throwable t) {
                throw new SlickException("Error finding native monitor resolution.", t);
              }
            
            app = new AppGameContainer(new StatedGame());
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.start();
            app.setFullscreen(true);
			app.setMaximumLogicUpdateInterval(TICK_INTERVAL);
			app.setMinimumLogicUpdateInterval(TICK_INTERVAL);
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }
    
    public int getNativeWidth(){
    	return nativeWidth;
    }
    
    public int getNativeHeight(){
    	return nativeHeight;
    }
    
}