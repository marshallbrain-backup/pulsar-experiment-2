package main.java.com.brain.ion.graphics;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;

@XmlRootElement(name = "ion")
public class IonXmlRoot {
	
	@XmlElement(name = "vector_layer")
	private List<VectorGroup> vectors;
	
	public List<VectorGroup> getVectorGroups() {
		return vectors;
	}
	
}
