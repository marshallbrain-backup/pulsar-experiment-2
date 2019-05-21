package main.java.com.brain.pulsar.files.format;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.pulsar.universe.BodyType;

@XmlRootElement(name = "range")
public class Range implements Cloneable {
	
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
	
	@Override
	public Range clone() {
		
		Range clone = null;
		try {
			
			clone = (Range) super.clone();
			
			clone.min = min;
			clone.max = max;
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return clone;
		
	}
	
}
