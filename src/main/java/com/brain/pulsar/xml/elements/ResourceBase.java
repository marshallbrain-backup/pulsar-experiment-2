package com.brain.pulsar.xml.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class ResourceBase {
	
	@XmlValue
	private final double amount;
	
	@XmlAttribute
	private final String id;
	
	public ResourceBase() {
		id = null;
		amount = 0;
	}

	public String getId() {
		
		return id;
	}

	public double getAmount() {
		
		return amount;
	}
	
}
