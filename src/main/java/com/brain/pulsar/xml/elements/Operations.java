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
	private List<Resource> upkeep;
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<Resource> production;
	
	public Operations() {
	}
	
	public Operations(Operations clone) {
		
		upkeep = new ArrayList<>(clone.upkeep);
		production = new ArrayList<>(clone.production);
		
	}

	public List<Resource> getUpkeep() {
		return new ArrayList<>(upkeep);
	}

	public List<Resource> getProduction() {
		return new ArrayList<>(production);
	}
	
}
