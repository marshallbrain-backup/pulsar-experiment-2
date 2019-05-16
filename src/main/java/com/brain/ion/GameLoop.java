package main.java.com.brain.ion;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JFrame;

public class GameLoop implements Runnable {
	
	private boolean running;
	
	private Map<SettingEntry, String> settings;
	private JFrame mainFrame;
	private Thread mainThread;

	public GameLoop(JFrame f, Map<SettingEntry, String> s) {
		
		settings = s;
		mainFrame = f;
		
		int x = Integer.parseInt(settings.get(SettingEntry.WINDOWED_POS_X));
		int y = Integer.parseInt(settings.get(SettingEntry.WINDOWED_POS_Y));
		int w = Integer.parseInt(settings.get(SettingEntry.WINDOWED_WIDTH));
		int h = Integer.parseInt(settings.get(SettingEntry.WINDOWED_HEIGHT));
		
		Canvas screen = new Canvas();
		screen.setPreferredSize(new Dimension(w, h));
		screen.setLocation(x, y);
		
		mainFrame.add(screen);
		
		screen.createBufferStrategy(2);
		
	}

	public synchronized void start() {
		
		if(running) {
			return;
		}
		
		running = true;
		
		mainThread = new Thread(this);
		mainThread.start();
		
	}
	
	public synchronized void stop(){
		
		//TODO have this method be called when the attempting to close the program
		//TODO find the better way of doing this
		if(!running) {
			mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
			return;
		}
		
		running = false;
		
		mainThread.interrupt();
		
		stop();
		
	}
	
	public void run() {
		
		
		
	}
	
}
