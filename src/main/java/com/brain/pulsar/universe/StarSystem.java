package main.java.com.brain.pulsar.universe;

import java.util.List;

public class StarSystem {

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
		
	}
	
}
