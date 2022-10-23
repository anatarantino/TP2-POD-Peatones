package ar.edu.itba.pod.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class TotalMillionsPerSensorReducer implements ReducerFactory<String, Integer, Integer> {
    @Override
    public Reducer<Integer, Integer> newReducer(String s) {
        return new TotalMillionsData();
    }

    private static class TotalMillionsData extends Reducer<Integer, Integer> {
        private Integer totalPedestrians;

        @Override
        public void beginReduce() {
            totalPedestrians = 0;
        }

        @Override
        public void reduce(Integer count) {
            totalPedestrians+=count;
        }

        @Override
        public Integer finalizeReduce() {
            return totalPedestrians - totalPedestrians % 1000000;
        }
    }
}
