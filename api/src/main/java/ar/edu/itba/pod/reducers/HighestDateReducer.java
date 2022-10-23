package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.time.LocalDateTime;

public class HighestDateReducer implements ReducerFactory<String, Pair<Integer, LocalDateTime>, Pair<Integer, LocalDateTime>> {

    @Override
    public Reducer<Pair<Integer, LocalDateTime>, Pair<Integer, LocalDateTime>> newReducer(String s) {
        return new HighestDateDataReducer();
    }

    private static class HighestDateDataReducer extends Reducer<Pair<Integer, LocalDateTime>, Pair<Integer, LocalDateTime>> {
        private Integer dateCount;
        private LocalDateTime date;

        @Override
        public void beginReduce() {
            dateCount = 0;
            date = null;
        }

        @Override
        public void reduce(Pair<Integer, LocalDateTime> pair) {
            if (pair.getKey() > dateCount) {
                dateCount = pair.getKey();
                date = pair.getValue();
            } else if (pair.getKey().equals(dateCount)) {
                if (date == null || pair.getValue().isAfter(date)) {
                    dateCount = pair.getKey();
                    date = pair.getValue();
                }
            }
        }

        @Override
        public Pair<Integer, LocalDateTime> finalizeReduce() {
            return new Pair<>(dateCount, date);
        }
    }
}
