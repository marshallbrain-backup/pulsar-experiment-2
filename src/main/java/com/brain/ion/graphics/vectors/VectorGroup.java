package com.brain.ion.graphics.vectors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a group of vectors that should be rendered together
 * 
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "vector_layer")
public class VectorGroup {
	
	@XmlAttribute
	private int x;
	@XmlAttribute
	private int y;
	
	@XmlAttribute
	private String type;
	@XmlAttribute
	private String id;
	
	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	private Map<String, Vector> mappedVectors;
	
	/**
	 * Base constructor
	 */
	public VectorGroup() {
	}
	
	/**
	 * Clones the {@link VectorGroup}
	 * 
	 * @param clone
	 *            The {@link VectorGroup} to clone
	 */
	public VectorGroup(VectorGroup clone) {
		
		type = clone.type;
		id = clone.id;
		x = clone.x;
		y = clone.y;
		
		vectors = new ArrayList<>();
		
		if(clone.vectors != null) {
			
			for (Object o : clone.vectors) {
				try {
					Vector v = (Vector) o;
					vectors.add(v.copyVector());
				} catch(ClassCastException e) {
				}
			}
			
		}
		
	}
	
	/**
	 * @return A list of the vectors in the group
	 */
	public List<Vector> getVectors() {
		
		List<Vector> list = new ArrayList<>();
		
		for (Vector v : vectors) {
			list.add(v.copyVector());
		}
		
		return list;
		
	}
	
	/**
	 * @return Gets the path of the VectorGroup, used to determine what VectorGroup
	 *         goes to what object with out the need for folders
	 */
	public String getPath() {
		
		return type + "." + id;
	}
	
	public Point getOrigin() {
		return new Point(x, y);
	}

	public Vector getVectorById(String id) {
		
		if(mappedVectors == null) {
			
			mappedVectors = new HashMap<>();
			for(Vector v: vectors) {
				mappedVectors.put(v.getId(), v);
			}
			
		}
		
		return mappedVectors.get(id);
	}
	
}
