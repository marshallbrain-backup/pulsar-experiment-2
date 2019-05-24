package main.java.com.brain.pulsar.universe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;

/**
 * Stores information about a cosmic body.
 * 
 * @author Marshall Brain
 *
 */
public class Body implements Cloneable {
	
	private long temperature;
	private long temperatureEmission;
	
	private double angle;
	
	private Body parent;
	private Distance distance;
	private Distance radius;
	private Random random;
	private BodyType type;
	
	public Body() {
		
		init();
	}
	
	/**
	 * Generate a new body of unknown type. Used when generating bodes whose type is
	 * dependent on the temperature.
	 * 
	 * @param parent
	 *            The body that this is orbiting
	 * @param distance
	 *            The distance from the parent this is orbiting at
	 */
	public Body(Body p, int d) {
		
		init();
		
		parent = p;
		
		distance = new Distance(d, DistanceType.AU);
		angle = random.nextDouble() * 360;
		
	}
	
	/**
	 * Generates a new body based on the given type and centered on the given
	 * parent.
	 * 
	 * @param starType
	 *            The type of body to be based off of
	 * @param parent
	 *            The body that this is centered on
	 */
	public Body(BodyType starType, Body p) {
		
		init();
		
		parent = p;
		type = starType;
		
		temperatureEmission = type.getRandomTemp();
		radius = new Distance(type.getRandomRadius(), DistanceType.SOLAR_RADIUS);
		distance = new Distance(0, DistanceType.AU);
		angle = 0;
		
		temperature = temperatureEmission;
		
	}
	
	/**
	 * Generates a new body based on the given type and orbiting the given parent at
	 * the given distance.
	 * 
	 * @param starType
	 *            The type of body to be based off of
	 * @param parent
	 *            The body that this is orbiting
	 * @param distance
	 *            The distance from the parent this is orbiting at
	 */
	public Body(BodyType starType, Body p, long d) {
		
		init();
		
		parent = p;
		type = starType;
		
		temperatureEmission = type.getRandomTemp();
		radius = new Distance(type.getRandomRadius(), DistanceType.SOLAR_RADIUS);
		distance = new Distance(d, DistanceType.AU);
		angle = random.nextDouble() * 360;
		
		temperature = temperatureEmission;
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Body clone() {
		
		Body clone = null;
		try {
			
			clone = (Body) super.clone();
			
			if (type != null) {
				clone.type = type.clone();
			}
			
			if (parent != null) {
				clone.parent = parent.clone();
			}
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return clone;
		
	}
	
	/**
	 * @return The angle the body is at.
	 */
	public double getAngle() {
		
		return angle;
	}
	
	/**
	 * @return The distance from the parent.
	 */
	public Distance getDistance() {
		
		return new Distance(distance);
	}
	
	/**
	 * Gets the distance between two bodies.
	 * 
	 * @param body1
	 * @param body2
	 * @return The distance between body1 and body2
	 */
	private Distance getDistance(Body body1, Body body2) {
		
		double[] polar1 = getPolar(body1);
		double[] polar2 = getPolar(body2);
		
		double[] polar = getPolar(polar1, polar2);
		
		return new Distance(polar[0], DistanceType.AU);
		
	}
	
	/**
	 * @param body
	 * @return A array of two doubles that are the distance and angle from the
	 *         system core that the given planet is at
	 */
	private double[] getPolar(Body body) {
		
		Body pare = body.parent;
		
		double[] polar1 = { body.distance.getDistance(), body.angle };
		if (pare == null) {
			return polar1;
		}
		double[] polar2 = { pare.distance.getDistance(), pare.angle };
		
		return getPolar(polar1, polar2);
		
	}
	
	/**
	 * @param polar1
	 * @param polar2
	 * @return Adds the two polar coordinates together
	 */
	private double[] getPolar(double[] polar1, double[] polar2) {
		
		double r1 = polar1[0];
		double r2 = polar2[0];
		double t1 = polar1[1];
		double t2 = polar2[1];
		
		double r = Math.sqrt((Math.pow(r1, 2) + Math.pow(r2, 2)) + ((2) * (r1) * (r2) * Math.cos(t2 - t1)));
		double t = t1 + Math.atan2(r2 * Math.sin(t2 - t1), r1 + r2 * Math.sin(t2 - t1));
		
		return new double[] { r, t };
		
	}
	
	/**
	 * @return The radius of the body.
	 */
	public Distance getRadius() {
		
		return new Distance(radius);
	}
	
	/**
	 * @return The temperature of the body.
	 */
	public long getTemperature() {
		
		return temperature;
	}
	
	/**
	 * Generic initialization code.
	 */
	private void init() {
		
		random = new Random();
	}
	
	/**
	 * Calculates the temperature of this body based on the emission temperature of
	 * all other bodies in the system.
	 * 
	 * @param bodyList
	 *            The list of bodies that are in the system
	 */
	public void nTemperatureCalc(List<Body> bodyList) {
		
		for (Body b : bodyList) {
			if (b != this && b.temperatureEmission > 0) {
				
				long t = b.temperatureEmission;
				Distance d = getDistance(this.clone(), b.clone());
				Distance r = b.radius;
				
				double tem = t * Math.sqrt(r.getDistance() / (2 * d.getDistance()));
				temperature += Math.round(tem);
				
			}
		}
		
	}
	
	/**
	 * Sets the type of this body to one that has a temperature range that includes
	 * this body temperature.
	 * 
	 * @param typeBodys
	 *            The list of bodies that can be assigned
	 */
	public void setType(List<BodyType> typeBodys) {
		
		if (type == null) {
			
			List<BodyType> sutable = new ArrayList<>();
			
			for (BodyType b : typeBodys) {
				if (b.inTemperatureRange(temperature)) {
					sutable.add(b);
				}
			}
			
			// TODO should be based on spawn chance
			// isSuitable()
			type = sutable.get(0);
			radius = new Distance(type.getRandomRadius(), DistanceType.SOLAR_RADIUS);
			
		}
		
	}
	
}
