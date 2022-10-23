package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.collators.YearsCollator;
import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;
import ar.edu.itba.pod.mappers.YearsMapper;
import ar.edu.itba.pod.predicates.SetCountPredicate;
import ar.edu.itba.pod.reducers.YearsReducer;
import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.ISet;
import com.hazelcast.core.MultiMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;


/*
Donde cada línea de la salida contenga, separados por “;” el año, la cantidad total de peatones registrados por todos los sensores durante los weekdays (lunes a viernes) de ese año, la cantidad total de peatones registrados por todos los sensores durante los weekends (sábado y domingo) de ese año y la suma de ambos valores.
Year;Weekdays_Count;Weekends_Count;Total_Count
2022;88657325;42157989;130815314 2021;97641853;42685412;140327265 ...
 */

public class Query2 {
    private static final String QUERY_NAME = "query2";
    private static final String CSV_HEADER = "Year;Weekdays_Count;Weekends_Count;Total_Count";
    private static final Logger logger = LoggerFactory.getLogger(Query2.class);

    public static void run(final HazelcastInstance hazelcastInstance, final Stream<Reading> readingStream, final Stream<Sensor> sensorStream, final File outFile) throws FileNotFoundException, InterruptedException, ExecutionException {
        final JobTracker jobTracker = hazelcastInstance.getJobTracker(QUERY_NAME);
        final MultiMap<Integer, Reading> readingsMap = hazelcastInstance.getMultiMap("readings");
        final ISet<Integer> yearsSet = hazelcastInstance.getSet("years");
        yearsSet.clear();
        logger.info("Loading data...");
        loadCsv(readingStream, yearsSet,readingsMap);
        logger.info("Data loading complete.");

        final KeyValueSource<Integer,Reading> source = KeyValueSource.fromMultiMap(readingsMap);

        final Job<Integer, Reading> job = jobTracker.newJob(source);

        ICompletableFuture<Collection<String>> future = job
                .keyPredicate(new SetCountPredicate<>("years"))
                .mapper(new YearsMapper())
                .reducer(new YearsReducer())
                .submit(new YearsCollator());

        try(PrintWriter printWriter = new PrintWriter(outFile)){
            printWriter.println(CSV_HEADER);
            future.get().forEach(printWriter::println);
        }
    }

    public static void loadCsv(final Stream<Reading> readingStream, ISet<Integer> yearsSet, MultiMap<Integer, Reading> readingsMap){
        readingStream.forEach(reading -> {
            readingsMap.put(reading.getYear(), reading);
            yearsSet.add(reading.getYear());
        });
    }

}
