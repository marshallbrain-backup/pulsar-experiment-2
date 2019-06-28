package com.brain.pulsar.xml.elements;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlValue;

public class BuildTime {
	
	@XmlValue
	private final int baseBuildTime;
	
	private Map<Modifier, Double> modifierList;
	
	public BuildTime() {
		
		baseBuildTime = 0;
		
		modifierList = new HashMap<>();
	}
	
	public BuildTime(int time) {
		
		baseBuildTime = time;
		
		modifierList = new HashMap<>();
	}
	
	public BuildTime(BuildTime c) {
		
		baseBuildTime = c.baseBuildTime;
		modifierList = c.modifierList;
	}

	public int getTime() {
		
		double modifiedTime = baseBuildTime;
		
		for(double m: modifierList.values()) {
			modifiedTime += m;
		}
		
		return (int) Math.ceil(modifiedTime);
	}
	
}
