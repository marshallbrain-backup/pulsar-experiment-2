package main.java.com.brain.pulsar.universe;

import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.pulsar.files.format.Range;

/**
 * Class for storing information about a system type from an xml file.
 * 
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "system")
public class StarSystemType {
	
	@XmlElement(name = "spawn_odds")
	private double spawnOdds;
	
	@XmlElement
	private String body;
	
	@XmlElement(name = "planets_num")
	private Range planetsNum;
	
	private Random r;
	
	public StarSystemType() {
		r = new Random();
	}

	/**
	 * @return The body at the core of the system
	 */
	public String getBody() {
		return body.trim();
	}

	/**
	 * @return A random number that is in the range of the possible number of
	 *         planets for the system
	 */
	public int getPlanetCount() {
		
		int min = Integer.parseInt(planetsNum.getMin());
		int max = Integer.parseInt(planetsNum.getMax());
		
		return r.nextInt(max - min) + min;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		
		Boolean equals = false;
		
		if(object instanceof StarSystemType) {
			
			StarSystemType compare = (StarSystemType) object;
			
			if(this == compare) {
				equals = true;
			} else {
				equals = true;
				if(spawnOdds != compare.spawnOdds) {
					equals = false;
				}
				if(!body.equals(compare.body)) {
					equals = false;
				}
				if(!planetsNum.equals(compare.planetsNum)) {
					equals = false;
				}
				
			}
			
		}
		
		return equals;
	}
	
}
