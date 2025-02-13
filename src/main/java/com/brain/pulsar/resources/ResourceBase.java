package com.brain.pulsar.resources;

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
	
	public ResourceBase(String id, double amount) {
		this.id = id;
		this.amount = amount;
	}

	public String getId() {
		
		return id;
	}

	public double getAmount() {
		
		return amount;
	}
	
}
