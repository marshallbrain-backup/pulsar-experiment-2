package main.java.com.brain.ion.graphics;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

import main.java.com.brain.ion.graphics.vectors.Vector;
import main.java.com.brain.ion.graphics.vectors.VectorGroup;

public class IonXmlRoot {
	
	@XmlElement
	private List<VectorGroup> vectors;
	
	public List<VectorGroup> getVectorGroups() {
		return vectors;
	}
	
}
