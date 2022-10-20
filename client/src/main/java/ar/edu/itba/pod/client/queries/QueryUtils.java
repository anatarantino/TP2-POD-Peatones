package ar.edu.itba.pod.client.queries;

import com.hazelcast.core.ISet;
import com.hazelcast.core.MultiMap;
import entities.Reading;
import entities.Sensor;

import java.util.stream.Stream;

public class QueryUtils {
    public static void loadCsv(final Stream<Sensor> sensorStream, final Stream<Reading> readingStream, ISet<Integer> sensorsSet, MultiMap<Integer, Reading> readingsMap){
        sensorStream.forEach(sensor -> sensorsSet.add(sensor.getId()));
        readingStream.forEach(reading -> readingsMap.put(reading.getSensorId(), reading));
    }
}
