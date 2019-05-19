package main.java.com.brain.pulsar.files.format;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "range")
public class Range {
	
	@XmlAttribute(name = "min")
	private String min;
	@XmlAttribute(name = "max")
	private String max;
	
//	public double getMin() {
//		return min;
//	}
//	
//	public double getMax() {
//		return max;
//	}
	
}
