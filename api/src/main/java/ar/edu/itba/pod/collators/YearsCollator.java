package ar.edu.itba.pod.collators;

import com.hazelcast.mapreduce.Collator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class YearsCollator implements Collator<Map.Entry<Integer, List<Long>>, Collection<String>> {
    @Override
    public Collection<String> collate(Iterable<Map.Entry<Integer, List<Long>>> values) {
        return StreamSupport.stream(values.spliterator(),false).sorted(
                (o1,o2) -> o2.getKey().compareTo(o1.getKey())
        ).map((o) -> o.getKey() + ";" + o.getValue().get(0) + ";" + o.getValue().get(1) + ";" + o.getValue().get(2)).collect(Collectors.toList());
    }
}
