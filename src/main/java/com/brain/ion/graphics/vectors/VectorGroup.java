package main.java.com.brain.ion.graphics.vectors;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vector_layer")
public class VectorGroup {
	
	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	
}
