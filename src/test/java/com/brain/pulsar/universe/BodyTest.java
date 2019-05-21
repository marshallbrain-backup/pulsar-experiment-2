package test.java.com.brain.pulsar.universe;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;
import main.java.com.brain.pulsar.files.DataContainer;
import main.java.com.brain.pulsar.universe.Body;
import main.java.com.brain.pulsar.universe.BodyType;
import test.java.com.brain.pulsar.JUnitTestSetup;


class BodyTest {
	
	private static DataContainer data;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		data = JUnitTestSetup.xmlReader(getXmlFiles());
		
	}
	
	@BeforeEach
	void setUp() {
		
		
		
	}
	
	@Test
	void starTest() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		
		Body core = new Body();
		Body star = new Body(typeBodys.get(0), core);
		
		testBodyPropertys(star,
				new Distance(3, DistanceType.SOLAR_RADIUS), new Distance(6, DistanceType.SOLAR_RADIUS),
				new Distance(0, DistanceType.AU), new Distance(0, DistanceType.AU),
				10000L, 20000L,
				0.0, 0.0);
		
	}
	
	@Test
	void planetTest() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		
		Body core = new Body();
		Body star = new Body(typeBodys.get(0), core, 2);
		
		testBodyPropertys(star,
				new Distance(3, DistanceType.SOLAR_RADIUS), new Distance(6, DistanceType.SOLAR_RADIUS),
				new Distance(2, DistanceType.AU), new Distance(2, DistanceType.AU),
				10000L, 20000L,
				0.0, 360.0);
		
	}
	
	static void testBodyPropertys(Body body, 
			Distance radiusMin, Distance radiusMax, 
			Distance distanceMin, Distance distanceMax, 
			Long temperatureMin, Long temperatureMax, 
			Double angleMin, Double angleMax) {
		
		assertThat("Radius range check", 
				body.getRadius().getDistance(), 
				anyOf(
						is(radiusMin.getDistance()), 
						is(radiusMax.getDistance()), 
						allOf(
								greaterThanOrEqualTo(radiusMin.getDistance()), 
								lessThanOrEqualTo(radiusMax.getDistance())
								)
						)
				);
		
		assertThat("Distance range check", 
				body.getDistance().getDistance(), 
				allOf(
						greaterThanOrEqualTo(distanceMin.getDistance()), 
						lessThanOrEqualTo(distanceMax.getDistance())
						)
				);
		
		assertThat("Temperature range check", 
				body.getTemperature(), 
				allOf(
						greaterThanOrEqualTo(temperatureMin), 
						lessThanOrEqualTo(temperatureMax)
						)
				);
		
		assertThat("Angle range check", 
				body.getAngle(), 
				allOf(
						greaterThanOrEqualTo(angleMin), 
						lessThanOrEqualTo(angleMax)
						)
				);
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(stars);
		
		return list;
		
	}
	
	private static String stars = 
			"<pulsar>\r\n" + 
			"\r\n" + 
			"	<body>\r\n" + 
			"		<name>sc_b_star ueauoueo</name>\r\n" + 
			"		<radius min=\"3\" max=\"6\"/>\r\n" + 
			"		<temp_set min=\"10e3\" max=\"20e3\"/>\r\n" + 
			"		<colonizable>false</colonizable>\r\n" + 
			"	</body>\r\n" + 
			"</pulsar>";
	
}
