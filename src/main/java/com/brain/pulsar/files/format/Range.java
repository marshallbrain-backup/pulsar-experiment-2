package main.java.com.brain.pulsar.files.format;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "range")
public class Range {
	
	@XmlAttribute
	private String max;
	@XmlAttribute
	private String min;
	@XmlAttribute
	private String units;
	
	/**
	 * 
	 */
	public Range() {
	}
	
	/**
	 * Clones a Range
	 * 
	 * @param clone The Range to clone
	 */
	public Range(Range clone) {
		
		if(clone == null) {
			return;
		}
		
		min = clone.min;
		max = clone.max;
		
	}
	
	/**
	 * @param min
	 *            The minimum value
	 * @param max
	 *            The max value
	 */
	public Range(String min, String max) {
		
		this.min = min;
		this.max = max;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		
		Boolean equals = false;
		
		if (object instanceof Range) {
			
			Range compare = (Range) object;
			
			if (this == compare) {
				equals = true;
			} else {
				equals = true;
				if (!min.equals(compare.min)) {
					equals = false;
				}
				if (!max.equals(compare.max)) {
					equals = false;
				}
				
			}
			
		}
		
		return equals;
		
	}
	
	/**
	 * Returns the max value
	 * 
	 * @return
	 */
	public String getMax() {
		
		return max;
	}
	
	/**
	 * @return Returns the minimum value
	 */
	public String getMin() {
		
		return min;
	}

	public String getUnits() {
		
		if(units == null) {
			return "METER";
		}
		
		return units;
		
	}
	
}
