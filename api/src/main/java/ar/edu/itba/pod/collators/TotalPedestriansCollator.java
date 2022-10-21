package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TotalPedestriansCollator implements Collator<Map.Entry<String,Long>, Collection<Pair<String,Long>>> {
    @Override
    public Collection<Pair<String, Long>> collate(Iterable<Map.Entry<String, Long>> values) {
        return StreamSupport.stream(values.spliterator(),false).sorted(
                (o1,o2) -> {
                    int aux = o2.getValue().compareTo(o1.getValue());
                    if(aux != 0){
                        return aux;
                    }
                    return o1.getKey().compareTo(o2.getKey());
                }
        ).map((o) -> new Pair<>(o.getKey(),o.getValue())).collect(Collectors.toList());
    }
}
