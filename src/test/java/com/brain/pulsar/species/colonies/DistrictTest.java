package com.brain.pulsar.species.colonies;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brain.ion.xml.XmlParser;
import com.brain.pulsar.xml.DataContainer;
import com.brain.pulsar.xml.types.BodyType;
import com.brain.pulsar.xml.types.StarSystemType;


class DistrictTest {
	
	private static DataContainer data;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Class<?>[] dataTypes = new Class<?>[] { DataContainer.class, BodyType.class, StarSystemType.class };
		
		List<DataContainer> uncompresed = new ArrayList<>();
		for (String xml : getXmlFiles()) {
			uncompresed.add((DataContainer) XmlParser.getXml(xml, dataTypes));
		}
		
		data = new DataContainer(uncompresed);
	
	}
	
	@BeforeEach
	void setUp() throws Exception {
	
	}
	
	@Test
	void test() {
		
		fail("Not yet implemented");
	}
	
	static List<String> getXmlFiles() {
		
		List<String> list = new ArrayList<>();
		
		list.add(DISTRICTS);
		
		return list;
		
	}
	
	private static final String DISTRICTS = 
			"<pulsar>\r\n" + 
			"	\r\n" + 
			"	<district>\r\n" + 
			"	\r\n" + 
			"			<base_buildtime>480</base_buildtime>\r\n" + 
			"			\r\n" + 
			"			<potential>\r\n" + 
			"				<tag>standard_city</tag>\r\n" + 
			"			</potential>\r\n" + 
			"			\r\n" + 
			"			<starting>\r\n" + 
			"				<tag>standard_city</tag>\r\n" + 
			"			</starting>\r\n" + 
			"			\r\n" + 
			"			<resources>\r\n" + 
			"			\r\n" + 
			"				<cost>\r\n" + 
			"					<resources type=\"mineral\">300</resources>\r\n" + 
			"				</cost>\r\n" + 
			"				<upkeep>\r\n" + 
			"					<resources type=\"energy\">1</resources>\r\n" + 
			"				</upkeep>\r\n" + 
			"				<production>\r\n" + 
			"					<resources type=\"housing\">5</resources>\r\n" + 
			"				</production>\r\n" + 
			"				\r\n" + 
			"			</resources>\r\n" + 
			"			\r\n" + 
			"	</district>\r\n" + 
			"	\r\n" + 
			"</pulsar>";
	
}
