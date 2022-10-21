package ar.edu.itba.pod.client.queries;

import com.hazelcast.core.ISet;
import com.hazelcast.core.MultiMap;
import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;

import java.util.stream.Stream;

public class QueryUtils {
    public static void loadCsv(final Stream<Sensor> sensorStream, final Stream<Reading> readingStream, ISet<String> sensorsSet, MultiMap<String, Reading> readingsMap){
        sensorStream.forEach(sensor -> sensorsSet.add(sensor.getDescription()));
        readingStream.forEach(reading -> readingsMap.put(reading.getSensorName(), reading));
    }
}
