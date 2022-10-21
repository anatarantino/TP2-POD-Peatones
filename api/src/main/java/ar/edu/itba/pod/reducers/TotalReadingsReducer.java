package ar.edu.itba.pod.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class TotalReadingsReducer implements ReducerFactory<Integer,Integer,Long> {
    @Override
    public Reducer<Integer, Long> newReducer(Integer sensorId) {
        return new TotalReadingReducer();
    }

    private static class TotalReadingReducer extends Reducer<Integer, Long> {
        private Long totalReadings;

        @Override
        public void beginReduce() {
            totalReadings = 0L;
        }

        @Override
        public void reduce(Integer count) {
            totalReadings += count;
        }

        @Override
        public Long finalizeReduce() {
            return totalReadings;
        }
    }
}
