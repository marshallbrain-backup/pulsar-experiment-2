package com.brain.pulsar.species.colonies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.ion.xml.XmlParser;
import com.brain.pulsar.other.JobCollection;
import com.brain.pulsar.other.Resource;
import com.brain.pulsar.other.ResourceCollection;
import com.brain.pulsar.other.TimeEntry;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.elements.BuildTime;
import com.brain.pulsar.xml.elements.JobAdapter;
import com.brain.pulsar.xml.elements.JobBase;
import com.brain.pulsar.xml.elements.JobType;
import com.brain.pulsar.xml.elements.Modifier;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.BuildingType;

public class BuildingTest {
	
	private static DataContainer data;
	private static List<JobBase> jobList;
	private Building building;
	private List<Modifier> modifiers;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		JobAdapter jobAdapter = new JobAdapter();
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, BuildingType.class, JobType.class, Modifier.class};		
		XmlAdapter<?, ?>[] adapterList = new XmlAdapter<?, ?>[] {jobAdapter};
		
		List<DataContainer> uncompresed = new ArrayList<>();
		for (String xml : getXmlFiles()) {
			uncompresed.add((DataContainer) XmlParser.getXml(xml, dataTypes, adapterList));
		}
		
		jobList = jobAdapter.getJobList();
		
		data = new DataContainer(uncompresed);
	
	}
	
	@BeforeEach
	void setUp() throws Exception {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);
		List<JobType> jobTypes = data.getMatchData(JobType.class);
		modifiers = data.getMatchData(Modifier.class);
		
		for(JobBase b: jobList) {
			b.setType(jobTypes);
		}
		
		Body body = new Body(bodyType, null);
		building = new Building();
		building.setPendingType(buildingType, body);

		ConstructionCoordinator cc = new ConstructionCoordinator();
		cc.add(building);
		cc.tick(new TimeEntry(10));
		
	}
	
	@Test
	void creation() {
		
		assertNotNull(building);
	}
	
	@Test
	void operations() {
		
		Map<String, Double> expected = new HashMap<>();
		
		expected.put("energy", 4.0);
		expected.put("housing", 5.0);
		
		ResourceCollection colony = new ResourceCollection("colony", "standard");
		
		colony.addManagers(building);
		
		List<Resource> resources = colony.getResources();
		
		checkResorcesMatch(resources, expected);
		
	}

	@Test
	void supply() {
		
		Map<String, Double> expected = new HashMap<>();
		
		expected.put("energy", 6.0);
		
		ResourceCollection colony = new ResourceCollection("colony", "standard");
		JobCollection jobs = new JobCollection();
		
		jobs.addJobs(building);
		colony.addManagers(jobs);
		
		List<Resource> resources = colony.getResources();
		
		checkResorcesMatch(resources, expected);
		
	}

	@Test
	void modifiers() {
		
		Map<String, Double> expected = new HashMap<>();
		
		expected.put("energy", 26.0);
		expected.put("housing", 10.0);
		
		ResourceCollection colony = new ResourceCollection("colony", "standerd");
		JobCollection jobs = new JobCollection();
		
		jobs.addJobs(building);
		
		colony.addManagers(building);
		colony.addManagers(jobs);
		colony.applyModifiers(modifiers.toArray(new Modifier[0]));
		
		List<Resource> resources = colony.getResources();
		
		checkResorcesMatch(resources, expected);
		
	}
	
	void checkResorcesMatch(List<Resource> resources, Map<String, Double> expected) {
		
		assertEquals("Size check", resources.size(), expected.size());
		
		for(Resource r: resources) {
			
			assertTrue("Id match for Resource: " + r, expected.containsKey(r.getId()));
			assertTrue("Value match for Resource: " + r, expected.get(r.getId()) == r.getAmount());
			
		}
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(BODYS);
		list.add(BUILDINGS);
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
	private static final String BUILDINGS = "" + 
			"<pulsar>" + 
			"	<building>" + 
			"		<name>building_city</name>" + 
			"		<base_buildtime>10</base_buildtime>" + 
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
			"	</building>" + 
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
			"		<parent>district:\\w*\\.production</parent>" + 
			"		<target>energy</target>" + 
			"		<amount>1</amount>" + 
			"	</modifier>" + 
			"	<modifier>" + 
			"		<id>modifier 2</id>" + 
			"		<parent>district:\\w*\\.production</parent>" + 
			"		<target>.*</target>" + 
			"		<amount>1</amount>" + 
			"	</modifier>" + 
			"	<modifier>" + 
			"		<id>modifier 3</id>" + 
			"		<parent>job\\.worker\\.clerk\\.production</parent>" + 
			"		<target>energy</target>" + 
			"		<amount>1</amount>" + 
			"	</modifier>" + 
			"</pulsar>";
	
}
