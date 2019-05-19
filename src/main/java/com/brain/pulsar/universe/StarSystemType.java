package main.java.com.brain.pulsar.universe;

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

	public String getBody() {
		String b = body.trim();
		return b;
	}
	
}
