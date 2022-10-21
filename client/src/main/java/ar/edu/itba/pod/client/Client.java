package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.queries.Query1;
import ar.edu.itba.pod.client.queries.Query2;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import ar.edu.itba.pod.entities.Reading;
import ar.edu.itba.pod.entities.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static ar.edu.itba.pod.utils.FileUtils.getReadingsStream;
import static ar.edu.itba.pod.utils.FileUtils.getSensorStream;

public class  Client {

   private static final Logger logger = LoggerFactory.getLogger(Client.class);
   private static final String CSV_DELIMITER = ";";
   private static final String ADDRESS_SEPARATOR = ";";

   public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
       logger.info("hz-config ar.edu.itba.pod.client.Client Starting ...");

       final Properties properties = System.getProperties();
       final List<String> serverAddresses;
       final String propertyAddresses;
       try {
           propertyAddresses = Optional.ofNullable(properties.getProperty("addresses")).orElseThrow(IllegalArgumentException::new);
       }catch (IllegalArgumentException e){
           logger.error("Invalid addresses.");
           return;
       }
       serverAddresses = Utils.parseAddresses(propertyAddresses, ADDRESS_SEPARATOR);

       Integer queryNumber;
       try {
           queryNumber = Utils.parseQueryNumber(args);
       }catch (IllegalArgumentException e){
           logger.error("Invalid query number.");
           return;
       }

       String inPath;
       try{
           inPath = Optional.ofNullable(properties.getProperty("inPath")).orElseThrow(IllegalArgumentException::new);
       }catch(IllegalArgumentException e){
           logger.error("Invalid inPath");
           return;
       }

       final File sensorFile = Paths.get(inPath + "/sensors.csv").toFile();
       final File readingsFile = Paths.get(inPath + "/readings.csv").toFile();

       String outPath;
       try {
           outPath = Optional.ofNullable(properties.getProperty("outPath")).orElseThrow(IllegalArgumentException::new);
       }catch(IllegalArgumentException e){
           logger.error("Invalid inPath");
           return;
       }
       final File outQueryFile = new File(outPath + "/query" + queryNumber + ".csv");
       final File outTimeFile = new File(outPath + "/time" + queryNumber + ".txt");

       ClientConfig clientConfig = new ClientConfig();

       // Group Config
       GroupConfig groupConfig = new GroupConfig().setName("g13").setPassword("g13-pass");
       clientConfig.setGroupConfig(groupConfig);
       ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();

       clientNetworkConfig.setAddresses(serverAddresses);
       clientConfig.setNetworkConfig(clientNetworkConfig);

       HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

       final Stream<Sensor> sensorStream = getSensorStream(sensorFile.toPath(), CSV_DELIMITER);
       //todo change limit
       final Stream<Reading> readingStream = getReadingsStream(readingsFile.toPath(), CSV_DELIMITER).limit(10000);
//       final Stream<Reading> readingStream = getReadingsStream(readingsFile.toPath(), CSV_DELIMITER);


       switch(queryNumber){
           case 1:
               Query1.run(hazelcastInstance, readingStream,sensorStream, outQueryFile);
               break;
           case 2:
               Query2.run(hazelcastInstance, readingStream, sensorStream, outQueryFile);
               break;
           case 3:
               long min;
               try{
                   min = Optional.of(Long.parseLong(properties.getProperty("min"))).orElseThrow(IllegalArgumentException::new);
               }catch(IllegalArgumentException e){
                   logger.error("Invalid minimum pedestrians value");
               }
               break;
           case 4:
               int n;
               int year;

               try{
                   n = Optional.of(Integer.parseInt(properties.getProperty("n"))).orElseThrow(IllegalArgumentException::new);
               }catch(IllegalArgumentException e){
                   logger.error("Invalid top sensors value");
               }

               try{
                   year = Optional.of(Integer.parseInt(properties.getProperty("year"))).orElseThrow(IllegalArgumentException::new);
               }catch(IllegalArgumentException e){
                   logger.error("Invalid year");
               }
               break;
           case 5:
               break;
       }

       





       // Shutdown
       HazelcastClient.shutdownAll();
   }

}
