package ar.edu.itba.pod.predicates;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.KeyPredicate;

import java.util.Collection;

public class SensorCountPredicate<K> implements KeyPredicate<K>, HazelcastInstanceAware {
    private final String colName;
    private transient Collection<K> collection;

    public SensorCountPredicate(String colName) {
        this.colName = colName;
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.collection = hazelcastInstance.getSet(colName);
    }

    @Override
    public boolean evaluate(K k) {
        System.out.println(collection.size());
        return k != null && collection.contains(k);
    }
}
