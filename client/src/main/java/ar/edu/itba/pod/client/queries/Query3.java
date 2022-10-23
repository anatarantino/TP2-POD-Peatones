package ar.edu.itba.pod.client.queries;
/*

Donde cada línea de la salida contenga, separados por “;” el nombre del sensor, la cantidad de peatones de la medición más alta de ese sensor y la fecha y hora de esa medición.
Sólo se deben listar los sensores presentes en el archivo CSV de sensores que tengan el estado Activo cuya medición más alta tenga una cantidad de peatones mayor al parámetro min.
En caso de existir para un mismo sensor dos o más mediciones con el mismo valor más alto, elegir la de la medición más reciente.
El orden de impresión es descendente por cantidad de peatones y luego alfabético por nombre del sensor.
La fecha y hora de la medición más alta debe imprimirse con el formato dd/MM/yyyy HH:00.

Sensor;Max_Reading_Count;Max_Reading_DateTime
New Quay;15782;14/11/2019 11:00
Birrarung Marr;10389;12/11/2019 12:00
Bourke St Bridge;10389;16/11/2019 18:00
 */

import ar.edu.itba.pod.collators.HighestDateCollator;
import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;
import ar.edu.itba.pod.mappers.HighestDateMapper;
import ar.edu.itba.pod.predicates.SetCountPredicate;
import ar.edu.itba.pod.reducers.HighestDateReducer;
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

public class Query3 {
    private static final String QUERY_NAME = "query3";
    private static final String CSV_HEADER = "Sensor;Max_Reading_Count;Max_Reading_DateTime";
    private static final Logger logger = LoggerFactory.getLogger(Query3.class);

    public static void run(final HazelcastInstance hazelcastInstance, final Stream<Reading> readingStream, final Stream<Sensor> sensorStream, final File outFile, Integer min) throws ExecutionException,InterruptedException, FileNotFoundException {
        final JobTracker jobTracker = hazelcastInstance.getJobTracker(QUERY_NAME);
        final MultiMap<String, Reading> readingsMap = hazelcastInstance.getMultiMap("readings");
        readingsMap.clear();
        final ISet<String> sensorsSet = hazelcastInstance.getSet("sensors");
        sensorsSet.clear();
        logger.info("Loading data...");
        loadCsv(sensorStream, readingStream, sensorsSet,readingsMap);
        logger.info("Data loading complete.");

        final KeyValueSource<String,Reading> source = KeyValueSource.fromMultiMap(readingsMap);

        final Job<String,Reading> job = jobTracker.newJob(source);

        ICompletableFuture<Collection<String>> future = job
                .keyPredicate(new SetCountPredicate<>("sensors"))
                .mapper(new HighestDateMapper())
                .reducer(new HighestDateReducer())
                .submit(new HighestDateCollator(min));

        try(PrintWriter printWriter = new PrintWriter(outFile)) {
            printWriter.println(CSV_HEADER);
            future.get().forEach(printWriter::println);
        }

    }

    public static void loadCsv(final Stream<Sensor> sensorStream, final Stream<Reading> readingStream, ISet<String> sensorsSet, MultiMap<String, Reading> readingsMap){
        sensorStream.forEach(sensor -> {
            if(sensor.isActive()){
                sensorsSet.add(sensor.getDescription());
            }
        });
        readingStream.forEach(reading -> {
            if(sensorsSet.contains(reading.getSensorName())){
                readingsMap.put(reading.getSensorName(), reading);
            }
        });
    }
}
