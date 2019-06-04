package com.brain.pulsar.species.colonies;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.ion.xml.XmlParser;
import com.brain.pulsar.other.Scope;
import com.brain.pulsar.species.ResourceBucket;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.elements.Resource;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.DistrictType;
import com.brain.pulsar.xml.types.StarSystemType;


class DistrictTest {
	
	private static DataContainer data;
	private District district;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, DistrictType.class };
		
		List<DataContainer> uncompresed = new ArrayList<>();
		for (String xml : getXmlFiles()) {
			uncompresed.add((DataContainer) XmlParser.getXml(xml, dataTypes));
		}
		
		data = new DataContainer(uncompresed);
	
	}
	
	@BeforeEach
	void setUp() throws Exception {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		DistrictType districtType = data.getMatchData(DistrictType.class).get(0);
		
		Body body = new Body(bodyType, null);
		district = District.create(districtType, body);
		
	}
	
	@Test
	void creation() {
		
		assertNotNull(district);
	}
	
	@Test
	void income() {
		
		List<Resource> upkeep = new ArrayList<>();
		upkeep.add(new Resource(1, "energy"));
		
		List<Resource> production = new ArrayList<>();
		production.add(new Resource(5, "housing"));
		
		ResourceBucket bucket = new ResourceBucket("district.basic_city", upkeep, production);
		
		ResourceBucket b = district.getResources();
		
		assertEquals(bucket, b);
		
	}
	
	@Test
	void construction() {
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(DISTRICTS);
		list.add(BODYS);
		
		return list;
		
	}
	
	private static final String BODYS = 
			"<pulsar>" + 
			"	<body>" + 
			"		<name>pc_continental</name>" + 
			"		<tags>" + 
			"			<tag>standard_city</tag>" + 
			"		</tags>" +
			"		<spawn_odds>1</spawn_odds>" + 
			"		<temp_range min=\"283\" max=\"300\"/><!--17-->" + 
			"		<radius min=\"3178000\" max=\"9534000\"/>" + 
			"		<tag></tag>" + 
			"	</body>" + 
			"</pulsar>";
	private static final String DISTRICTS = 
			"<pulsar>\r\n" + 
			"	<district>\r\n" + 
			"		<name>basic_city</name>\r\n" + 
			"		<base_buildtime>480</base_buildtime>\r\n" + 
			"		<potential>\r\n" + 
			"				<trigger name=\"has_type_tag\">\r\n" + 
			"					standard_city\r\n" + 
			"				</trigger>\r\n" + 
			"		</potential>\r\n" + 
			"		<starting>\r\n" + 
			"				<trigger name=\"has_planet_tag\">\r\n" + 
			"					standard_city\r\n" + 
			"				</trigger>\r\n" + 
			"		</starting>\r\n" + 
			"		<operations>\r\n" + 
			"			<cost>\r\n" + 
			"				<resource type=\"mineral\">300</resource>\r\n" + 
			"			</cost>\r\n" + 
			"			<upkeep>\r\n" + 
			"				<resource type=\"energy\">1</resource>\r\n" + 
			"			</upkeep>\r\n" + 
			"			<production>\r\n" + 
			"				<resource type=\"housing\">5</resource>\r\n" + 
			"			</production>\r\n" + 
			"		</operations>\r\n" + 
			"	</district>\r\n" + 
			"</pulsar>";
	
}
