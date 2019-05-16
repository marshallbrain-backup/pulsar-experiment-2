package main.java.com.brain.ion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Settings {
	
	private Map<String, Map<String, String>> settingGroups;
	
	public Settings() {
		
		settingGroups = new HashMap<String, Map<String, String>>();
		
		FileReader in;
		
		Pattern header = Pattern.compile("^\\[\\w+\\]");
		Pattern name = Pattern.compile("^\\w=");
		Pattern value = Pattern.compile("^=\\w");
		Pattern none = Pattern.compile("^\\s+");
		
		try {
			
			File file = new File("settings.txt");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			in = new FileReader(file);
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
					settingGroups.put(m.group(), currentGroup);
				} else if((m = name.matcher(l)).find()) {
					currentEntry = m.group();
				} else if((m = value.matcher(l)).find()) {
					currentGroup.put(currentEntry, m.group());
				} else if((m = none.matcher(l)).find()) {
					lines.substring(m.end());
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
		
		String value = settingGroups.get(group).get(name);
		if(value == null && defaultValue != null) {
			put(key, defaultValue);
			value = defaultValue;
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
		
		return settingGroups.get(group).put(name, value);
		
	}
	
	public void push() {
		
	}
	
}
