package ar.edu.itba.pod.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class Client {

   private static final Logger logger = LoggerFactory.getLogger(Client.class);
   private static final String CSV_DELIMITER = ";";
   private static final String ADDRESS_SEPARATOR = ";";

   public static void main(String[] args) {
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
       serverAddresses.forEach(System.out::println);

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

       String outPath;
       try {
           outPath = Optional.ofNullable(properties.getProperty("inPath")).orElseThrow(IllegalArgumentException::new);
       }catch(IllegalArgumentException e){
           logger.error("Invalid inPath");
           return;
       }

       // ar.edu.itba.pod.client.Client Config
       ClientConfig clientConfig = new ClientConfig();

       // Group Config
       GroupConfig groupConfig = new GroupConfig().setName("g13").setPassword("g13-pass");
       clientConfig.setGroupConfig(groupConfig);

       // ar.edu.itba.pod.client.Client Network Config
       ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
//       String[] addresses = {"192.168.1.51:5701"};
       //todo add all addresses check how to do it
//       clientNetworkConfig.addAddress(serverAddresses.get(0));
       clientConfig.setNetworkConfig(clientNetworkConfig);

       //todo DESCOMENTAME!!!!
//       HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

       System.out.println(inPath);


//       final Stream<Sensor> sensorStream = getSensorStream();

       switch(queryNumber){
           case 1:
               break;
           case 2:
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
