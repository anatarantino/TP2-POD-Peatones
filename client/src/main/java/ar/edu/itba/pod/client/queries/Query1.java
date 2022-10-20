package ar.edu.itba.pod.client.queries;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.ISet;
import com.hazelcast.core.MultiMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import entities.Reading;
import entities.Sensor;
import javafx.util.Pair;

import java.io.File;
import java.util.Collection;
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
    public static void run(final HazelcastInstance hazelcastInstance, final Stream<Reading> readingStream, final Stream<Sensor> sensorStream, final File outFile){
        final JobTracker jobTracker = hazelcastInstance.getJobTracker(QUERY_NAME);
        final MultiMap<Integer, Reading> readingsMap = hazelcastInstance.getMultiMap("readings");
        readingsMap.clear();
        final ISet<Integer> sensorsSet = hazelcastInstance.getSet("sensors");
        sensorsSet.clear();
        loadCsv(sensorStream, readingStream, sensorsSet,readingsMap);

//        final KeyValueSource<Integer,Reading> source = KeyValueSource.fromMultiMap(readingsMap);
//        final Job<Integer, Reading> job = jobTracker.newJob(source);
//
//        final ICompletableFuture<Collection<Pair<Integer,Long>>> jobFuture = job.keyPredicate(new ValuesIn)

    }
}
