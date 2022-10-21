package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.collators.TotalReadingsCollator;
import ar.edu.itba.pod.mappers.TotalReadingsMapper;
import ar.edu.itba.pod.predicates.SensorCountPredicate;
import ar.edu.itba.pod.reducers.TotalReadingsReducer;
import ar.edu.itba.pod.utils.Pair;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.ISet;
import com.hazelcast.core.MultiMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;
import com.hazelcast.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static ar.edu.itba.pod.client.queries.QueryUtils.loadCsv;

/*
Donde cada línea de la salida contenga, separados por “;” el nombre del sensor y la cantidad total de peatones registrados por el sensor.
Sólo se deben listar los sensores presentes en el archivo CSV de sensores que tengan el estado Activo.
El orden de impresión es descendente por el total de peatones registrados y luego alfabético por nombre del sensor.

Sensor;Total_Count
Town Hall (West);185243492
Flinders Street Station Underpass;163910450
 */

//todo save all timestamp for outTimeFile

public class Query1 {
    private static final String QUERY_NAME = "query1";
    private static final Logger logger = LoggerFactory.getLogger(Query1.class);
    private static final String CSV_HEADER = "Sensor;Total_Count";

    public static void run(final HazelcastInstance hazelcastInstance, final Stream<Reading> readingStream, final Stream<Sensor> sensorStream, final File outFile) throws FileNotFoundException, InterruptedException, ExecutionException{
        final JobTracker jobTracker = hazelcastInstance.getJobTracker(QUERY_NAME);
        final MultiMap<Integer, Reading> readingsMap = hazelcastInstance.getMultiMap("readings");
        readingsMap.clear();
        final ISet<Integer> sensorsSet = hazelcastInstance.getSet("sensors");
        sensorsSet.clear();
        logger.info("pre csv");

        loadCsv(sensorStream, readingStream, sensorsSet,readingsMap);
        logger.info("despues csv");

        logger.info("sensors:" + sensorsSet.size());
        logger.info("readings: " + readingsMap.size());

        final KeyValueSource<Integer,Reading> source = KeyValueSource.fromMultiMap(readingsMap);
        final Job<Integer, Reading> job = jobTracker.newJob(source);
        ICompletableFuture<Collection<Pair<Integer,Long>>> future = job.keyPredicate(new SensorCountPredicate<>(hazelcastInstance.getSet("sensors")))
                .mapper(new TotalReadingsMapper())
                .reducer(new TotalReadingsReducer())
                .submit(new TotalReadingsCollator());

        try(PrintWriter printWriter = new PrintWriter(outFile)){
            printWriter.println(CSV_HEADER);
            future.get().forEach(f -> printWriter.println("hola"));
        }

    }
}
