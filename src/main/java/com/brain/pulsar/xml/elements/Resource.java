package com.brain.pulsar.xml.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Resource {
	
	@XmlValue
	private double amount;

	@XmlAttribute
	private String type;
	
}
