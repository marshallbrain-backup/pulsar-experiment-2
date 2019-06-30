package com.brain.pulsar.resources;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.pulsar.empires.colonies.District;
import com.brain.pulsar.empires.colonies.types.BuildingType;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.universe.types.BodyType;

import nl.jqno.equalsverifier.EqualsVerifier;


class ResourceTest {
	
	Resource r;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	}
	
	@BeforeEach
	void setUp() throws Exception {
		r = new Resource(new ResourceBase("test", 1), "base");
	}
	
	@Test
	void id() {
		
		assertThat(r.getId(), equalTo("test"));
		
	}
	
	@Test
	void type() {
		
		assertThat(r.getType(), equalTo("base"));
		
	}
	
	@Test
	void amount() {
		
		assertThat(r.getAmount(), equalTo(1.0));
		
	}
	
	@Test
	void combine() {
		
		r = r.combine(new Resource(new ResourceBase("test", 1), "base 2"));
		assertThat(r, equalTo(new Resource(new ResourceBase("test", 2), "").zip()));
		
	}
	
	@Test
	void modifier() {
		
		Modifier modifier = new Modifier("test", "base", "test", 1);
		
		r.applyModifier(modifier);
		r = r.zip();
		assertThat(r, equalTo(new Resource(new ResourceBase("test", 2), "").zip()));
		
	}
	
	@Test
	void equals() {
		EqualsVerifier.forClass(Resource.class).verify();
	}
	
}
