package main.java.com.brain.pulsar.universe;

import main.java.com.brain.pulsar.data.Distance;

public class Body {
	
	private long temperature;
	private long distance;
	private long radius;
	
	private double angle;
	
	public Body() {
	}

	public Body(BodyType starType, Body core) {
		
		temperature = starType.getRandomTemp();
		radius = starType.getRandomRadius();
		
		angle = 0;
		distance = 0;
		
	}

	public Distance getRadius() {
		return new Distance(radius);
	}
	
}
