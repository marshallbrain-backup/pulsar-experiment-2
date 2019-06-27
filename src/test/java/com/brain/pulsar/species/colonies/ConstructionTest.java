package com.brain.pulsar.species.colonies;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.pulsar.xml.elements.BuildTime;

class ConstructionTest {
	
	private ConstructionCoordinator cc;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}
	
	@BeforeEach
	void setUp() throws Exception {
		
		cc = new ConstructionCoordinator();
		
	}
	
	@Test
	void creation() {
		
		assertNotNull(cc);
	}
	
	@Test
	void build() {
		
		BuildTime b = new BuildTime(10);
		ConstructibleTest c = new ConstructibleTest(b);
		
		cc.add(c);
		cc.tick();
		
		assertTrue("Amount is 1", c.getAmount() == 1);
	}
	
}

class ConstructibleTest implements Constructible {
	
	private int amount;
	
	private BuildTime buildTime;
	
	public ConstructibleTest(BuildTime buildTime) {
		this.buildTime = new BuildTime(buildTime);
	}
	
	public int getAmount() {
		return amount;
	}

	@Override
	public int getBuildTime() {
		
		return buildTime.getTime();
	}

	@Override
	public void build() {
		amount++;
	}
	
}
