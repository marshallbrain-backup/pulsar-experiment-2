package main.java.com.brain.ion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlParser {

	public static Object getXml(File fileName, Class<?>[] classList) {
		return readXmlFile(fileName, classList);
	}
	
	private static Object readXmlFile(File fileName, Class<?>[] classList) {
		
		try(FileReader reader = new FileReader(fileName)) {
			
			JAXBContext context = JAXBContext.newInstance(classList);
			Unmarshaller um = context.createUnmarshaller();
			
			return um.unmarshal(reader);
			
		} catch (IOException | JAXBException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
