package com.brain.pulsar.empires.colonies.jobs;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.pulsar.empires.colonies.District;
import com.brain.pulsar.empires.colonies.types.JobType;
import com.brain.pulsar.resources.Modifier;
import com.brain.pulsar.resources.Resource;
import com.brain.pulsar.resources.ResourceBase;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


class JobTest {
	
	Job job;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	}
	
	@BeforeEach
	void setUp() throws Exception {
		
		List<ResourceBase> upkeep = new ArrayList<>();
		upkeep.add(new ResourceBase("test", 1));
		
		List<ResourceBase> production = new ArrayList<>();
		production.add(new ResourceBase("test", 2));
		
		JobType type = new JobType("test", upkeep, production);
		
		job = new JobBase(type, 1).createJobs().get(0);
		
	}
	
	@Test
	void creation() {
		
		assertThat(job, notNullValue());
	}
	
	@Test
	void name() {
		
		assertThat(job.getName(), equalTo("test"));
	}
	
	@Test
	void type() {
		
		assertThat(job.getType(), equalTo("worker"));
	}
	
	@Test
	void resources() {
		
		List<Resource> expected = new ArrayList<>();
		expected.add(new Resource(new ResourceBase("test", -1), "upkeep"));
		expected.add(new Resource(new ResourceBase("test", 2), "production"));
		
		assertThat(job.getResources().toArray(new Resource[0]), arrayContainingInAnyOrder(expected.toArray(new Resource[0])));
		
	}
	
	@Test
	void modifier() {
		
		Modifier modifier = new Modifier("test", ".*", "test", 1);
		
		job.applyModifiers(modifier);
		
		List<Resource> actual = new ArrayList<>();
		for(Resource r: job.getResources()) {
			actual.add(r.zip());
		}
		
		List<Resource> expected = new ArrayList<>();
		expected.add(new Resource(new ResourceBase("test", -2), "").zip());
		expected.add(new Resource(new ResourceBase("test", 4), "").zip());
		
		assertThat(actual.toArray(new Resource[0]), arrayContainingInAnyOrder(expected.toArray(new Resource[0])));
		
	}
	
	@Test
	void equals() {
		EqualsVerifier.forClass(Job.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
	
}
