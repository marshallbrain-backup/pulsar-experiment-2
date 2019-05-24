package main.java.com.brain.ion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Used to load and set settings.
 * 
 * @author Marshall Brain
 *
 */
public class Settings {
	
	private Map<String, Map<String, String>> settingGroups;
	
	private File settingsFile;
	
	/**
	 * Initializes a new instance of settings using the settings.txt file.
	 */
	public Settings() {
		
		settingsFile = new File("settings.txt");
		init();
	}
	
	/**
	 * Creates a temporary file to load and set settings to. Used primarily for
	 * testing purposes.
	 * 
	 * @param file
	 *            The path of the temporary file
	 */
	public Settings(String file) {
		
		settingsFile = new File(file);
		settingsFile.deleteOnExit();
		init();
	}
	
	/**
	 * Loads all settings from the file specified by the settingsFile variable into
	 * settingGroups
	 */
	private void init() {
		
		settingGroups = new HashMap<>();
		
		Pattern header = Pattern.compile("(?:^\\[)(\\w+)(?:\\])");
		Pattern name = Pattern.compile("(^\\w+)(?:=)");
		Pattern value = Pattern.compile("(?:^=)(\\w+)");
		Pattern none = Pattern.compile("^\\s+");
		
		if (!settingsFile.exists()) {
			
			try {
				settingsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(settingsFile))) {
			
			int pos = 0;
			String currentEntry = "";
			String lines = br.lines().collect(Collectors.joining(System.lineSeparator()));
			
			Map<String, String> currentGroup = new HashMap<>();
			settingGroups.put("", currentGroup);
			
			while (pos < lines.length()) {
				
				Matcher m;
				String l = lines.substring(pos);
				
				m = header.matcher(l);
				if (m.find()) {
					
					currentGroup = new HashMap<>();
					settingGroups.put(m.group(1), currentGroup);
					
					lines = l.substring(m.end());
					pos = 0;
					
					continue;
					
				}
				
				m = name.matcher(l);
				if (m.find()) {
					
					currentEntry = m.group(1);
					
					lines = l.substring(m.end(1));
					pos = 0;
					
					continue;
					
				}
				
				m = value.matcher(l);
				if (m.find()) {
					
					currentGroup.put(currentEntry, m.group(1));
					
					lines = l.substring(m.end());
					pos = 0;
					
					continue;
					
				}
				
				m = none.matcher(l);
				if (m.find()) {
					
					lines = l.substring(m.end());
					pos = 0;
					
					continue;
					
				}
				
				pos++;
				
			}
			
			if (!lines.isEmpty()) {
				System.out.println(lines);
			}
			
			if (settingGroups.get("").isEmpty()) {
				settingGroups.remove("");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the setting by the key. Setting groups are identified by the first "."
	 * in the key, all subsequence "." are ignored.
	 * <p>
	 * Ex - "test_group.test_key", test_group is the group the desired setting is in
	 * and test_key is the name of the setting.
	 * 
	 * @param key
	 *            The name of the setting
	 * @return The value of the setting
	 */
	public String get(String key) {
		
		return get(key, null);
	}
	
	/**
	 * Gets the setting by the key. Setting groups are identified by the first "."
	 * in the key, all subsequence "." are ignored. If the setting does not exist
	 * than it is created and set to the default value.
	 * <p>
	 * Ex - "test_group.test_key", test_group is the group the desired setting is in
	 * and test_key is the name of the setting.
	 * 
	 * @param key
	 *            The name of the setting
	 * @param defaultValue
	 *            The value the setting should be set to if it does not exist
	 * @return The value of the setting
	 */
	public String get(String key, String defaultValue) {
		
		String group = "";
		String name = key;
		if (key.contains(".")) {
			int i = key.indexOf(".");
			group = key.substring(0, i);
			name = key.substring(i + 1);
		}
		
		String value = defaultValue;
		if ((settingGroups.get(group) == null || settingGroups.get(group).get(name) == null) && defaultValue != null) {
			put(key, defaultValue);
		} else {
			value = settingGroups.get(group).get(name);
		}
		
		return value;
		
	}
	
	/**
	 * Overwrites the setting with a desired value. Setting groups are identified by
	 * the first "." in the key, all subsequence "." are ignored.
	 * <p>
	 * Ex - "test_group.test_key", test_group is the group the desired setting is in
	 * and test_key is the name of the setting.
	 * 
	 * @param key
	 *            The name of the setting
	 * @param value
	 *            The value that the setting should be set to
	 * @return The value of the setting
	 */
	public String put(String key, String value) {
		
		String group = "";
		String name = key;
		if (key.contains(".")) {
			int i = key.indexOf(".");
			group = key.substring(0, i);
			name = key.substring(i + 1);
		}
		
		if (!settingGroups.containsKey(group)) {
			settingGroups.put(group, new HashMap<String, String>());
		}
		
		String v = settingGroups.get(group).put(name, value);
		System.out.println("Added setting - " + key + "=" + value);
		return v;
		
	}
	
	/**
	 * Saves the current settings to a file.
	 */
	public void push() {
		
		String file = "";
		
		for (Entry<String, Map<String, String>> g : settingGroups.entrySet()) {
			
			if (!g.getKey().isEmpty()) {
				file = file.concat("[" + g.getKey() + "]" + System.lineSeparator());
			}
			
			for (Entry<String, String> e : g.getValue().entrySet()) {
				file = file.concat(e.getKey() + "=" + e.getValue() + System.lineSeparator());
			}
			
		}
		
		try (PrintWriter out = new PrintWriter(settingsFile)) {
			out.print(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
