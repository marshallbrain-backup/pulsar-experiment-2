package main.java.com.brain.ion.graphics.vectors;

import java.util.ArrayList;
import java.util.List;

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
	
	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	
	@XmlAttribute
	private String type;
	@XmlAttribute
	private String id;
	
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
		
		vectors = new ArrayList<>();
		
		for(Vector v: clone.vectors) {
			vectors.add(v.copyVector());
		}
		
	}

	/**
	 * @return A list of the vectors in the group
	 */
	public List<Vector> getVectors(){
		
		List<Vector> list = new ArrayList<>();
		
		for(Vector v: vectors) {
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
	
}
