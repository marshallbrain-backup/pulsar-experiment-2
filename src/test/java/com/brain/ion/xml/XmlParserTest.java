package com.brain.ion.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.elements.Range;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.DistrictType;
import com.brain.pulsar.xml.types.StarSystemType;

class XmlParserTest {
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		File commonTest = new File("XmlTestFiles");
		commonTest.mkdir();
		commonTest.deleteOnExit();
		
	}
	
	@Test
	void xmlToBody() throws NoSuchFieldException, IllegalAccessException {
		
		BodyType xmlBody = ((DataContainer) loadXmlFile("body", bodyXml)).getMatchData(BodyType.class).get(0);
		BodyType setBody = new BodyType();
		
		Field suitable = BodyType.class.getDeclaredField("suitable");
		Field name = BodyType.class.getDeclaredField("name");
		Field colonizable = BodyType.class.getDeclaredField("colonizable");
		Field climate = BodyType.class.getDeclaredField("climate");
		Field spawnOdds = BodyType.class.getDeclaredField("spawnOdds");
		Field radius = BodyType.class.getDeclaredField("radius");
		Field tempSet = BodyType.class.getDeclaredField("tempSet");
		Field tempRange = BodyType.class.getDeclaredField("tempRange");
		Field moonSize = BodyType.class.getDeclaredField("moonSize");
		
		suitable.setAccessible(true);
		name.setAccessible(true);
		colonizable.setAccessible(true);
		climate.setAccessible(true);
		spawnOdds.setAccessible(true);
		radius.setAccessible(true);
		tempSet.setAccessible(true);
		tempRange.setAccessible(true);
		moonSize.setAccessible(true);
		
		suitable.set(setBody, false);
		name.set(setBody, "pc_continental");
		colonizable.set(setBody, false);
		climate.set(setBody, "wet");
		spawnOdds.set(setBody, 1);
		radius.set(setBody, new Range("12", "25"));
		tempSet.set(setBody, new Range("10e3", "20e3"));
		tempRange.set(setBody, new Range("0", "10000"));
		moonSize.set(setBody, new Range("10", "15"));
		
		assertEquals(setBody, xmlBody);
		
	}
	
	@Test
	void xmlToSystem() throws NoSuchFieldException, IllegalAccessException {
		
		StarSystemType xmlSystem = ((DataContainer) loadXmlFile("system", systemXml)).getMatchData(StarSystemType.class)
				.get(0);
		StarSystemType setSystem = new StarSystemType();
		
		Field spawnOdds = StarSystemType.class.getDeclaredField("spawnOdds");
		Field body = StarSystemType.class.getDeclaredField("body");
		Field planetsNum = StarSystemType.class.getDeclaredField("planetsNum");
		
		spawnOdds.setAccessible(true);
		body.setAccessible(true);
		planetsNum.setAccessible(true);
		
		spawnOdds.set(setSystem, 10);
		body.set(setSystem, "sc_b_star");
		planetsNum.set(setSystem, new Range("4", "10"));
		
		assertEquals(setSystem, xmlSystem);
		
	}
	
	@Test
	void xmlToDistrict() throws NoSuchFieldException, IllegalAccessException {
		
		DistrictType xmlDistrict = ((DataContainer) loadXmlFile("district", DISTRICTS)).getMatchData(DistrictType.class)
				.get(0);
		DistrictType setDistrict = new DistrictType();
		
		Field baseBuildtime = DistrictType.class.getDeclaredField("baseBuildtime");
		
		baseBuildtime.setAccessible(true);
		
		baseBuildtime.set(setDistrict, 480);
		
		assertEquals(setDistrict, xmlDistrict);
		
	}
	
	static Object loadXmlFile(String name, String text) {
		
		File f = new File("XmlTestFiles\\" + name + ".xml");
		f.deleteOnExit();
		
		try (PrintWriter out = new PrintWriter(f)) {
			out.print(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return XmlParser.getXml(f, dataTypes);
		
	}
	
	private static Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, StarSystemType.class, DistrictType.class };
	
	private static String bodyXml = "<pulsar>\r\n" + "\r\n" + "	<body>\r\n" + "		<name>pc_continental</name>\r\n"
			+ "		<climate>wet</climate>\r\n" + "		<suitable>false</suitable>\r\n"
			+ "		<colonizable>false</colonizable>\r\n" + "		<spawn_odds>1</spawn_odds>\r\n"
			+ "		<temp_set min=\"10e3\" max=\"20e3\"/>\r\n" + "		<temp_range min=\"0\" max=\"10000\"/>\r\n"
			+ "		<radius min=\"12\" max=\"25\"/>\r\n" + "		<moon_size min=\"10\" max=\"15\"/>\r\n"
			+ "	</body>\r\n" + "\r\n" + "</pulsar>";
	private static String systemXml = "<pulsar>\r\n" + "\r\n" + "	<system>\r\n" + "		<body>sc_b_star</body>\r\n"
			+ "		<spawn_odds>10</spawn_odds>\r\n" + "		<planets_num min=\"4\" max=\"10\"/>\r\n"
			+ "	</system>\r\n" + "</pulsar>";
	private static final String DISTRICTS = 
			"<pulsar>\r\n" + 
			"	\r\n" + 
			"	<district>\r\n" + 
			"	\r\n" + 
			"			<base_buildtime>480</base_buildtime>\r\n" + 
			"			\r\n" + 
			"			<potential>\r\n" + 
			"				<tag>standard_city</tag>\r\n" + 
			"			</potential>\r\n" + 
			"			\r\n" + 
			"			<starting>\r\n" + 
			"				<tag>standard_city</tag>\r\n" + 
			"			</starting>\r\n" + 
			"			\r\n" + 
			"			<resources>\r\n" + 
			"			\r\n" + 
			"				<cost>\r\n" + 
			"					<resources type=\"mineral\">300</resources>\r\n" + 
			"				</cost>\r\n" + 
			"				<upkeep>\r\n" + 
			"					<resources type=\"energy\">1</resources>\r\n" + 
			"				</upkeep>\r\n" + 
			"				<production>\r\n" + 
			"					<resources type=\"housing\">5</resources>\r\n" + 
			"				</production>\r\n" + 
			"				\r\n" + 
			"			</resources>\r\n" + 
			"			\r\n" + 
			"	</district>\r\n" + 
			"	\r\n" + 
			"</pulsar>";
	
}
