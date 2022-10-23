package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AvgCountReducer implements ReducerFactory<String, Pair<Integer,Integer>, Pair<Integer,Double>> {
    private static final int TOTAL_MONTHS = 12;
    private final Integer year;

    public AvgCountReducer(Integer year) {
        this.year = year;
    }

    @Override
    public Reducer<Pair<Integer, Integer>, Pair<Integer, Double>> newReducer(String s) {
        return new AvgCountDataReducer(year);
    }

    private static class AvgCountDataReducer extends Reducer<Pair<Integer,Integer>, Pair<Integer,Double>>{
        private Double[] monthsData;
        private Integer year;

        public AvgCountDataReducer(Integer year) {
            this.year = year;
        }

        @Override
        public void beginReduce() {
            monthsData = new Double[12];
            Arrays.fill(monthsData, (double) 0);
        }

        @Override
        public void reduce(Pair<Integer, Integer> pair) {
            monthsData[pair.getKey()-1] += pair.getValue();
        }

        @Override
        public Pair<Integer, Double> finalizeReduce() {
            int maxMonthIdx = 0;
            for(int i = 0 ; i < TOTAL_MONTHS; i++){
                monthsData[i] /= YearMonth.of(year, i+1).lengthOfMonth();
                if(monthsData[i].compareTo(monthsData[maxMonthIdx]) > 0){
                    maxMonthIdx = i;
                }
            }
            return new Pair<>(maxMonthIdx+1,monthsData[maxMonthIdx]);
        }

    }
}
