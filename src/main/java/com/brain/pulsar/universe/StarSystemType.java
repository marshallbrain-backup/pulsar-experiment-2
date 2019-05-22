package main.java.com.brain.pulsar.universe;

import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.pulsar.files.format.Range;

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

	public String getBody() {
		String b = body.trim();
		return b;
	}

	public int getPlanetCount() {
		
		int min = Integer.parseInt(planetsNum.getMin());
		int max = Integer.parseInt(planetsNum.getMax());
		
		return r.nextInt(max - min) + min;
	}

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
