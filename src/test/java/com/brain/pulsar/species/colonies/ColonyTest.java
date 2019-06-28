package com.brain.pulsar.species.colonies;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.ion.xml.XmlParser;
import com.brain.pulsar.other.PopCollection;
import com.brain.pulsar.other.TimeEntry;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.elements.JobAdapter;
import com.brain.pulsar.xml.elements.JobBase;
import com.brain.pulsar.xml.elements.JobType;
import com.brain.pulsar.xml.elements.Modifier;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.BuildingType;
import com.brain.pulsar.xml.types.DistrictType;

public class ColonyTest {
	
	private static DataContainer data;
	private static List<JobBase> jobList;
	private Colony colony;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		JobAdapter jobAdapter = new JobAdapter();
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, DistrictType.class, BuildingType.class, JobType.class, Modifier.class};
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
		List<DistrictType> districtType = data.getMatchData(DistrictType.class);
		List<BuildingType> buildingType = data.getMatchData(BuildingType.class);
		List<JobType> jobTypes = data.getMatchData(JobType.class);
		
		for(JobBase b: jobList) {
			b.setType(jobTypes);
		}
		
		Body body = new Body(bodyType, null);
		colony = new Colony(body, districtType, buildingType);
		
	}
	
	@Test
	void creation() {
		
		assertNotNull(colony);
	}
	
	@Test
	void districtTest() {
		
		Set<String> districtTypeSet = new HashSet<>();
		districtTypeSet.add("district_city");
		
		List<District> districts = colony.getDistricts();
		
		for(District d: districts) {
			if(d.isAssined()) {
				assertTrue("Colony should contain district of type: " + d.getName(), districtTypeSet.contains(d.getName()));
			}
		}
		
	}
	
	@Test
	void buildingTest() {
		
		Set<String> buildingTypeSet = new HashSet<>();
		buildingTypeSet.add("building_city");
		
		List<Building> buildings = colony.getBuildings();
		
		for(Building b: buildings) {
			if(b.isAssined()) {
				assertTrue("Colony should contain district of type: " + b.getName(), buildingTypeSet.contains(b.getName()));
			}
		}
		
	}
	
	@Test
	void popGrouthTest() {
		
		PopCollection pc = new PopCollection();
		
		pc.tick(new TimeEntry(11));
		pc.tick(new TimeEntry(11));
		pc.tick(new TimeEntry(11));
		
		assertEquals(1, pc.getPopList().size());
		
	}
	
	@Test
	void jobTest() {
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(BODYS);
		list.add(DISTRICTS);
		list.add(BUILDINGS);
		list.add(JOB_TYPES);
		
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
			"	</district>" + 
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
	
}
