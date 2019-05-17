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
		
		int tps;
		int fps;
		
		long lastSecond;
		long lastUpdateTime;
		long lastRenderTime;
		final long TARGET_FPS = 60;
		final long TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
		final long GAME_HERTZ = 60;
		final long TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		
		tps = 0;
		fps = 0;
		lastSecond = System.nanoTime();
		lastUpdateTime = System.nanoTime();
		lastRenderTime = System.nanoTime();
		
		while(running) {
			
			long now = System.nanoTime();
			
			while(now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				tps++;
				lastUpdateTime += TIME_BETWEEN_UPDATES;
			}
			
			fps++;
			lastRenderTime = now;
			
			if((lastUpdateTime / 1000000) - (lastSecond / 1000000) >= 1000) {
				
				System.out.println("TPS - " + String.valueOf(tps));
				System.out.println("FPS - " + String.valueOf(fps));
				System.out.println();
				
				tps = 0;
				fps = 0;
				lastSecond = lastUpdateTime;
				
			}
			
			while(now - lastRenderTime < TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				
				Thread.yield();
				try {
					Thread.sleep(1);
				} catch(Exception e) {
				}
				
				now = System.nanoTime();
               
			}
			
//			if(now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
//				
//				tps++;
//				lastUpdateTime = now;
//				
//			}
//			
//			now = System.nanoTime();
//			
//			if(now - lastRenderTime > TIME_BETWEEN_RENDERS) {
//				
//				fps++;
//				lastRenderTime = now;
//				
//			}
//			
//			now = System.nanoTime();
//			
//			if(now - lastTime > 1000000000) {
//				
//				lastTime = now;
//				System.out.println("TPS - " + String.valueOf(tps));
//				System.out.println("FPS - " + String.valueOf(fps));
//				System.out.println();
//				
//				tps = 0;
//				fps = 0;
//				
//			}
			
		}
		
	}
	
}
