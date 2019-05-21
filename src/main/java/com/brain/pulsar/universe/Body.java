package main.java.com.brain.pulsar.universe;

import java.util.Random;

import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;

public class Body implements Cloneable {
	
	private long temperature;
	
	private double angle;
	
	private Distance distance;
	private Distance radius;
	private Random random;
	
	public Body() {
		init();
	}

	public Body(BodyType starType, Body core) {
		init();
		
		temperature = starType.getRandomTemp();
		radius = new Distance(starType.getRandomRadius(), DistanceType.SOLAR_RADIUS);
		
		angle = 0;
		distance = new Distance(0, DistanceType.AU);
		
	}

	public Body(BodyType starType, Body parent, long d) {
		init();
		
		temperature = starType.getRandomTemp();
		radius = new Distance(starType.getRandomRadius(), DistanceType.SOLAR_RADIUS);
		
		angle = random.nextDouble() * 360;
		distance = new Distance(d, DistanceType.AU);
		
	}
	
	private void init() {
		random = new Random();
	}

	public Distance getRadius() {
		return new Distance(radius);
	}

	public Distance getDistance() {
		return new Distance(distance);
	}

	public long getTemperature() {
		return temperature;
	}

	public double getAngle() {
		return angle;
	}
	
	@Override
	public Body clone() {
		
		Body clone = new Body();
		
		clone.angle = angle;
		clone.distance = distance;
		clone.radius = radius;
		clone.temperature = temperature;
		
		return clone;
		
	}
	
}
