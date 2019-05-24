package main.java.com.brain.ion;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JFrame;

/**
 * The class that contains the main loop for a game.
 * 
 * @author Marshall Brain
 *
 */
public class GameLoop implements Runnable {
	
	private boolean running;
	
	private Map<SettingEntry, String> settings;
	private TickCall tickClass;
	private RenderCall renderClass;
	
	private JFrame mainFrame;
	private Thread mainThread;
	
	/**
	 * @param frame
	 *            The JFrame that the screen is added to
	 * @param settings
	 *            The settings map
	 * @param tickCall
	 *            The object to call the tick method from
	 * @param renderCall
	 *            The object to call the render method from
	 * @param screen
	 *            The canvas that the game is drawn in
	 */
	public GameLoop(JFrame frame, Map<SettingEntry, String> settings, TickCall tickCall, RenderCall renderCall, Canvas screen) {
		
		this.settings = settings;
		mainFrame = frame;
		tickClass = tickCall;
		renderClass = renderCall;
		
		int x = Integer.parseInt(settings.get(SettingEntry.WINDOWED_POS_X));
		int y = Integer.parseInt(settings.get(SettingEntry.WINDOWED_POS_Y));
		int w = Integer.parseInt(settings.get(SettingEntry.WINDOWED_WIDTH));
		int h = Integer.parseInt(settings.get(SettingEntry.WINDOWED_HEIGHT));
		
		screen.setPreferredSize(new Dimension(w, h));
		screen.setLocation(x, y);
		
		mainFrame.add(screen);
		
		screen.createBufferStrategy(2);
		
	}
	
	/**
	 * Starts the dedicated tick and rendering thread
	 */
	public synchronized void start() {
		
		if (running) {
			return;
		}
		
		running = true;
		
		// Creates a new thread for the loop so that the main thread is not stuck
		mainThread = new Thread(this);
		mainThread.start();
		
	}
	
	/**
	 * Stops the dedicated tick and rendering thread and closes the main frame
	 */
	public synchronized void stop() {
		
		// TODO have this method be called when the attempting to close the program
		// TODO find the better way of doing this
		if (!running) {
			mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
			return;
		}
		
		running = false;
		
		mainThread.interrupt();
		
		stop();
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		final long TARGET_FPS = 60;
		final long TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
		final long GAME_HERTZ = 60;
		final long TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		
		int tps = 0;
		int fps = 0;
		long lastSecond = System.nanoTime();
		long lastUpdateTime = lastSecond;
		long lastRenderTime;
		
		while (running) {
			
			long now = System.nanoTime();
			
			while (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				
				tickClass.tick();
				tps++;
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				
			}
			
			renderClass.render();
			fps++;
			lastRenderTime = now;
			
			// Is called once a second
			if ((lastUpdateTime / 1000000) - (lastSecond / 1000000) >= 1000) {
				
//				System.out.println("TPS - " + String.valueOf(tps));
//				System.out.println("FPS - " + String.valueOf(fps));
//				System.out.println();
				
				tps = 0;
				fps = 0;
				lastSecond = lastUpdateTime;
				
			}
			
			// Idles until a frame or game tick needs to happen
			while (now - lastRenderTime < TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				
				Thread.yield();
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				now = System.nanoTime();
				
			}
			
		}
		
	}
	
}
