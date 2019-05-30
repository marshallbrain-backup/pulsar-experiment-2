package com.brain.ion.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SettingsTest {
	
	@Test
	void settingsTest() {
		
		Settings settings = new Settings("settings_test.txt");
		
		assertEquals("true", settings.get("test", "true"));
		assertEquals("true", settings.get("test"));
		
		assertEquals("true", settings.get("test.test", "true"));
		assertEquals("true", settings.get("test.test"));
		
		settings.push();
		
		settings = new Settings("settings_test.txt");
		assertEquals("true", settings.get("test"));
		assertEquals("true", settings.get("test.test"));
		
	}
	
}
