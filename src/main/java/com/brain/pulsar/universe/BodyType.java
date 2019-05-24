package main.java.com.brain.pulsar.universe;

import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.pulsar.files.format.Range;

/**
 * Class for storing information about a body type from an xml file.
 * 
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "body")
public class BodyType implements Cloneable {
	
	@XmlElement(name = "spawn_odds")
	private double spawnOdds;
	@XmlElement(defaultValue = "true")
	private boolean suitable;
	
	@XmlElement(defaultValue = "true")
	private boolean colonizable;
	
	@XmlElement
	private String climate;
	@XmlElement
	private String name;
	
	@XmlElement
	private Range radius;
	@XmlElement(name = "moon_size")
	private Range moonSize;
	@XmlElement(name = "temp_range")
	private Range tempRange;
	@XmlElement(name = "temp_set")
	private Range tempSet;
	
	
	private Random random;
	
	public BodyType() {
		
		random = new Random();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public BodyType clone() {
		
		BodyType clone = null;
		try {
			
			clone = (BodyType) super.clone();
			
			clone.name = name;
			clone.colonizable = colonizable;
			clone.radius = radius.clone();
			clone.tempSet = tempSet.clone();
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return clone;
		
	}
	
	/**
	 * Converts a number as a string with scientific notation to a double
	 * 
	 * @param number
	 *            The number to convert
	 * @return The converted double
	 */
	private double convert(String n) {
		
		String[] s = n.split("e");
		
		if (s.length == 1)
			return Double.parseDouble(n);
		
		long e = Long.parseLong(s[1]);
		String i = s[0];
		
		double d = Double.parseDouble(i);
		
		return d * Math.pow(10, e);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		
		Boolean equals = false;
		
		if (object instanceof BodyType) {
			
			BodyType compare = (BodyType) object;
			
			if (this == compare) {
				equals = true;
			} else {
				equals = true;
				if (spawnOdds != compare.spawnOdds) {
					equals = false;
				}
				if (suitable != compare.suitable) {
					equals = false;
				}
				if (colonizable != compare.colonizable) {
					equals = false;
				}
				if (!name.equals(compare.name)) {
					equals = false;
				}
				if (!climate.equals(compare.climate)) {
					equals = false;
				}
				if (!radius.equals(compare.radius)) {
					equals = false;
				}
				if (!tempSet.equals(compare.tempSet)) {
					equals = false;
				}
				if (!tempRange.equals(compare.tempRange)) {
					equals = false;
				}
				if (!moonSize.equals(compare.moonSize)) {
					equals = false;
				}
				
			}
			
		}
		
		return equals;
	}
	
	/**
	 * @return The name of the type of planet
	 */
	public String getName() {
		
		return name;
	}
	
	/**
	 * @return A random radius that is within the set range
	 */
	public long getRandomRadius() {
		
		double min = convert(radius.getMin());
		double max = convert(radius.getMax());
		
		double r = ((max - min) * random.nextDouble()) + min;
		
		return Math.round(r);
		
	}
	
	/**
	 * @return A random temperature that is within the set range
	 */
	public long getRandomTemp() {
		
		double min = convert(tempSet.getMin());
		double max = convert(tempSet.getMax());
		
		double t = ((max - min) * random.nextDouble()) + min;
		
		return Math.round(t);
		
	}
	
	/**
	 * Test whether the given temperature is within the set range
	 * 
	 * @param Temperature
	 *            The temperature value to test
	 * @return The results
	 */
	public boolean inTemperatureRange(long temperature) {
		
		if (tempRange == null) {
			return true;
		}
		
		double min = convert(tempRange.getMin());
		double max = convert(tempRange.getMax());
		
		if (min < temperature && temperature < max) {
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * @return Whether the planet is suitable for generation based on temperature of a body
	 */
	public boolean isSuitable() {
		
		return suitable;
	}
	
}
