package main.java.com.brain.pulsar.universe;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import main.java.com.brain.pulsar.files.format.Range;

@XmlRootElement(name = "body")
public class BodyType {
	
	@XmlElement
	private String name;
	@XmlElement
	private String colonizable;
	
	@XmlElement
	private Range radius;
	@XmlElement(name = "temp_set")
	private Range tempSet;
	
	
	
}
