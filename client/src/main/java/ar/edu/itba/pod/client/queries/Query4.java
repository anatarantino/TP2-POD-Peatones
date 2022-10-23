package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.collators.AvgCountCollator;
import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;
import ar.edu.itba.pod.mappers.AvgCountMapper;
import ar.edu.itba.pod.predicates.SetCountPredicate;
import ar.edu.itba.pod.reducers.AvgCountReducer;
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
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/*
Donde cada línea de la salida contenga, separados por “;” el nombre del sensor, el
mayor promedio mensual de peatones de ese sensor para el año year y el mes de ese
promedio.
El promedio mensual de peatones de un sensor se calcula como la cantidad total de
peatones registrados en las mediciones de un mes dividido la cantidad de días del mes.
El mayor promedio mensual de peatones de un sensor es el promedio más alto de
los doce promedios posibles para el sensor.
El orden de impresión es descendente por el promedio mensual de peatones y luego
alfabético por nombre del sensor.
Sólo se deben listar los primeros n sensores del resultado que estén presentes en el
archivo CSV de sensores que tengan el estado Activo.
Los promedios se deben truncar en dos decimales.
Sensor;Month,Max_Monthly_Avg
Flinders La-Swanston St (West);April;1628.49
Southbank;October;1465.41
Bourke St Bridge;March;1440.00
 */

public class Query4 {
    private static final String QUERY_NAME = "query4";
    private static final String CSV_HEADER = "Sensor;Month;Max_Monthly_Avg";
    private static final Logger logger = LoggerFactory.getLogger(Query4.class);

    public static void run(final HazelcastInstance hazelcastInstance,
                           final Stream<Reading> readingStream,
                           final Stream<Sensor> sensorStream,
                           final File outFile, Integer year, Integer n) throws ExecutionException,InterruptedException, FileNotFoundException {
        final JobTracker jobTracker = hazelcastInstance.getJobTracker(QUERY_NAME);
        final MultiMap<String,Reading> readingsMap = hazelcastInstance.getMultiMap("readings");
        readingsMap.clear();
        final ISet<String> sensorsSet = hazelcastInstance.getSet("sensors");
        sensorsSet.clear();
        logger.info("Loading data...");
        loadCsv(sensorStream, readingStream, sensorsSet,readingsMap, year);
        logger.info("Data loading complete.");

        final KeyValueSource<String,Reading> source = KeyValueSource.fromMultiMap(readingsMap);
        final Job<String,Reading> job = jobTracker.newJob(source);

        ICompletableFuture<Collection<String>> future = job
                .keyPredicate(new SetCountPredicate<>("sensors"))
                .mapper(new AvgCountMapper())
                .reducer(new AvgCountReducer(year))
                .submit(new AvgCountCollator(n));

        try(PrintWriter printWriter = new PrintWriter(outFile)) {
            printWriter.println(CSV_HEADER);
            future.get().forEach(printWriter::println);
        }

    }

    public static void loadCsv(final Stream<Sensor> sensorStream, final Stream<Reading> readingStream, ISet<String> sensorsSet, MultiMap<String, Reading> readingsMap, Integer year){
        sensorStream.forEach(sensor -> {
            if(sensor.isActive()){
                sensorsSet.add(sensor.getDescription());
            }
        });
        readingStream.forEach(reading -> {
            if(reading.getYear() == year && sensorsSet.contains(reading.getSensorName())){
                readingsMap.put(reading.getSensorName(), reading);
            }
        });
    }

}
