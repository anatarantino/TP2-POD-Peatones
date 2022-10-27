package ar.edu.itba.pod.client.queries;

/*
Donde cada línea de la salida contenga separados por ; el grupo de millones de
peatones, el nombre del sensor A que corresponde al grupo de peatones y el nombre del
sensor B que corresponde al grupo de peatones.
El último grupo posible a mostrar es el de 1.000.000 (incluyendo aquellos pares de
sensores que registren entre 1.000.000 de peatones inclusive y 1.999.999 peatones
inclusive), es decir, no listar los pares de sensores que registren hasta 999.999 peatones
inclusive.
No se debe listar el par opuesto (es decir si se lista Grupo;Sensor A;Sensor B no
debe aparecer la tupla Grupo;Sensor B;Sensor A).
Si un grupo está compuesto por un único sensor, el par no puede armarse por lo que
no se lista.
El orden de impresión es descendente por grupo y el orden de los pares dentro de
cada grupo es alfabético por nombre del sensor.
Sólo se deben listar los sensores presentes en el archivo CSV de sensores que
tengan el estado Activo.
Group;Sensor A;Sensor B
12.000.000;Flinders Street Station Underpass;Melbourne Central
...
7.000.000;Flinders St-Elizabeth St (East);Spencer St-Collins St
(North)
7.000.000;Flinders St-Elizabeth St (East);State Library
 */


import ar.edu.itba.pod.client.PerformanceResults;
import ar.edu.itba.pod.collators.PairsByMillionsCollator;
import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;
import ar.edu.itba.pod.mappers.TotalPedestriansMapper;
import ar.edu.itba.pod.reducers.TotalMillionsPerSensorReducer;
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
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class Query5 {
    private static final String QUERY_NAME = "query5";
    private static final String CSV_HEADER = "Group;Sensor A;Sensor B";
    private static final Logger logger = LoggerFactory.getLogger(Query4.class);

    public static PerformanceResults run(final HazelcastInstance hazelcastInstance,
                                         final Stream<Reading> readingStream,
                                         final Stream<Sensor> sensorStream,
                                         final File outFile) throws ExecutionException,InterruptedException, FileNotFoundException{

        final JobTracker jobTracker = hazelcastInstance.getJobTracker(QUERY_NAME);
        final MultiMap<String,Reading> readingsMap = hazelcastInstance.getMultiMap("readings");
        readingsMap.clear();
        final ISet<String> sensorsSet = hazelcastInstance.getSet("sensors");
        sensorsSet.clear();
        logger.info("Loading data...");

        final PerformanceResults performanceResults = new PerformanceResults();

        loadCsv(sensorStream, readingStream, sensorsSet,readingsMap, performanceResults);
        logger.info("Data loading complete.");
        final KeyValueSource<String,Reading> source = KeyValueSource.fromMultiMap(readingsMap);
        final Job<String,Reading> job = jobTracker.newJob(source);

        performanceResults.setMapReduceBegin(LocalDateTime.now());
        ICompletableFuture<Collection<String>> future = job
                .mapper(new TotalPedestriansMapper())
                .reducer(new TotalMillionsPerSensorReducer())
                .submit(new PairsByMillionsCollator());

        try(PrintWriter printWriter = new PrintWriter(outFile)) {
            printWriter.println(CSV_HEADER);
            future.get().forEach(printWriter::println);
        }

        performanceResults.setMapReduceEnd(LocalDateTime.now());
        return performanceResults;

    }

    public static void loadCsv(final Stream<Sensor> sensorStream, final Stream<Reading> readingStream, ISet<String> sensorsSet, MultiMap<String, Reading> readingsMap, PerformanceResults performanceResults){
        performanceResults.setReadingFileBegin(LocalDateTime.now());
        sensorStream.forEach(sensor -> sensorsSet.add(sensor.getDescription()));
        readingStream.forEach(reading -> readingsMap.put(reading.getSensorName(), reading));
        performanceResults.setReadingFileEnd(LocalDateTime.now());
    }

}
