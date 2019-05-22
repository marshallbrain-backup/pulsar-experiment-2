package main.java.com.brain.pulsar.files.format;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.pulsar.universe.BodyType;

@XmlRootElement(name = "range")
public class Range implements Cloneable {
	
	@XmlAttribute
	private String min;
	@XmlAttribute
	private String max;
	
	public Range() {
	}
	
	public Range(String min, String max) {
		this.min = min;
		this.max = max;
	}

	public String getMin() {
		return min;
	}
	
	public String getMax() {
		return max;
	}

	@Override
	public boolean equals(Object object) {
		
		Boolean equals = false;
		
		if(object instanceof Range) {
			
			Range compare = (Range) object;
			
			if(this == compare) {
				equals = true;
			} else {
				equals = true;
				if(!min.equals(compare.min)) {
					equals = false;
				}
				if(!max.equals(compare.max)) {
					equals = false;
				}
				
			}
			
		}
		
		return equals;
		
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
