package head;

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
    public static final int WIDTH   = 1024;
    public static final int HEIGHT  = 768;
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
}