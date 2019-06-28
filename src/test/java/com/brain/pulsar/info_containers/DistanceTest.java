package com.brain.pulsar.info_containers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DistanceTest {
	
	@Test
	void meter() {
		
		assertEquals(new Distance(695500000L, DistanceUnit.METER), new Distance(1, DistanceUnit.SOLAR_RADIUS));
		assertEquals(new Distance(149597870700L, DistanceUnit.METER), new Distance(1, DistanceUnit.AU));
	}
	
	@Test
	void au() {
		
		assertEquals(new Distance(5, DistanceUnit.AU), new Distance(149597870700L * 5, DistanceUnit.METER));
		assertEquals(new Distance(5, DistanceUnit.AU).convert(DistanceUnit.SOLAR_RADIUS),
				new Distance(149597870700L * 5, DistanceUnit.METER));
	}
	
	@Test
	void solarRadius() {
		
		assertEquals(new Distance(5, DistanceUnit.SOLAR_RADIUS), new Distance(695500000L * 5, DistanceUnit.METER));
	}
	
}
