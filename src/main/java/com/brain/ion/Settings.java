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

public class Settings {
	
	private Map<String, Map<String, String>> settingGroups;
	
	private File settingsFile;
	
	public Settings() {
		settingsFile = new File("settings.txt");
		init();
	}
	
	public Settings(boolean b) {
		settingsFile = new File("settings_test.txt");
		settingsFile.deleteOnExit();
		init();
	}
	
	private void init() {
		
		settingGroups = new HashMap<String, Map<String, String>>();
		
		FileReader in;
		
		Pattern header = Pattern.compile("(?:^\\[)(\\w+)(?:\\])");
		Pattern name = Pattern.compile("(^\\w+)(?:=)");
		Pattern value = Pattern.compile("(?:^=)(\\w+)");
		Pattern none = Pattern.compile("^\\s+");
		
		try {
			
			if (!settingsFile.exists()) {
				settingsFile.createNewFile();
			}
			
			in = new FileReader(settingsFile);
			BufferedReader br = new BufferedReader(in);
			
			int pos = 0;
			String currentEntry = "";
			String lines = br.lines().collect(Collectors.joining(System.lineSeparator()));
			
			Map<String, String> currentGroup = new HashMap<String, String>();
			settingGroups.put("", currentGroup);
			
			while (pos < lines.length()) {
				
				Matcher m;
				String l = lines.substring(pos);
				
				if((m = header.matcher(l)).find()) {
					currentGroup = new HashMap<String, String>();
					settingGroups.put(m.group(1), currentGroup);
					lines = l.substring(m.end());
					pos=0;
				} else if((m = name.matcher(l)).find()) {
					currentEntry = m.group(1);
					lines = l.substring(m.end(1));
					pos=0;
				} else if((m = value.matcher(l)).find()) {
					currentGroup.put(currentEntry, m.group(1));
					lines = l.substring(m.end());
					pos=0;
				} else if((m = none.matcher(l)).find()) {
					lines = l.substring(m.end());
					pos=0;
				} else {
					pos++;
				}
				
			}
			
			if(!lines.isEmpty()) {
				System.out.println(lines);
			}
			
			if(settingGroups.get("").isEmpty()) {
				settingGroups.remove("");
			}
			
			in.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	public String get(String key) {
		return get(key, null);
	}
	
	public String get(String key, String defaultValue) {

		String group = "";
		String name = key;
		if(key.contains(".")) {
			int i = key.indexOf(".");
			group = key.substring(0, i);
			name = key.substring(i+1);
		}
		
		String value = defaultValue;
		if((settingGroups.get(group) == null || settingGroups.get(group).get(name) == null) && defaultValue != null) {
			put(key, defaultValue);
		} else {
			value = settingGroups.get(group).get(name);
		}
		
		return value;
		
	}
	
	public String put(String key, String value) {
		
		String group = "";
		String name = key;
		if(key.contains(".")) {
			int i = key.indexOf(".");
			group = key.substring(0, i);
			name = key.substring(i+1);
		}
		
		if(!settingGroups.containsKey(group)) {
			settingGroups.put(group, new HashMap<String, String>());
		}
		
		String v = settingGroups.get(group).put(name, value);
		System.out.println("Added setting - " + key + "=" + value);
		return v;
		
	}
	
	public void push() {
		
		String file = "";
		
		for(Entry<String, Map<String, String>> g: settingGroups.entrySet()) {
			
			if(!g.getKey().isEmpty()) {
				file = file.concat("[" + g.getKey() + "]" + System.lineSeparator());
			}
			
			for(Entry<String, String> e: g.getValue().entrySet()) {
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
