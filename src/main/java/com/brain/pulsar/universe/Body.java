package main.java.com.brain.pulsar.universe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;

public class Body implements Cloneable {
	
	private long temperature;
	private long temperatureEmission;
	
	private double angle;
	
	private Distance distance;
	private Distance radius;
	private Random random;
	private Body parent;
	private BodyType type;
	
	public Body() {
		init();
	}

	public Body(BodyType starType, Body core) {
		init();
		
		type = starType;
		
		temperatureEmission = type.getRandomTemp();
		radius = new Distance(type.getRandomRadius(), DistanceType.SOLAR_RADIUS);
		distance = new Distance(0, DistanceType.AU);
		angle = 0;
		
		temperature = temperatureEmission;
		
	}

	public Body(BodyType starType, Body p, long d) {
		init();
		
		parent = p;
		type = starType;
		
		temperature = type.getRandomTemp();
		radius = new Distance(type.getRandomRadius(), DistanceType.SOLAR_RADIUS);
		distance = new Distance(d, DistanceType.AU);
		angle = random.nextDouble() * 360;
		
		temperature = temperatureEmission;
		
	}
	
	public Body(Body p, int d) {
		init();
		
		parent = p;

		distance = new Distance(d, DistanceType.AU);
		angle = random.nextDouble() * 360;
		
	}

	private void init() {
		random = new Random();
	}

	public void nTemperatureCalc(List<Body> bodyList) {
		
		for(Body b: bodyList) {
			if(b != this && b.temperatureEmission > 0) {
				
				long t = b.temperatureEmission;
				Distance d = Body.getDistance(this.clone(), b.clone());
				Distance r = b.radius;
				
				double tem = t * Math.sqrt(r.getDistance()/(2*d.getDistance()));
				temperature += Math.round(tem);
				
			}
		}
		
	}

	public void setType(List<BodyType> typeBodys) {
		
		if(type == null) {
			
			List<BodyType> sutable = new ArrayList<>();
			
			for(BodyType b: typeBodys) {
				if(b.inTemperatureRange(temperature)) {
					sutable.add(b);
				}
			}
			
			//TODO should be based on spawn chance
			//isSuitable()
			type = sutable.get(0);
			radius = new Distance(type.getRandomRadius(), DistanceType.SOLAR_RADIUS);
			
		}
		
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

	private static Distance getDistance(Body body1, Body body2) {
		
		double[] polar1 = getPolar(body1);
		double[] polar2 = getPolar(body2);
		
		double[] polar = getPolar(polar1, polar2);
		
		return new Distance(polar[0], DistanceType.AU);
		
	}

	private static double[] getPolar(Body body) {
		
		Body pare = body.parent;
		
		double[] polar1 = {body.distance.getDistance(), body.angle};
		if(pare == null) {
			return polar1;
		}
		double[] polar2 = {pare.distance.getDistance(), pare.angle};
		
		return getPolar(polar1, polar2);
		
	}
	
	private static double[] getPolar(double[] polar1, double[] polar2) {
		
		double r1 = polar1[0];
		double r2 = polar2[0];
		double t1 = polar1[1];
		double t2 = polar2[1];
		
		double r = 
				Math.sqrt(
						( Math.pow(r1, 2) + Math.pow(r2, 2) ) + 
						( (2)*(r1)*(r2) * Math.cos(t2 - t1) ) 
						)
				;
		double t = 
				t1 + Math.atan2(
						r2 * Math.sin(t2 - t1),
						r1 + r2 * Math.sin(t2 - t1)
						)
				;
		
		return new double[] {r, t};
		
	}

	@Override
	public Body clone() {
		
		Body clone = null;
		try {
			
			clone = (Body) super.clone();
			
			if(type != null) {
				clone.type = type.clone();
			}
			
			if(parent != null) {
				clone.parent = parent.clone();
			}
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return clone;
		
	}
	
}
