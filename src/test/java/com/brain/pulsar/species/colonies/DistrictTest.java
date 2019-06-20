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
import com.brain.pulsar.other.Scope;
import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.elements.Resource;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.DistrictType;
import com.brain.pulsar.xml.types.StarSystemType;


class DistrictTest {
	
	private static DataContainer data;
	private District district;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, DistrictType.class };
		
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
		
	}
	
	@Test
	void creation() {
		
		assertNotNull(district);
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(DISTRICTS);
		list.add(BODYS);
		
		return list;
		
	}
	
	private static final String BODYS = "";
	private static final String DISTRICTS = "";
	
}
