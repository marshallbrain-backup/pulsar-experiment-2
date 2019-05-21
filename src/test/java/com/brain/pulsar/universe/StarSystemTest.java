package test.java.com.brain.pulsar.universe;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

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
import main.java.com.brain.pulsar.universe.StarSystem;
import main.java.com.brain.pulsar.universe.StarSystemType;
import test.java.com.brain.pulsar.JUnitTestSetup;


class StarSystemTest {
	
	private static DataContainer data;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		data = JUnitTestSetup.xmlReader(getXmlFiles());
		
	}
	
	@BeforeEach
	void setUp() {
		
		
		
	}
	
	@Test
	void oneStarSystemTest() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		List<StarSystemType> typeSystems = data.getMatchData(StarSystemType.class);
		
		StarSystem one = new StarSystem(typeBodys, typeSystems);
		
		List<Body> stars = one.getStarList();
		BodyTest.testBodyPropertys(stars.get(0),
				new Distance(3, DistanceType.SOLAR_RADIUS), new Distance(6, DistanceType.SOLAR_RADIUS),
				new Distance(0, DistanceType.AU), new Distance(0, DistanceType.AU),
				10000L, 20000L,
				0.0, 360.0);
		
		testPlanets(one.getPlanetList());
		
	}
	
	static void testPlanets(List<Body> planets) {

		int distance = 1;
		for(Body b: planets) {
			
			BodyTest.testBodyPropertys(b,
					new Distance(3, DistanceType.SOLAR_RADIUS), new Distance(6, DistanceType.SOLAR_RADIUS),
					new Distance(distance, DistanceType.AU), new Distance(distance, DistanceType.AU),
					0L, 10000L,
					0.0, 360.0);
			distance++;
			
		}
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(stars);
		list.add(planets);
		list.add(systems);
		
		return list;
		
	}
	
	private static String stars = 
			"<pulsar>\r\n" + 
			"\r\n" + 
			"	<body>\r\n" + 
			"		<name>sc_b_star</name>\r\n" + 
			"		<suitable>false</suitable>\r\n" + 
			"		<radius min=\"3\" max=\"6\"/>\r\n" + 
			"		<temp_set min=\"10e3\" max=\"20e3\"/>\r\n" + 
			"		<colonizable>false</colonizable>\r\n" + 
			"	</body>\r\n" + 
			"</pulsar>";
	
	private static String planets = 
			"<pulsar>\r\n" + 
			"\r\n" + 
			"	<body>\r\n" + 
			"		<name>pc_continental</name>\r\n" + 
			"		<climate>wet</climate>\r\n" + 
			"		<tag></tag>\r\n" + 
			"		<spawn_odds>1</spawn_odds>\r\n" + 
			"		<temp_range min=\"0\" max=\"10000\"/>\r\n" + 
			"		<radius min=\"12\" max=\"25\"/>\r\n" + 
			"		<moon_size min=\"10\" max=\"15\"/>\r\n" + 
			"	</body>\r\n" + 
			"\r\n" + 
			"</pulsar>";
	
	private static String systems = 
			"<pulsar>\r\n" + 
			"\r\n" + 
			"	<system>\r\n" + 
			"		<body>sc_b_star</body>\r\n" + 
			"		<spawn_odds>10</spawn_odds>\r\n" + 
			"		<planets_num min=\"4\" max=\"10\"/>\r\n" + 
			"	</system>\r\n" + 
			"</pulsar>";
	
}
