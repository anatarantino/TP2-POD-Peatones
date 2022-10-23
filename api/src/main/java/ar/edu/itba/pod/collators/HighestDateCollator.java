package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Collator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HighestDateCollator implements Collator<Map.Entry<String, Pair<Integer, LocalDateTime>>, Collection<String>> {
    private final Integer min;

    public HighestDateCollator(Integer min) {
        this.min = min;
    }

    @Override
    public Collection<String> collate(Iterable<Map.Entry<String, Pair<Integer, LocalDateTime>>> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).
                filter(o -> o.getValue().getKey() > min).
                sorted(
                        (o1, o2) -> {
                            int aux = o2.getValue().getKey().compareTo(o1.getValue().getKey());
                            if (aux != 0) {
                                return aux;
                            }
                            return o1.getValue().getValue().compareTo(o2.getValue().getValue());
                        }
                ).map((o) -> {
                    String date = o.getValue().getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    return o.getKey() + ";" + o.getValue().getKey() + ";" + date;
                }).collect(Collectors.toList());
    }
}
