package main.java.com.brain.ion.graphics;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.ion.graphics.vectors.VectorGroup;

/**
 * The top level class for data from an xml file that is used by the Ion engine.
 * 
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "ion")
public class IonXmlRoot {
	
	@XmlElement(name = "vector_layer")
	private List<VectorGroup> vectors;
	
	/**
	 * @return The list of VectorGroups
	 */
	public List<VectorGroup> getVectorGroups() {
		return new ArrayList<>(vectors);
	}
	
}
