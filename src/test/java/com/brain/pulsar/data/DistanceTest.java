package test.java.com.brain.pulsar.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;


class DistanceTest {
	
	@Test
	void meter() {
		assertEquals(new Distance(695500L, DistanceType.METER), new Distance(1, DistanceType.SOLAR_RADIUS));
		assertEquals(new Distance(149597870700L, DistanceType.METER), new Distance(1, DistanceType.AU));
	}
	
	@Test
	void au() {
		assertEquals(new Distance(5, DistanceType.AU), new Distance(149597870700L*5, DistanceType.METER));
		
	}
	
	@Test
	void solarRadius() {
		assertEquals(new Distance(5, DistanceType.SOLAR_RADIUS), new Distance(695500L*5, DistanceType.METER));
	}
	
}
