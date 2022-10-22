package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class YearsCollator implements Collator<Map.Entry<Integer,Long[]>, Collection<Pair<Integer,String>>> {
    @Override
    public Collection<Pair<Integer, String>> collate(Iterable<Map.Entry<Integer, Long[]>> values) {
        return StreamSupport.stream(values.spliterator(),false).sorted(
                (o1,o2) -> {
                    int aux = o2.getKey().compareTo(o1.getKey());
                    if(aux != 0){
                        return aux;
                    }
                    return o1.getKey().compareTo(o2.getKey());
                }
        ).map((o) -> new Pair<>(o.getKey(),o.getValue()[0] + ";" + o.getValue()[1] + ";" + o.getValue()[2] )).collect(Collectors.toList());
    }
}
