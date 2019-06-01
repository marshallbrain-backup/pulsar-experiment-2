package com.brain.pulsar.other;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import com.brain.pulsar.universe.Body;
import com.brain.pulsar.xml.types.BodyType;

public enum ScopeType {
	
	BODY_TYPE(BodyType.class),
	BODY(Body.class, setOf(BODY_TYPE));
	
	private Class<?> scopeClass;
	private Set<ScopeType> inheritedScopes;

    private ScopeType(Class<?> v, Set<ScopeType> requirements) {
    	this(v);
        this.inheritedScopes = requirements;
    }

    private ScopeType(Class<?> v) {
    	scopeClass = v;
    }
    
    public Class<?> getScopeClass(){
    	return scopeClass;
    }

    private static Set<ScopeType> setOf(ScopeType... values) {
        return new HashSet<>(Arrays.asList(values));
    }

    static {
        for (ScopeType v : values()) {
            if (v.inheritedScopes == null) {
                v.inheritedScopes = EnumSet.noneOf(ScopeType.class);
            } else {
                v.inheritedScopes = EnumSet.copyOf(v.inheritedScopes);
            }
        }
    }
    
    
	
}
