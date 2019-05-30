package com.brain.pulsar.universe;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.ion.xml.XmlParser;
import com.brain.pulsar.units.Distance;
import com.brain.pulsar.units.DistanceUnit;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.StarSystemType;

class BodyTest {
	
	private static DataContainer data;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, StarSystemType.class };
		
		List<DataContainer> uncompresed = new ArrayList<>();
		for (String xml : getXmlFiles()) {
			uncompresed.add((DataContainer) XmlParser.getXml(xml, dataTypes));
		}
		
		data = new DataContainer(uncompresed);
		
	}
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void starTest() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		
		Body core = new Body();
		Body star = new Body(typeBodys.get(0), core);
		
		testBodyPropertys(star, new Distance(3, DistanceUnit.SOLAR_RADIUS), new Distance(6, DistanceUnit.SOLAR_RADIUS),
				new Distance(0, DistanceUnit.AU), new Distance(0, DistanceUnit.AU), 10000L, 20000L, 0.0, 0.0);
		
	}
	
	@Test
	void planetTest() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		
		Body core = new Body();
		Body star = new Body(typeBodys.get(0), core, 2);
		
		testBodyPropertys(star, new Distance(3, DistanceUnit.SOLAR_RADIUS), new Distance(6, DistanceUnit.SOLAR_RADIUS),
				new Distance(2, DistanceUnit.AU), new Distance(2, DistanceUnit.AU), 0L, 20000L, 0.0, 360.0);
		
	}
	
	static void testBodyPropertys(Body body, Distance radiusMin, Distance radiusMax, Distance distanceMin,
			Distance distanceMax, Long temperatureMin, Long temperatureMax, Double angleMin, Double angleMax) {
		
		assertThat("Radius range check", body.getRadius().getDistance(), anyOf(is(radiusMin.getDistance()),
				is(radiusMax.getDistance()),
				allOf(greaterThanOrEqualTo(radiusMin.getDistance()), lessThanOrEqualTo(radiusMax.getDistance()))));
		
		assertThat("Distance range check", body.getDistance().getDistance(),
				allOf(greaterThanOrEqualTo(distanceMin.getDistance()), lessThanOrEqualTo(distanceMax.getDistance())));
		
		assertThat("Temperature range check", body.getTemperature(),
				allOf(greaterThanOrEqualTo(temperatureMin), lessThanOrEqualTo(temperatureMax)));
		
		assertThat("Angle range check", body.getAngle(),
				allOf(greaterThanOrEqualTo(angleMin), lessThanOrEqualTo(angleMax)));
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(stars);
		
		return list;
		
	}
	
	private static String stars = "<pulsar>\r\n" + "\r\n" + "	<body>\r\n" + "		<name>sc_b_star ueauoueo</name>\r\n"
			+ "		<radius min=\"3\" max=\"6\" units=\"SOLAR_RADIUS\"/>\r\n"
			+ "		<temp_set min=\"10e3\" max=\"20e3\"/>\r\n" + "		<colonizable>false</colonizable>\r\n"
			+ "	</body>\r\n" + "</pulsar>";
	
}
