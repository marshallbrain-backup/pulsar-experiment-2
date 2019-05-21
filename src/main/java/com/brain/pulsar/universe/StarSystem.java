package main.java.com.brain.pulsar.universe;

import java.util.ArrayList;
import java.util.List;

public class StarSystem {
	
	private List<Body> starList = new ArrayList<>();

	public StarSystem(List<BodyType> typeBodys, List<StarSystemType> typeSytems) {
		
		StarSystemType systemType = typeSytems.get(0);
		BodyType starType = null;
		
		for(BodyType b: typeBodys) {
			if(b.getName().equals(systemType.getBody())) {
				starType = b;
			}
		}
		
		Body core = new Body();
		Body star = new Body(starType, core);
		
		starList.add(star);
		
	}

	public List<Body> getStarList() {
		
		List<Body> list = new ArrayList<>();
		
		for(Body b: starList) {
			list.add(b.clone());
		}
		
		return list;
	}
	
}
