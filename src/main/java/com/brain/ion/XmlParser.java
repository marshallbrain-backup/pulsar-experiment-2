package main.java.com.brain.ion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlParser {

	public static Object getXml(File fileName, Class<?>[] classList) {
		
		Object xml = null;
		
		try {
			xml = readXmlFile(new FileReader(fileName), classList);
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
		
		return xml;
		
	}
	
	public static Object getXml(String xmlString, Class<?>[] classList) {
		
		Object xml = null;
		
		try {
			xml = readXmlFile(new StringReader(xmlString), classList);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return xml;
		
	}
	
	private static Object readXmlFile(Reader reader, Class<?>[] classList) throws JAXBException {
			
		JAXBContext context = JAXBContext.newInstance(classList);
		Unmarshaller um = context.createUnmarshaller();
		
		return um.unmarshal(reader);
		
	}

}
