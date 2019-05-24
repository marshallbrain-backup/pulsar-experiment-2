package main.java.com.brain.pulsar.universe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The object that contains the information about a star system.
 * 
 * @author Marshall Brain
 *
 */
public class StarSystem {
	
	private List<Body> BodyList;
	private List<Body> starList;
	private List<Body> planetList;
	private List<Body> moonList;

	/**
	 * @param typeBodys A list of body types
	 * @param typeSytems A list of the systems that this system could be
	 */
	public StarSystem(List<BodyType> typeBodys, List<StarSystemType> typeSytems) {
		
		BodyType starType = null;
		
		BodyList = new ArrayList<>();
		starList = new ArrayList<>();
		planetList = new ArrayList<>();
		moonList = new ArrayList<>();
		
		StarSystemType systemType = typeSytems.get(0);
		
		for(BodyType b: typeBodys) {
			if(b.getName().equals(systemType.getBody())) {
				starType = b;
			}
		}
		
		Body core = new Body();
		Body star = new Body(starType, core);
		
		starList.add(star);
		
		int planetCount = systemType.getPlanetCount();
		int distance = 1;
		for(int i = 0; i < planetCount; i++) {
			
			Body p = new Body(star, distance);
			planetList.add(p);
			distance++;
			
		}
		
		BodyList.addAll(starList);
		BodyList.addAll(planetList);
		BodyList.addAll(moonList);
		
		for(Body b: BodyList) {
			
			b.nTemperatureCalc(BodyList);
			b.setType(typeBodys);
			
		}
		
		
	}

	/**
	 * @return The list of stars that are in the system
	 */
	public List<Body> getStarList() {
		
		List<Body> list = new ArrayList<>();
		
		for(Body b: starList) {
			list.add(b.clone());
		}
		
		return list;
	}

	/**
	 * Generates a list of all bodies that are orbiting a star.
	 * 
	 * @return The list of planets that are in the system
	 */
	public List<Body> getPlanetList() {
		
		List<Body> list = new ArrayList<>();
		
		for(Body b: planetList) {
			list.add(b.clone());
		}
		
		return list;
	}
	
}
