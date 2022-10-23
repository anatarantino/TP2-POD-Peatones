package ar.edu.itba.pod.collators;

import com.hazelcast.mapreduce.Collator;

import java.util.*;
import java.util.stream.StreamSupport;

public class PairsByMillionsCollator implements Collator<Map.Entry<String,Integer>, Collection<String>> {
    @Override
    public Collection<String> collate(Iterable<Map.Entry<String, Integer>> iterable) {
        final Map<Integer, SortedSet<String>> sensorsByMillions = new TreeMap<>(Collections.reverseOrder());
        StreamSupport.stream(iterable.spliterator(),false)
                .forEach(entry -> {
                    if(entry.getValue() >= 1000000){
                        sensorsByMillions.putIfAbsent(entry.getValue(), new TreeSet<>(Comparator.naturalOrder()));
                        sensorsByMillions.get(entry.getValue()).add(entry.getKey());
                    }
                });
        final List<String> aux = new LinkedList<>();
        for(Integer key : sensorsByMillions.keySet()){
            SortedSet<String> sensors = sensorsByMillions.get(key);
            while(!sensors.isEmpty()){
                String A = sensors.first();
                sensors.remove(A);
                sensors.forEach(B -> aux.add(key + ";" + A + ";" + B));
            }
        }
        return aux;
    }
}
