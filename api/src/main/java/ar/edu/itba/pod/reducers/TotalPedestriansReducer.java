package ar.edu.itba.pod.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class TotalPedestriansReducer implements ReducerFactory<String,Integer,Long> {
    @Override
    public Reducer<Integer, Long> newReducer(String sensorName) {
        return new TotalReadingReducer();
    }

    private static class TotalReadingReducer extends Reducer<Integer, Long> {
        private Long totalPedestrians;

        @Override
        public void beginReduce() {
            totalPedestrians = 0L;
        }

        @Override
        public void reduce(Integer count) {
            totalPedestrians += count;
        }

        @Override
        public Long finalizeReduce() {
            return totalPedestrians;
        }
    }
}
