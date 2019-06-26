package com.brain.pulsar.other;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brain.pulsar.xml.elements.Modifier;

public interface ResourceManager {
	
	default boolean modifierMatcher(String chain, String pattern, boolean match) {
		
		if(!match) {
			
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(chain);
			boolean f = m.find();
			
			if(f) {
				return true;
			}
			
		}
		
		return false;
		
	}

	String getType();
	String getName();
	default String getKey() {
		
		return getType() + ((!getName().isEmpty())? ":" + getName(): "");
	}
	
	List<Resource> getResources();

	void applyModifiers(String chain, boolean match, Modifier modifier);
	default void applyModifiers(Modifier... modifiers) {
		for(Modifier mod: modifiers) {
			applyModifiers("", false, mod);
		}
	}
	
}
