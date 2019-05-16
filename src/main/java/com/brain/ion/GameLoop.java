package main.java.com.brain.ion;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;

public class GameLoop {
	
	private Map<SettingEntry, String> settings;
	private JFrame mainFrame;

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
		
	}
	
}
