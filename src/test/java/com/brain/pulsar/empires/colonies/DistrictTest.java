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
import com.brain.pulsar.empires.colonies.jobs.JobBase;
import com.brain.pulsar.empires.colonies.types.DistrictType;
import com.brain.pulsar.empires.colonies.types.JobType;
import com.brain.pulsar.resources.Modifier;
import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.universe.types.BodyType;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.adapters.JobAdapter;

class DistrictTest {
	
	static DataContainer data;
	static JobAdapter jobAdapter;
	
	District district;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		jobAdapter = new JobAdapter();
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, DistrictType.class, JobType.class, Modifier.class};		
		XmlAdapter<?, ?>[] adapterList = new XmlAdapter<?, ?>[] {jobAdapter};
		
		String[] resourceList = new String[] {
				"districts.xml",
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
		
		district = new District();
	
	}
	
	@Test
	void creation() {
		
		assertThat(district, notNullValue());
		
	}
	
	@Test
	void retool() {
		
		setDistrictType();
		
		assertThat(district.getDistrictType(), equalToObject(data.getMatchData(DistrictType.class).get(0)));
		
	}
	
	@Test
	void build() {
		
		setDistrictType();
		district.build();
		
		assertThat(district.getAmount(), equalTo(1));
		
	}
	
	@Test
	void resource() {
		
		setDistrictType();
		district.build();
		
		List<String> expected = new ArrayList<>();
		expected.add("energy -1.0");
		expected.add("energy 5.0");
		expected.add("housing 5.0");
		
		List<Resource> resources = district.getResources();
		
		checkResorcesMatch(resources, expected);
		
	}
	
	@Test
	void key() {
		
		assertThat(district.getKey(), equalTo("district:null"));
		
		setDistrictType();
		
		assertThat(district.getKey(), equalTo("district:district_city"));
		
	}
	
	void setDistrictType() {
		
		BodyType bodyType = data.getMatchData(BodyType.class).get(0);
		DistrictType districtType = data.getMatchData(DistrictType.class).get(0);

		Body body = new Body(bodyType, null);
		
		assertThat(district.setRetoolingType(districtType, body), is(true));
		
		district.build();
		
	}
	
	void checkResorcesMatch(List<Resource> resources, List<String> expected) {
		
		assertThat(resources.size(), equalTo(expected.size()));
		
		List<String> actual = new ArrayList<>();
		
		for(Resource r: resources) {
			actual.add(r.getId() + " " + r.getAmount());
		}
			
		assertThat(actual.toArray(new String[0]), arrayContainingInAnyOrder(expected.toArray(new String[0])));
		
	}
	
}
