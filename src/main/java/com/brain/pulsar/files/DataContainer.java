package main.java.com.brain.pulsar.files;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pulsar")
public class DataContainer {
	
	@XmlAnyElement(lax = true)
	private List<Object> data;
	
}
