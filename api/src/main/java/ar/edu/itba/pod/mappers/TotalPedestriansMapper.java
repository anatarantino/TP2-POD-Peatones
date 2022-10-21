package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.entities.Reading;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class TotalPedestriansMapper implements Mapper<String, Reading, String, Integer> {
    @Override
    public void map(String sensorName, Reading reading, Context<String, Integer> context) {
        context.emit(sensorName,reading.getPedestrians());
    }
}
