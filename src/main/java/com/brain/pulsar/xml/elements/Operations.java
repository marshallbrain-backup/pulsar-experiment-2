package com.brain.pulsar.xml.elements;

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
	
}
