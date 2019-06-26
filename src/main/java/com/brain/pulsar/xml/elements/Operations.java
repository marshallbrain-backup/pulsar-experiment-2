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
	private List<ResourceBase> upkeep;
	@XmlElementWrapper
	@XmlElement(name = "resource")
	private List<ResourceBase> production;
	
	public Operations() {
	}
	
	public Operations(Operations clone) {
		
		upkeep = new ArrayList<>(clone.upkeep);
		production = new ArrayList<>(clone.production);
		
	}

	public List<ResourceBase> getUpkeep() {
		return new ArrayList<>(upkeep);
	}

	public List<ResourceBase> getProduction() {
		return new ArrayList<>(production);
	}
	
}
