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
import com.brain.pulsar.other.ModifierBucket;
import com.brain.pulsar.other.Resource;
import com.brain.pulsar.other.ResourceBucket;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.elements.JobType;
import com.brain.pulsar.xml.elements.Modifier;
import com.brain.pulsar.xml.elements.ResourceBase;
import com.brain.pulsar.xml.elements.ResourceType;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.DistrictType;

class DistrictTest {
	
	private static DataContainer data;
	private District district;
	private ResourceType resourceType;
	private JobType jobType;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, DistrictType.class, Modifier.class };
		
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
	void operations() {
		
		List<Resource> expected = new ArrayList<>();
		
		expected.add(new Resource("energy", 4));
		expected.add(new Resource("housing", 5));
		
		ResourceBucket bucket = district.getOperations();
		
		bucket.merge();
		
		List<Resource> resources = bucket.getResources();
		
		assertThat(resources, containsInAnyOrder(resources.toArray()));
		
	}
	
	@Test
	void modifiers() {
		
	}
	
	@Test
	void supply() {
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(BODYS);
		list.add(DISTRICTS);
		list.add(RESORCE_TYPES);
		list.add(JOB_TYPES);
		list.add(MODIFIERS);
		
		return list;
		
	}
	
	private static final String BODYS = "" + 
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
	private static final String DISTRICTS = "" + 
			"<pulsar>" + 
			"	<district>" + 
			"		<name>district_city</name>" + 
			"		<potential>" + 
			"			<trigger name=\"has_type_tag\">" + 
			"				standard_city" + 
			"			</trigger>" + 
			"		</potential>" + 
			"		<starting>" + 
			"			<trigger name=\"has_type_tag\">" + 
			"				standard_city" + 
			"			</trigger>" + 
			"		</starting>" + 
			"		<upkeep>" + 
			"			<resource id=\"energy\">" + 
			"				1" + 
			"			</resource>" + 
			"		</upkeep>" + 
			"		<production>" + 
			"			<resource id=\"energy\">" + 
			"				5" + 
			"			</resource>" + 
			"			<resource id=\"housing\">" + 
			"				5" + 
			"			</resource>" + 
			"		</production>" + 
			"		<supply>" + 
			"			<job id=\"clerk\">" + 
			"				2" + 
			"			</job>" + 
			"		</supply>" + 
			"	</district>" + 
			"</pulsar>";
	private static final String RESORCE_TYPES = "" + 
			"<pulsar>" + 
			"	<resorce_type>" + 
			"		<id>energy</id>" + 
			"		<root>empire</root>" + 
			"	</resorce_type>" + 
			"	<resorce_type>" + 
			"		<id>housing</id>" + 
			"		<root>planet</root>" + 
			"		<static>true</static>" + 
			"	</resorce_type>" + 
			"</pulsar>";
	private static final String JOB_TYPES = "" + 
			"<pulsar>" + 
			"	<job_type>" + 
			"		<id>clerk</id>" + 
			"		<production>" + 
			"			<resource id=\"energy\">" + 
			"				3" + 
			"			</resource>" + 
			"		</production>" + 
			"	</job_type>" + 
			"</pulsar>";
	private static final String MODIFIERS = "" + 
			"<pulsar>" + 
			"	<modifier>" + 
			"		<id>modifier 1</id>" + 
			"		<parent>district_city.production</parent>" + 
			"		<target>energy</target>" + 
			"		<amount>2</amount>" + 
			"	</modifier>" + 
			"</pulsar>";
}
