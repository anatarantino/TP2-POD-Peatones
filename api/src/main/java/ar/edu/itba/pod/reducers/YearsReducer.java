package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.List;


public class YearsReducer implements ReducerFactory<Integer, Pair<Boolean,Integer>, Long[]> {
    private static final Boolean IS_WEEKDAY = Boolean.TRUE;

    @Override
    public Reducer<Pair<Boolean, Integer>, Long[]> newReducer(Integer integer) {
        return new YearsDataReducer();
    }

    private static class YearsDataReducer extends Reducer<Pair<Boolean,Integer>,Long[]> {
        private Long totalWeekdays, totalWeekends,totalCount;

        @Override
        public void beginReduce() {
            totalCount = 0L;
            totalWeekdays = 0L;
            totalWeekends = 0L;
        }

        @Override
        public void reduce(Pair<Boolean,Integer> pair) {
            if(pair.getKey().equals(IS_WEEKDAY)){
                totalWeekdays += pair.getValue();
            }else{
                totalWeekends += pair.getValue();
            }
            totalCount += pair.getValue();

        }

        @Override
        public Long[] finalizeReduce() {
            Long[] result = new Long[3];
            result[0] = totalWeekdays;
            result[1] = totalWeekends;
            result[2] = totalCount;
            return result;
        }
    }
}
