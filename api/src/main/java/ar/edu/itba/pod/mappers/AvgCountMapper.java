package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class AvgCountMapper implements Mapper<String, Reading, String, Pair<Integer,Integer>> {
    @Override
    public void map(String sensor, Reading reading, Context<String, Pair<Integer, Integer>> context) {
        context.emit(sensor, new Pair<>(reading.getLocalDate().getMonthValue(), reading.getPedestrians()));
    }
}
