package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class YearsMapper implements Mapper<Integer, Reading,Integer,Pair<Boolean,Integer>> {
    @Override
    public void map(Integer year, Reading reading, Context<Integer,Pair<Boolean,Integer>> context) {
        context.emit(year,new Pair<>(isWeekday(reading.getWeekday()),reading.getPedestrians()));
    }

    private static boolean isWeekday(String day){
        return !day.equals("Saturday") && !day.equals("Sunday");
    }
}
