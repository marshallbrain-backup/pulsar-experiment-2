package com.brain.pulsar.universe;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.brain.ion.xml.XmlParser;
import com.brain.pulsar.units.Distance;
import com.brain.pulsar.units.DistanceUnit;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.StarSystemType;

class StarSystemTest {
	
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
	
	@Test
	void oneStarSystemTest() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		List<StarSystemType> typeSystems = data.getMatchData(StarSystemType.class);
		
		StarSystem one = new StarSystem(typeBodys, typeSystems);
		
		List<Body> stars = one.getStarList();
		BodyTest.testBodyPropertys(stars.get(0), new Distance(3, DistanceUnit.SOLAR_RADIUS),
				new Distance(6, DistanceUnit.SOLAR_RADIUS), new Distance(0, DistanceUnit.AU),
				new Distance(0, DistanceUnit.AU), 10000L, 20000L, 0.0, 360.0);
		
		testPlanets(one.getPlanetList());
		
	}
	
	static void testPlanets(List<Body> planets) {
		
		double distance = 1;
		for (Body b : planets) {
			
			BodyTest.testBodyPropertys(b, new Distance(3, DistanceUnit.SOLAR_RADIUS),
					new Distance(6, DistanceUnit.SOLAR_RADIUS), new Distance(distance, DistanceUnit.AU),
					new Distance(distance, DistanceUnit.AU), 0L, 10000L, 0.0, 360.0);
			distance += 0.5;
			
		}
		
	}
	
	@Test
	void MassSystemGeneration() {
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, StarSystemType.class };
		
		List<DataContainer> uncompresed = new ArrayList<>();
		getXmlFiles(new File("common"), dataTypes, uncompresed);
		
		DataContainer common = new DataContainer(uncompresed);
		
		List<BodyType> typeBodys = common.getMatchData(BodyType.class);
		List<StarSystemType> typeSystems = common.getMatchData(StarSystemType.class);
		
		List<StarSystem> galaxy = new ArrayList<>();
		Map<String, Integer> bodyCount = new HashMap<>();
		for (int i = 0; i < 100; i++) {
			
			StarSystem ss = new StarSystem(typeBodys, typeSystems);
			for (Body b : ss.getBodyList()) {
				int count = bodyCount.getOrDefault(b.getId(), 0);
				bodyCount.put(b.getId(), count + 1);
			}
			galaxy.add(ss);
			
		}
		
		for (Entry<String, Integer> e : bodyCount.entrySet()) {
			System.out.println(e.getKey() + " - " + e.getValue());
		}
		
	}
	
	void getXmlFiles(File folder, Class<?>[] classList, List<DataContainer> dataList) {
		
		for (File f : folder.listFiles()) {
			
			if (f.isDirectory()) {
				getXmlFiles(f, classList, dataList);
			} else if (f.isFile()) {
				dataList.add((DataContainer) XmlParser.getXml(f, classList));
			}
			
		}
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(starsXml);
		list.add(planetsXml);
		list.add(systemsXml);
		
		return list;
		
	}
	
	private static String starsXml = "<pulsar>\r\n" + "\r\n" + "	<body>\r\n" + "		<name>sc_b_star</name>\r\n"
			+ "		<suitable>false</suitable>\r\n" + "		<radius min=\"3\" max=\"6\" units=\"SOLAR_RADIUS\"/>\r\n"
			+ "		<temp_set min=\"10e3\" max=\"20e3\"/>\r\n" + "		<colonizable>false</colonizable>\r\n"
			+ "	</body>\r\n" + "</pulsar>";
	
	private static String planetsXml = "<pulsar>\r\n" + "\r\n" + "	<body>\r\n" + "		<name>pc_continental</name>\r\n"
			+ "		<climate>wet</climate>\r\n" + "		<tag></tag>\r\n" + "		<spawn_odds>1</spawn_odds>\r\n"
			+ "		<temp_range min=\"0\" max=\"10000\"/>\r\n"
			+ "		<radius min=\"3\" max=\"6\" units=\"SOLAR_RADIUS\"/>\r\n"
			+ "		<moon_size min=\"10\" max=\"15\"/>\r\n" + "	</body>\r\n" + "\r\n" + "</pulsar>";
	
	private static String systemsXml = "<pulsar>\r\n" + "\r\n" + "	<system>\r\n" + "		<body>sc_b_star</body>\r\n"
			+ "		<spawn_odds>10</spawn_odds>\r\n" + "		<planets_num min=\"4\" max=\"10\"/>\r\n"
			+ "	</system>\r\n" + "</pulsar>";
	
}
