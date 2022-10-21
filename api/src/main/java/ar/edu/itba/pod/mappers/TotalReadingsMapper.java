package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.entities.Reading;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class TotalReadingsMapper implements Mapper<Integer, Reading, Integer, Integer> {
    @Override
    public void map(Integer sensorId, Reading reading, Context<Integer, Integer> context) {
        context.emit(sensorId,1);
    }
}
