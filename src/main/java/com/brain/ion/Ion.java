package main.java.com.brain.ion;

import java.awt.Canvas;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JFrame;

/**
 * The primary class for the Ion game engine.
 * 
 * @author Marshall Brain
 *
 */
public class Ion {
	
	private Map<SettingEntry, String> settings;
	
	private JFrame mainFrame;
	private GameLoop gameLoop;
	
	/**
	 * Initialization of the Ion game engine
	 * 
	 * @param tickCall
	 *            The object to call the tick method from
	 * @param renderCall
	 *            The object to call the render method from
	 * @param screen
	 *            The canvas that the game is drawn in
	 */
	public Ion(TickCall tickCall, RenderCall renderCall, Canvas screen) {
		
		init(tickCall, renderCall, screen);
		
	}
	
	/**
	 * @param tickCall
	 *            The object to call the tick method from
	 * @param renderCall
	 *            The object to call the render method from
	 * @param screen
	 *            The canvas that the game is drawn in
	 */
	private void init(TickCall tickCall, RenderCall renderCall, Canvas screen) {
		
		settings = new EnumMap<>(SettingEntry.class);
		mainFrame = new JFrame();
		
		initSettings();
		
		mainFrame.setTitle("Pulsar");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		gameLoop = new GameLoop(mainFrame, settings, tickCall, renderCall, screen);
		
		mainFrame.pack();
		
	}
	
	/**
	 * Populates the settings map with all of the game settings
	 */
	private void initSettings() {
		
		Settings s = new Settings();
		
		settings.put(SettingEntry.WINDOWED_POS_X, s.get("WindowedPosX", "0"));
		settings.put(SettingEntry.WINDOWED_POS_Y, s.get("WindowedPosY", "0"));
		settings.put(SettingEntry.WINDOWED_WIDTH, s.get("WindowedWidth", "1920"));
		settings.put(SettingEntry.WINDOWED_HEIGHT, s.get("WindowedHeight", "1080"));
		
	}
	
	/**
	 * Starts the main game loop
	 */
	public void start() {
		
		gameLoop.start();
		
	}
	
}
