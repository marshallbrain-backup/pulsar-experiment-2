package main.java.com.brain.pulsar.universe;

import java.util.Random;

import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;

public class Body {
	
	private long temperature;
	
	private double angle;
	
	private Distance distance;
	private Distance radius;
	private Random random;
	
	public Body() {
	}

	public Body(BodyType starType, Body core) {
		
		random = new Random();
		
		temperature = starType.getRandomTemp();
		radius = new Distance(starType.getRandomRadius(), DistanceType.METER);
		
		angle = 0;
		distance = new Distance(0, DistanceType.AU);;
		
	}

	public Body(BodyType starType, Body parent, long d) {
		
		random = new Random();
		
		temperature = starType.getRandomTemp();
		radius = new Distance(starType.getRandomRadius(), DistanceType.METER);
		
		angle = random.nextDouble() * 360;
		distance = new Distance(d, DistanceType.AU);
		
	}

	public Distance getRadius() {
		return new Distance(radius);
	}

	public long getTemperature() {
		return temperature;
	}

	public double getAngle() {
		return angle;
	}
	
}
