package main.java.com.brain.pulsar.universe;

import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.pulsar.files.format.Range;

@XmlRootElement(name = "system")
public class StarSystemType {
	
	@XmlElement(name = "spawn_odds")
	private int spawnOdds;
	
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
	
}
