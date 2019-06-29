package com.brain.pulsar.empires.colonies;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.pulsar.info_containers.TimeEntry;


class ConstrutionTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	}
	
	@BeforeEach
	void setUp() throws Exception {
	
	}
	
	@Test
	void add() {
		
		ConstructionCoordinator cc = new ConstructionCoordinator();
		Construction c = new Construction();
		
		cc.add(c);
		
		assertThat(cc.getConstructionList().get(0).getTarget(), equalTo(c));
		
	}
	
	@Test
	void tick() {
		
		ConstructionCoordinator cc = new ConstructionCoordinator();
		Construction c = new Construction();
		
		cc.add(c);
		cc.tick(new TimeEntry(10));
		
		assertThat(c.getAmount(), equalTo(1));
		
	}
	
	@Test
	void flush() {
		
		ConstructionCoordinator cc = new ConstructionCoordinator();
		Construction c = new Construction();
		
		cc.add(c);
		cc.flush();
		
		assertThat(c.getAmount(), equalTo(1));
		
	}
	
}

class Construction implements Constructible {
	
	private int amount;
	
	public int getAmount() {
		return amount;
	}

	@Override
	public int getBuildTime() {
		return 10;
	}

	@Override
	public void build() {
		amount++;
	}
	
}
