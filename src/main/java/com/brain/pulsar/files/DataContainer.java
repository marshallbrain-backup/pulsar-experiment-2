package main.java.com.brain.pulsar.files;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pulsar")
public class DataContainer {

	@XmlAnyElement(lax = true)
	private List<Object> data;
	
	public DataContainer() {
	}
	
	public DataContainer(List<DataContainer> dataList) {
		
		data = new ArrayList<>();
		
		for(DataContainer dc: dataList) {
			data.addAll(dc.data);
		}
		
	}
	
	public <T> List<T> getMatchData(Class<T> match) {
		
		List<T> matchingList = new ArrayList<>();
		
		for(Object o: data) {
			if(match.isInstance(o)) {
				matchingList.add(match.cast(o));
			}
		}
		
		return matchingList;
		
	}
	
}
