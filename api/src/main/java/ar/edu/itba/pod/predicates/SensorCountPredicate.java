package ar.edu.itba.pod.predicates;

import com.hazelcast.mapreduce.KeyPredicate;
import com.hazelcast.query.Predicates;

import java.util.Collection;

public class SensorCountPredicate<Sensor> implements KeyPredicate<Sensor> {
    private Collection<Sensor> collection;

    public SensorCountPredicate(Collection<Sensor> collection) {
        this.collection = collection;
    }

    @Override
    public boolean evaluate(Sensor sensor) {
        return sensor != null && collection.contains(sensor);
    }
}
