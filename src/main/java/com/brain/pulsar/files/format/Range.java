package main.java.com.brain.pulsar.files.format;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "range")
public class Range {
	
	@XmlAttribute(name = "min")
	private String min;
	@XmlAttribute(name = "max")
	private String max;
	
	public String getMin() {
		return min;
	}
	
	public String getMax() {
		return max;
	}
	
}
