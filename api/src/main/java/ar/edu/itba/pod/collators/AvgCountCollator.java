package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.mapreduce.Collator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AvgCountCollator implements Collator<Map.Entry<String, Pair<Integer, Double>>, Collection<String>> {
    private final Integer n;

    public AvgCountCollator(Integer n) {
        this.n = n;
    }

    @Override
    public Collection<String> collate(Iterable<Map.Entry<String, Pair<Integer, Double>>> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .sorted(
                        (o1, o2) -> {
                            int aux = o2.getValue().getValue().compareTo(o1.getValue().getValue());
                            if (aux != 0) {
                                return aux;
                            }
                            return o1.getKey().compareTo(o2.getKey());
                        }
                ).map(
                        (o) -> {
                            String month = Month.of(o.getValue().getKey()).toString().toLowerCase();
                            String capitalizedMonth = month.substring(0, 1).toUpperCase() + month.substring(1);
                            return o.getKey() + ";" + capitalizedMonth + ";" + BigDecimal.valueOf(o.getValue().getValue()).setScale(2, RoundingMode.UP);
                        }).limit(n).collect(Collectors.toList());
    }
}
