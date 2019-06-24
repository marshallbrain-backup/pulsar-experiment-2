package com.brain.ion.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Parses an xml file or string into an object utilizing the list of classes.
 * 
 * @author Marshall Brain
 *
 */
public class XmlParser {
	
	/**
	 * Parses an xml file.
	 * 
	 * @param fileName
	 *            The file to parse
	 * @param classList
	 *            The list of classes to put the data from the file
	 * @return The object that contains the data from the xml file
	 */
	public static Object getXml(File fileName, Class<?>[] classList) {
		return getXml(fileName, classList, null);
	}
	
	public static Object getXml(File fileName, Class<?>[] classList, XmlAdapter<?, ?>[] adapterList) {
		
		Object xml = null;
		
		try {
			xml = readXmlFile(new FileReader(fileName), classList, adapterList);
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
		
		return xml;
		
	}
	
	/**
	 * Parses an xml string.
	 * 
	 * @param xmlString
	 *            The string to parse
	 * @param classList
	 *            The list of classes to put the data from the file
	 * @return The object that contains the data from the xml file
	 */
	public static Object getXml(String xmlString, Class<?>[] classList) {
		return getXml(xmlString, classList, null);
	}
	
	public static Object getXml(String xmlString, Class<?>[] classList, XmlAdapter<?, ?>[] adapterList) {
		
		Object xml = null;
		
		try {
			xml = readXmlFile(new StringReader(xmlString), classList, adapterList);
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
		
		return xml;
		
	}
	
	/**
	 * Generates the object containing the data
	 * 
	 * @param reader
	 *            The reader that contains the character stream of the xml data
	 * @param classList
	 *            The list of classes to put the data
	 * @return The object that contains the data
	 * @throws JAXBException
	 * @throws IOException 
	 */
	private static Object readXmlFile(Reader reader, Class<?>[] classList, XmlAdapter<?, ?>[] adapterList) throws JAXBException, IOException {
		
		JAXBContext context = JAXBContext.newInstance(classList);
		Unmarshaller um = context.createUnmarshaller();
		
		if(adapterList != null) {
			for(XmlAdapter<?, ?> a: adapterList) {
				um.setAdapter(a);
			}
		}
		
		if(reader.ready()) {
			return um.unmarshal(reader);
		}
		
		return null;
		
	}
	
	private XmlParser() {
	
	}
	
}
