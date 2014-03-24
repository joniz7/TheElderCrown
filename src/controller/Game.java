package controller;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	
	private static AppGameContainer app; 

    // Game state identifiers
    public static final int MAINMENU = 1;
    public static final int GAME     = 2;
    
    // Game state controllers
    private MenuController menuController;
    private WorldController worldController;
    
    // Application Properties
    public static int WIDTH   = 800;
    public static int HEIGHT  = 600;
    private static int nativeHeight, nativeWidth;
    
    public static final int FPS     = 160;
    public static final double VERSION = 0.01;
	private static String title = "The Elder Crown";
	private static final int TICK_INTERVAL = 5;
	
	// Whether a world has been created or not
	public boolean gameInitialized;

	/**
	 * Create and launch a new game.
	 */
	public Game() {
		super(title +" v"+ VERSION);		
	}
	

	@Override
    // Initialize the game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) throws SlickException {
		menuController = new MenuController();
		worldController = new WorldController();
        // (The first state added will be the one that is loaded first, when the application is launched)
		this.addState(menuController);
        this.addState(worldController);
    }
	
    // Method to quit the game completely
    public void exit(){
    	app.exit();
    }
    
    public int getNativeWidth(){
    	return nativeWidth;
    }
    
    public int getNativeHeight(){
    	return nativeHeight;
    }
    
	/**
	 * The entrypoint of the application
	 */
	public static void main(String[] args) {
        try {
            try {
                for(DisplayMode displayMode : Display.getAvailableDisplayModes()) {
                  if (displayMode.getBitsPerPixel() == 32 || displayMode.getBitsPerPixel() == 24
                      && (displayMode.getWidth() > Game.WIDTH
                          || displayMode.getHeight() > HEIGHT)) {
                	nativeWidth = displayMode.getWidth();
                	nativeHeight = displayMode.getHeight();
                    
                  }
                } 
              } catch(Throwable t) {
                throw new SlickException("Error finding native monitor resolution.", t);
              }

            app = new AppGameContainer(new Game());
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

	public MenuController getMenuController() {
		return menuController;
	}


	public WorldController getWorldController() {
		return worldController;
	}
    
	public boolean isGameInitialized() {
		return gameInitialized;
	}

	public void setGameInitialized(boolean gameInitialized) {
		this.gameInitialized = gameInitialized;
	}
	
}