package main.java.com.brain.pulsar.files;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The top level class for data from an xml file primarily in the common folder.
 * 
 * @author Marshall Brain
 *
 */
@XmlRootElement(name = "pulsar")
public class DataContainer {
	
	@XmlAnyElement(lax = true)
	private List<Object> data;
	
	/**
	 * Empty constructor
	 */
	public DataContainer() {
	
	}
	
	/**
	 * Merges a list of DataContainers into a single one.
	 * 
	 * @param dataList
	 *            The list to be merged
	 */
	public DataContainer(List<DataContainer> dataList) {
		
		data = new ArrayList<>();
		
		for (DataContainer dc : dataList) {
			data.addAll(dc.data);
		}
		
	}
	
	/**
	 * Gets a list of classes that are an instance of the given class.
	 * 
	 * @param match
	 *            The target class
	 * @return A list of the target class
	 */
	public <T> List<T> getMatchData(Class<T> match) {
		
		List<T> matchingList = new ArrayList<>();
		
		for (Object o : data) {
			if (match.isInstance(o)) {
				matchingList.add(match.cast(o));
			}
		}
		
		return matchingList;
		
	}
	
}
