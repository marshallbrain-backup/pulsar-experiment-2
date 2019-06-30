package com.brain.pulsar.empires.colonies;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.ion.xml.XmlParser;
import com.brain.pulsar.empires.colonies.jobs.Job;
import com.brain.pulsar.empires.colonies.jobs.JobBase;
import com.brain.pulsar.empires.colonies.types.BuildingType;
import com.brain.pulsar.empires.colonies.types.DistrictType;
import com.brain.pulsar.empires.colonies.types.JobType;
import com.brain.pulsar.resources.Modifier;
import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.universe.types.BodyType;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.adapters.JobAdapter;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class BuildingTest {
	
	static DataContainer data;
	static JobAdapter jobAdapter;
	
	Building building;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		jobAdapter = new JobAdapter();
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, BuildingType.class, JobType.class, Modifier.class};		
		XmlAdapter<?, ?>[] adapterList = new XmlAdapter<?, ?>[] {jobAdapter};
		
		String[] resourceList = new String[] {
				"buildings.xml",
				"jobs.xml",
				"modifiers.xml",
				"bodys.xml",
		};
		
		List<DataContainer> uncompresed = new ArrayList<>();
		for (String xmlFiles: resourceList) {
			uncompresed.add((DataContainer) XmlParser.getXml(new File("junit test files\\" + xmlFiles), dataTypes, adapterList));
		}
		
		data = new DataContainer(uncompresed);
	
	}
	
	@BeforeEach
	void setUp() throws Exception {
		
		List<JobType> jobTypes = data.getMatchData(JobType.class);
		
		for(JobBase b: jobAdapter.getJobList()) {
			b.setType(jobTypes);
		}
		
		building = new Building();
	
	}
	
	@Test
	void creation() {
		
		assertThat(building, notNullValue());
		
	}

	@Test
	void pendingWorked() {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);

		Body body = new Body(bodyType, null);
		
		assertThat(building.setPendingType(buildingType, body), is(true));
		
	}

	@Test
	void pendingCorrect() {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		
		assertThat(building.getPendingType(), equalTo(buildingType));
		
	}
	
	@Test
	void buildWorked() {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		building.build();
		
		assertThat(building.isAssined(), is(true));
		
	}
	
	@Test
	void buildCorrect() {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		building.build();
		
		assertThat(building.getBuildingType(), equalTo(buildingType));
		
	}
	
	@Test
	void buildTimeBefore() {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		
		assertThat(building.getBuildTime(), equalTo(10));
		
	}
	
	@Test
	void buildTimeAfter() {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		building.build();

		assertThat(building.getBuildTime(), equalTo(0));
		
	}
	
	@Test
	void resource() {
		
		List<String> expected = new ArrayList<>();
		expected.add("energy -1.0");
		expected.add("energy 5.0");
		expected.add("housing 5.0");
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		building.build();
		
		List<Resource> actual = building.getResources();
		
		checkResorcesMatch(actual, expected);
		
	}
	
	@Test
	void resourceModifier() {
		
		List<String> expected = new ArrayList<>();
		expected.add("energy -2.0");
		expected.add("energy 10.0");
		expected.add("housing 10.0");
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);
		Modifier modifier = data.getMatchData(Modifier.class).get(0);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		building.build();
		building.applyModifiers(modifier);
		
		List<Resource> actual = building.getResources();
		
		checkResorcesMatch(actual, expected);
		
	}
	
	@Test
	void job() {
		
		List<String> expected = new ArrayList<>();
		expected.add("energy 3.0");
		expected.add("energy 3.0");
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);
		Modifier modifier = data.getMatchData(Modifier.class).get(0);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		building.build();
		building.applyModifiers(modifier);
		
		List<Job> jobs = building.getJobs();
		List<Resource> actual = new ArrayList<>();
		
		for(Job j: jobs) {
			actual.addAll(j.getResources());
		}
		
		checkResorcesMatch(actual, expected);
		
	}
	
	@Test
	void jobModifier() {
		
		List<String> expected = new ArrayList<>();
		expected.add("energy 6.0");
		expected.add("energy 6.0");
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		BuildingType buildingType = data.getMatchData(BuildingType.class).get(0);
		Modifier modifier = data.getMatchData(Modifier.class).get(1);

		Body body = new Body(bodyType, null);
		building.setPendingType(buildingType, body);
		building.build();
		building.applyModifiers(modifier);
		
		List<Job> jobs = building.getJobs();
		List<Resource> actual = new ArrayList<>();
		
		for(Job j: jobs) {
			actual.addAll(j.getResources());
		}
		
		checkResorcesMatch(actual, expected);
		
	}
	
	@Test
	void key() {
		
	}
	
	@Test
	void equals() {
		EqualsVerifier.forClass(Building.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
	
	void checkResorcesMatch(List<Resource> resources, List<String> expected) {
		
		assertThat(resources.size(), equalTo(expected.size()));
		
		List<String> actual = new ArrayList<>();
		
		for(Resource r: resources) {
			r = r.zip();
			actual.add(r.getId() + " " + r.getAmount());
		}
			
		assertThat(actual.toArray(new String[0]), arrayContainingInAnyOrder(expected.toArray(new String[0])));
		
	}
	
}
