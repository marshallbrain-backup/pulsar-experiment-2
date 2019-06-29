package com.brain.pulsar.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "modifier")
public class Modifier {

	@XmlElement
	private final double amount;
	
	@XmlElement
	private final String id;
	@XmlElement
	private final String parent;
	@XmlElement
	private final String target;
	
	public Modifier() {
		
		id = null;
		parent = null;
		target = null;
		amount = 0;
	}
	
	public Modifier(String id, String parent, String target, double amount) {
		
		this.id = id;
		this.parent = parent;
		this.target = target;
		this.amount = amount;
	}

	public Modifier(Modifier m, String newParent) {
		
		id = m.id;
		parent = newParent;
		target = m.target;
		amount = m.amount;
	}

	public String getParent() {
		
		return parent;
	}

	public double getValue() {
		return amount;
	}

	public String getTarget() {
		
		return target;
	}
	
}
