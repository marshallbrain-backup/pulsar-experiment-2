/**
 * 
 */
/**
 * @author Marshall Brain
 *
 */
@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value=StringAdapter.class, type=String.class),
})
package com.brain.pulsar.conditions;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import com.brain.ion.xml.StringAdapter;
