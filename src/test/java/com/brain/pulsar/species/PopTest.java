package com.brain.pulsar.species;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.pulsar.empires.colonies.jobs.Job;
import com.brain.pulsar.empires.colonies.types.JobType;


class PopTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	}
	
	@BeforeEach
	void setUp() throws Exception {
	
	}
	
	@Test
	void job() {
		
		Pop p = new Pop();
		p.setJob(new Job(new JobType("test", new ArrayList<>(), new ArrayList<>())));
		
		assertThat(p.isUnimployed(), equalTo(false));
		
	}
	
}
