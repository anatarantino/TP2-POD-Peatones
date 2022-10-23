package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.time.LocalDateTime;

public class HighestDateMapper implements Mapper<String, Reading, String, Pair<Integer, LocalDateTime>> {
    @Override
    public void map(String sensor, Reading reading, Context<String, Pair<Integer, LocalDateTime>> context) {
        context.emit(sensor, new Pair<>(reading.getPedestrians(), reading.getLocalDate()));
    }

}
