package test.java.com.brain.pulsar;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import main.java.com.brain.pulsar.files.DataContainer;
import main.java.com.brain.pulsar.universe.BodyType;
import main.java.com.brain.pulsar.universe.StarSystemType;

public class JUnitTestSetup {
	
	public static DataContainer xmlReader(List<String> xmlFiles) {
		
		Class<?>[] dataTypes = new Class<?>[]{
			DataContainer.class, 
			BodyType.class, StarSystemType.class
			};
		
	    try {
	    	
	    	JAXBContext jaxbContext = JAXBContext.newInstance(dataTypes);       
		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		    
		    List<DataContainer> dList = new ArrayList<>();
		    
		    for(String xml: xmlFiles) {
		    	dList.add((DataContainer) jaxbUnmarshaller.unmarshal(new StringReader(xml)));
		    }
		    
		    return new DataContainer(dList);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	    
		return null;
		
	}
	
}
