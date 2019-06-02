package com.brain.pulsar.xml.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Operations {
	
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<Resource> cost;
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<Resource> upkeep;
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<Resource> production;
	
	public Operations() {
	}
	
	public Operations(Operations clone) {
		
		cost = new ArrayList<>(clone.cost);
		upkeep = new ArrayList<>(clone.upkeep);
		production = new ArrayList<>(clone.production);
		
	}

	public static List<Operations> toList(Operations... ops) {
		
		return toList(ops, new Operations[0]);
	}
	
	public static List<Operations> toList(Operations[]... opsArray) {
		
		List<Operations> ops = new ArrayList<>();
		
		for(Operations[] o: opsArray) {
			ops.addAll(Arrays.asList(o));
		}
		
		return ops;
	}

	public List<Resource> getUpkeep() {
		
		List<Resource> newUpkeep = new ArrayList<>();
		for(Resource r: upkeep) {
			newUpkeep.add(new Resource(-r.getAmount(), r.getType()));
		}
		
		return newUpkeep;
	}

	public List<Resource> getProduction() {
		
		List<Resource> newProduction = new ArrayList<>();
		for(Resource r: production) {
			newProduction.add(new Resource(r.getAmount(), r.getType()));
		}
		
		return newProduction;
	}
	
}
