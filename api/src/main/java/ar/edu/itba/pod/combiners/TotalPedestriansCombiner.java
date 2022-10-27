package ar.edu.itba.pod.combiners;

import ar.edu.itba.pod.entities.Reading;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class TotalPedestriansCombiner implements CombinerFactory<String, Integer, Integer> {
    @Override
    public Combiner<Integer, Integer> newCombiner(String s) {
        return new TotalPedestriansCombinerData();
    }

    private static class TotalPedestriansCombinerData extends Combiner<Integer,Integer> {
        private int count = 0;

        @Override
        public void reset() {
            this.count = 0;
        }

        @Override
        public void combine(Integer value) {
            this.count += value;
        }

        @Override
        public Integer finalizeChunk() {
            return this.count;
        }
    }
}
