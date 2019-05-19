package main.java.com.brain.pulsar.universe;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "body")
public class BodyType {
	
	@XmlElement
	private String name;
	
}
