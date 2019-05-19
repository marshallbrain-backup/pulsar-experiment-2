package main.java.com.brain.pulsar.universe;

import java.util.List;

public class StarSystem {

	public StarSystem(List<BodyType> typeBodys, List<StarSystemType> typeSytems) {
		
		StarSystemType systemType = typeSytems.get(0);
		
		System.out.println(systemType.getBody());
		
	}
	
}
