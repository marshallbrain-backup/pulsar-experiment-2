package main.java.com.brain.ion;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ion {
	
	private Map<SettingEntry, String> settings;
	
	private JFrame mainFrame;
	
	public Ion() {
		
		init();
		
	}
	
	private void init() {
		
		settings = new EnumMap<SettingEntry, String>(SettingEntry.class);
		mainFrame = new JFrame();
		
		initSettings();
		
		mainFrame.setTitle("Pulsar");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		GameLoop gameLoop = new GameLoop(mainFrame, settings);
		
		mainFrame.pack();
		
		gameLoop.start();
		
//		c.createBufferStrategy(2);
//		BufferStrategy bs = c.getBufferStrategy();
//		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
//		
//		g.dispose();
//		bs.show();
		
//		do {
//		    try{
//		        g2 = (Graphics2D) bs.getDrawGraphics();
//		        drawWhatEver(g2);
//		    } finally {
//		           g2.dispose();
//		    }
//		    bs.show();
//		} while (bs.contentsLost());
		
	}
	
	private void initSettings() {
		
		Settings s = new Settings();
		
		settings.put(SettingEntry.WINDOWED_POS_X, s.get("WindowedPosX", "0"));
		settings.put(SettingEntry.WINDOWED_POS_Y, s.get("WindowedPosY", "0"));
		settings.put(SettingEntry.WINDOWED_WIDTH, s.get("WindowedWidth", "1920"));
		settings.put(SettingEntry.WINDOWED_HEIGHT, s.get("WindowedHeight", "1080"));
		
	}
	
}
