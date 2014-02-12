package head;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import control.WorldController;
import view.View;
import model.GamePhase;
import model.TestWorld;
import model.villager.Villager;

public class GameAWT implements Runnable{

	private boolean running = true;
	private Frame gameFrame;
	
	private static GamePhase state;
	private View view;
	private DisplayMode activeDisplay;
	
	private Display disp;
	
	public static final double TARGET_TPS = 75.0;
	public static final double TARGET_FPS = 200.0;
	
	private static final DisplayMode[] preferedResolutions = {
		new DisplayMode(1920, 1080, 32, 0),
		new DisplayMode(1920, 1080, 24, 0),
		new DisplayMode(1920, 1080, 16, 0),
		
		new DisplayMode(1600, 900, 32, 0),
		new DisplayMode(1600, 900, 24, 0),
		new DisplayMode(1600, 900, 16, 0),
		
		new DisplayMode(1680, 1050, 32, 0),
		new DisplayMode(1680, 1050, 24, 0),
		new DisplayMode(1680, 1050, 16, 0),
		
		new DisplayMode(1280, 1024, 32, 0),
		new DisplayMode(1280, 1024, 24, 0),
		new DisplayMode(1280, 1024, 16, 0),
		
		new DisplayMode(1024, 768, 32, 0),
		new DisplayMode(1024, 768, 24, 0),
		new DisplayMode(1024, 768, 16, 0),
	};
	

	@Override
	public void run() {
		double previousTime = System.nanoTime();
		double currentTime = 0;
		double passedTime = 0;
		double timer = System.currentTimeMillis();
		double delta = 0;
//		double delta2 = 0;
		final double OPTIMAL_TPSTIME = 1000000000.0/TARGET_TPS;
		final double OPTIMAL_FPSTIME = 1000000000.0/TARGET_FPS;
		int fps = 0;
		int tps = 0;
		
		while(running){
			currentTime = System.nanoTime();
			passedTime = currentTime - previousTime;
			delta += passedTime / OPTIMAL_TPSTIME;
//			if(OptionsTrack.isFPSCap())
//				delta2 += passedTime / OPTIMAL_FPSTIME;
			previousTime = currentTime;
			
			if(delta >= 1){
//				getInput();
				state.tick();
				tps++;
				delta--;
			}
			
//			if(OptionsTrack.isFPSCap())
//				if(delta2 >= 1){
//					render();
//					fps++;
//					delta2--;
//				}
			
			render();
			fps++;
		
			if((System.currentTimeMillis() - timer) >= 1000){
				timer += 1000;
				//Display.getFrame().setTitle(TITLE + "  ||  " + tps + " tps, " + fps +  " fps");
//				System.out.println(tps + " tps, " + fps +  " fps");
				tps = 0;
				fps = 0;
			}
		}
	}
	
	private void init(){
		disp = new Display();
		
		Frame frame = new Frame(0, 0);
		
		disp.getGraphicsDevice().setFullScreenWindow(frame);
		
		DisplayMode dm = disp.findFirstCompatibleMode(preferedResolutions);
		
		DisplayMode newDisplayMode = dm;
		DisplayMode oldDisplayMode = disp.getGraphicsDevice().getDisplayMode();
		
		try {
			disp.getGraphicsDevice().setFullScreenWindow(frame);
			disp.getGraphicsDevice().setDisplayMode(newDisplayMode);
			activeDisplay = newDisplayMode;
		} finally {
			disp.getGraphicsDevice().setDisplayMode(oldDisplayMode);
			disp.getGraphicsDevice().setFullScreenWindow(frame);
			activeDisplay = oldDisplayMode;
		}
		
		view = new View(activeDisplay);
		state = new TestWorld();
		
		frame.addKeyListener(new WorldController(state));
		disp.getGraphicsDevice().getFullScreenWindow().addKeyListener(new WorldController(state));
	}
	
	public static void render(){
		BufferStrategy bs = Frame.getCanvas().getBufferStrategy();
		if(bs == null){
			Frame.getCanvas().createBufferStrategy(3);
			Frame.getCanvas().requestFocus();
			return;
		}
		
		Graphics2D g = null;
		do{
			try{
				g = (Graphics2D) bs.getDrawGraphics();
				
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, Frame.getCanvas().getWidth(), Frame.getCanvas().getHeight());
				
				// Sets preferences for rendering / Sets rendering rules
				// KEY_ANTIALIASING reduces artifacts on shapes
				// VALUE_ANTIALIAS_ON will clean up the edges
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
				
				View.render(g, state.getViewX(), state.getViewY());
			}finally{g.dispose();}
		}while(bs.contentsLost());
		bs.show();
		Toolkit.getDefaultToolkit().sync();
	}
}
