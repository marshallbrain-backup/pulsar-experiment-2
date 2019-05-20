package test.java.com.brain.pulsar.universe;

import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.brain.pulsar.data.Distance;
import main.java.com.brain.pulsar.data.DistanceType;
import main.java.com.brain.pulsar.files.DataContainer;
import main.java.com.brain.pulsar.universe.Body;
import main.java.com.brain.pulsar.universe.BodyType;
import test.java.com.brain.pulsar.JUnitTestSetup;


class BodyTest {
	
	private static DataContainer data;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		data = JUnitTestSetup.xmlReader(getXmlFiles());
		
	}
	
	@BeforeEach
	void setUp() {
		
		
		
	}
	
	@Test
	void starTest() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		
		Body core = new Body();
		Body star = new Body(typeBodys.get(0), core);
		
		assertThat("Radius range check", 
				star.getRadius().getDistance(), 
				allOf(
						greaterThanOrEqualTo(new Distance(3, DistanceType.METER).getDistance()), 
						lessThanOrEqualTo(new Distance(6, DistanceType.METER).getDistance())
						)
				);
		
		assertThat("Temperature range check", star.getTemperature(), allOf(greaterThanOrEqualTo(10000L), lessThanOrEqualTo(20000L)));
		
	}
	
	@Test
	void planetTest() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		
		Body core = new Body();
		Body star = new Body(typeBodys.get(0), core);
		
		assertThat("Radius range check", 
				star.getRadius().getDistance(), 
				allOf(
						greaterThanOrEqualTo(new Distance(3, DistanceType.METER).getDistance()), 
						lessThanOrEqualTo(new Distance(6, DistanceType.METER).getDistance())
						)
				);
		
		assertThat("Temperature range check", star.getTemperature(), allOf(greaterThanOrEqualTo(10000L), lessThanOrEqualTo(20000L)));
		assertThat("Angle range check", star.getAngle(), allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(360.0)));
		
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(stars);
		
		return list;
		
	}
	
	private static String stars = 
			"<pulsar>\r\n" + 
			"\r\n" + 
			"	<body>\r\n" + 
			"		<name>sc_b_star ueauoueo</name>\r\n" + 
			"		<radius min=\"3\" max=\"6\"/>\r\n" + 
			"		<temp_set min=\"10e3\" max=\"20e3\"/>\r\n" + 
			"		<colonizable>false</colonizable>\r\n" + 
			"	</body>\r\n" + 
			"</pulsar>";
	
}
