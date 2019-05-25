package main.java.com.brain.ion.graphics.vectors;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.com.brain.ion.graphics.VectorGraphics;

@XmlRootElement(name = "vector_layer")
public class VectorGroup {
	
	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	
	@XmlAttribute
	private String type;
	@XmlAttribute
	private String id;
	
	public VectorGroup() {
		
	}
	
	public VectorGroup(VectorGroup clone) {
		
		type = clone.type;
		id = clone.id;
		
		vectors = new ArrayList<>();
		
		for(Vector v: clone.vectors) {
			vectors.add(v.clone());
		}
		
	}

	public List<Vector> getVectors(){
		
		List<Vector> list = new ArrayList<>();
		
		for(Vector v: vectors) {
			list.add(v.clone());
		}
		
		return list;
		
	}

	public String getPath() {
		return type + "." + id;
	}
	
}
