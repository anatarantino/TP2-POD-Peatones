package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.collators.TotalPedestriansCollator;
import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;
import ar.edu.itba.pod.mappers.TotalPedestriansMapper;
import ar.edu.itba.pod.predicates.ReadingCountPredicate;
import ar.edu.itba.pod.predicates.SensorCountPredicate;
import ar.edu.itba.pod.reducers.TotalPedestriansReducer;
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

import static ar.edu.itba.pod.client.queries.QueryUtils.loadCsv;

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
        final MultiMap<String, Reading> readingsMap = hazelcastInstance.getMultiMap("readings");
        readingsMap.clear();
        final ISet<String> sensorsSet = hazelcastInstance.getSet("sensors");
        sensorsSet.clear();
        logger.info("pre csv");

        loadCsv(sensorStream, readingStream, sensorsSet,readingsMap);
        logger.info("despues csv");

        logger.info("sensors:" + sensorsSet.size());
        logger.info("readings: " + readingsMap.size());

        final KeyValueSource<String,Reading> source = KeyValueSource.fromMultiMap(readingsMap);

        final Job<String, Reading> job = jobTracker.newJob(source);

        ICompletableFuture<Collection<Pair<Integer,Pair<Long,Long>>>> future = job
                .keyPredicate(new ReadingCountPredicate<>())

//        ICompletableFuture<Collection<Pair<Integer,Pair<Long,Long>>>> future = job
//                .keyPredicate(new SensorCountPredicate<>("readings"))
//                .mapper(new TotalPedestriansMapper())
//                .reducer(new TotalPedestriansReducer())
//                .submit(new TotalPedestriansCollator());

//
//        try(PrintWriter printWriter = new PrintWriter(outFile)){
//            printWriter.println(CSV_HEADER);
//            future.get().forEach(p -> printWriter.println(p.getKey() + ";" + p.getValue()));
//        }
    }
}
