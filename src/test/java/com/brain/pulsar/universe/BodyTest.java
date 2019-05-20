package test.java.com.brain.pulsar.universe;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void oneStarInit() {
		
		List<BodyType> typeBodys = data.getMatchData(BodyType.class);
		
		Body core = new Body();
		Body star = new Body(typeBodys.get(0), core);
		
		assertThat(star.getRadius().getDistance(), allOf(greaterThanOrEqualTo(3L), lessThanOrEqualTo(6L)));
		
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
