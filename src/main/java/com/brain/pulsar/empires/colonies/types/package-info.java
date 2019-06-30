/**
 * 
 */
/**
 * @author Marshall Brain
 *
 */
@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value=StringAdapter.class, type=String.class),
    @XmlJavaTypeAdapter(value=JobAdapter.class, type=Job.class),
})
package com.brain.pulsar.empires.colonies.types;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import com.brain.ion.xml.StringAdapter;
import com.brain.pulsar.empires.colonies.jobs.Job;
import com.brain.pulsar.xml.adapters.JobAdapter;
