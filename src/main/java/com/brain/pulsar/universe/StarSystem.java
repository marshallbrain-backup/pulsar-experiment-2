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
	
	private List<Body> bodyList;
	private List<Body> starList;
	private List<Body> planetList;
	private List<Body> moonList;
	
	private Random random;
	
	/**
	 * @param typeBodys
	 *            A list of body types
	 * @param typeSytems
	 *            A list of the systems that this system could be
	 */
	public StarSystem(List<BodyType> typeBodys, List<StarSystemType> typeSytems) {
		
		BodyType starType = null;
		
		bodyList = new ArrayList<>();
		starList = new ArrayList<>();
		planetList = new ArrayList<>();
		moonList = new ArrayList<>();
		random = new Random();
		
		List<Double> probabilityList = new ArrayList<>();
		double total = 0;
		
		for (StarSystemType s : typeSytems) {
			probabilityList.add(s.getSpawnChance());
			total += s.getSpawnChance();
		}
		
		double chance = random.nextDouble() * total;
		int id = 0;
		for (int i = 0; i < probabilityList.size(); i++) {
			chance -= probabilityList.get(i);
			if (chance <= 0) {
				id = i;
				break;
			}
		}
		
		StarSystemType systemType = typeSytems.get(id);
		
		for (BodyType b : typeBodys) {
			if (b.getName().equals(systemType.getBody())) {
				starType = b;
			}
		}
		
		Body core = new Body();
		Body star = new Body(starType, core);
		
		starList.add(star);
		
		int planetCount = systemType.getPlanetCount();
		double distance = 1;
		for (int i = 0; i < planetCount; i++) {
			
			Body p = new Body(star, distance);
			planetList.add(p);
			distance += 0.5;
			
		}
		
		bodyList.addAll(starList);
		bodyList.addAll(planetList);
		bodyList.addAll(moonList);
		
		List<Body> remove = new ArrayList<>();
		for (Body b : bodyList) {
			
			b.nTemperatureCalc(bodyList);
			boolean s = b.setType(typeBodys);
			
			if (!s) {
				remove.add(b);
			}
			
		}
		
		bodyList.removeAll(remove);
		
	}
	
	/**
	 * @return The list of stars that are in the system
	 */
	public List<Body> getStarList() {
		
		return new ArrayList<>(starList);
	}
	
	/**
	 * Generates a list of all bodies that are orbiting a star.
	 * 
	 * @return The list of planets that are in the system
	 */
	public List<Body> getPlanetList() {
		
		return new ArrayList<>(planetList);
	}
	
	/**
	 * @return A list of all bodys in a system
	 */
	public List<Body> getBodyList() {
		
		return new ArrayList<>(bodyList);
	}
	
}
