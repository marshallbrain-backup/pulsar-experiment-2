package com.brain.ion.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.brain.ion.graphics.vectors.VectorGroup;

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
		
		if(vectors != null) {
			return new ArrayList<>(vectors);
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * Gets the group of VectorGroups that have a key matching the regex String.
	 * 
	 * @param groups
	 *            The map of VectorGroups with there corresponding ids as keys
	 * @param regex
	 *            The expression to match to
	 * @param cut
	 *            Where to cut off the group name from the key
	 * @return A map with the matching VectorGroups
	 */
	public static Map<String, VectorGroup> getVectorGroups(Map<String, VectorGroup> groups, String regex, boolean cut) {
		
		Map<String, VectorGroup> newGroup = new HashMap<>();
		Map<String, VectorGroup> vg = new HashMap<>(groups);
		
		Pattern regexPatern = Pattern.compile(regex);
		
		for (Entry<String, VectorGroup> e : vg.entrySet()) {
			if (regexPatern.matcher(e.getKey()).find()) {
				
				String key = e.getKey();
				
				if (cut) {
					key = key.substring(key.indexOf('.') + 1);
				}
				
				newGroup.put(key, new VectorGroup(e.getValue()));
				
			}
		}
		
		return newGroup;
		
	}
	
}
