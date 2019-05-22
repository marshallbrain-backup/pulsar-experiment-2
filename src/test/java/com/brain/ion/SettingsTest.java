package test.java.com.brain.ion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.brain.ion.Settings;


class SettingsTest {
	
	private static Settings settings;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		settings = new Settings(true);
		
	}
	
	@BeforeEach
	void setUp() throws Exception {
	
	}
	
	@Test
	void settingsTest() {
		
		assertEquals("true", settings.get("test", "true"));
		assertEquals("true", settings.get("test"));
		
		assertEquals("true", settings.get("test.test", "true"));
		assertEquals("true", settings.get("test.test"));
		
		settings.push();
		
		settings = new Settings(true);
		assertEquals("true", settings.get("test"));
		assertEquals("true", settings.get("test.test"));
		
	}
	
}
