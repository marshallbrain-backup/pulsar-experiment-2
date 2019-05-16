package main.java.com.brain.ion;

import java.awt.Frame;

public class Ion {
	
	private Frame mainFrame;
	private Settings settings;
	
	public Ion() {
		
		init();
		
	}
	
	private void init() {
		
		settings = new Settings();
		
		initSettings();
		
	}
	
	private void initSettings() {
		
		String winX = settings.get("WindowedPosX", "0");
		String winY = settings.get("WindowedPosY", "0");
		String width = settings.get("WindowedWidth", "1920");
		String height = settings.get("WindowedHeight", "1080");
		
	}
	
}
