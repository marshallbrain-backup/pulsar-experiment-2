package com.brain.pulsar.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brain.pulsar.xml.elements.Modifier;

public class PopCollection {
	
	private double baseGrouth;
	private double maxGrouth;
	private double currentGrouth;
	
	private List<Pop> popList;
	
	private Map<Modifier, Double> grouthModifierList;
	
	public PopCollection() {
		
		baseGrouth = 3;
		maxGrouth = 99;
		
		grouthModifierList = new HashMap<>();
		popList = new ArrayList<>();
		
	}
	
	public void tick(TimeEntry time) {
		
		double addGrouth = baseGrouth;
		
		for(double d: grouthModifierList.values()) {
			addGrouth += d;
		}
		
		currentGrouth += addGrouth * time.getTime();
		
		while(currentGrouth >= maxGrouth) {
			popList.add(new Pop());
			currentGrouth -= maxGrouth;
		}
		
	}
	
	public List<Pop> getPopList() {
		return popList;
	}
	
	public void addModifier(Modifier m) {
		
	}
	
}
