package head;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Frame extends JFrame{

	private static Canvas canvas;
	
	public Frame(int width, int height){
		canvas = new Canvas();
		
		Dimension canvasSize = new Dimension(width, height);
		canvas.setPreferredSize(canvasSize);
		canvas.setMinimumSize(canvasSize);
		canvas.setMaximumSize(canvasSize);
		canvas.setIgnoreRepaint(true);
		
		setUndecorated(true);
		
		add(canvas);
		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIgnoreRepaint(true);
		
		
	}

	public static Canvas getCanvas() {
		return canvas;
	}
	
	public void update(){
		BufferStrategy bs = getBufferStrategy();
		if(!bs.contentsLost())
			bs.show();
		Toolkit.getDefaultToolkit().sync();
	}
}
