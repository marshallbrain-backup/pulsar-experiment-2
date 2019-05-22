package main.java.com.brain.pulsar.universe;

import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.pulsar.files.format.Range;

@XmlRootElement(name = "body")
public class BodyType implements Cloneable {
	
	@XmlElement(name = "spawn_odds")
	private double spawnOdds;

	@XmlElement(defaultValue = "true")
	private boolean suitable;
	@XmlElement(defaultValue = "true")
	private boolean colonizable;

	@XmlElement
	private String name;
	@XmlElement
	private String climate;
	
	@XmlElement
	private Range radius;
	@XmlElement(name = "temp_set")
	private Range tempSet;
	@XmlElement(name = "temp_range")
	private Range tempRange;
	@XmlElement(name = "moon_size")
	private Range moonSize;
	
	private Random random;
	
	public BodyType() {
		random = new Random();
	}
	
	public String getName() {
		return name;
	}

	public boolean isSuitable () {
		return suitable;
	}
	
	public long getRandomTemp() {
		
		double min = convert(tempSet.getMin());
		double max = convert(tempSet.getMax());
		
		double t = ((max-min)*random.nextDouble())+min;
		
		return Math.round(t);
		
	}
	
	public long getRandomRadius() {
		
		double min = convert(radius.getMin());
		double max = convert(radius.getMax());
		
		double r = ((max-min)*random.nextDouble())+min;
		
		return Math.round(r);
		
	}

	public boolean inTemperatureRange(long temperature) {
		
		if(tempRange == null) {
			return true;
		}
		
		double min = convert(tempRange.getMin());
		double max = convert(tempRange.getMax());
		
		if(min < temperature && temperature < max) {
			return true;
		}
		
		return false;
		
	}
	
	private double convert(String n) {
		
		String[] s = n.split("e");
		
		if(s.length == 1)
			return Double.parseDouble(n);
		
		long e = Long.parseLong(s[1]);
		String i = s[0];
		
		double d = Double.parseDouble(i);
		
		return d * Math.pow(10, e);
		
	}

	@Override
	public boolean equals(Object object) {
		
		Boolean equals = false;
		
		if(object instanceof BodyType) {
			
			BodyType compare = (BodyType) object;
			
			if(this == compare) {
				equals = true;
			} else {
				equals = true;
				if(spawnOdds != compare.spawnOdds) {
					equals = false;
				}
				if(suitable != compare.suitable) {
					equals = false;
				}
				if(colonizable != compare.colonizable) {
					equals = false;
				}
				if(!name.equals(compare.name)) {
					equals = false;
				}
				if(!climate.equals(compare.climate)) {
					equals = false;
				}
				if(!radius.equals(compare.radius)) {
					equals = false;
				}
				if(!tempSet.equals(compare.tempSet)) {
					equals = false;
				}
				if(!tempRange.equals(compare.tempRange)) {
					equals = false;
				}
				if(!moonSize.equals(compare.moonSize)) {
					equals = false;
				}
				
			}
			
		}
		
		return equals;
	}
	
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
	
}
