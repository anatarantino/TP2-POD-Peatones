package ar.edu.itba.pod.collators;

import com.hazelcast.mapreduce.Collator;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class YearsCollator implements Collator<Map.Entry<Integer,String>, Collection<String>> {
    @Override
    public Collection<String> collate(Iterable<Map.Entry<Integer, String>> values) {
        return StreamSupport.stream(values.spliterator(),false).sorted(
                (o1,o2) -> o2.getKey().compareTo(o1.getKey())
        ).map((o) -> o.getKey() + ";" + o.getValue()).collect(Collectors.toList());
    }
}
