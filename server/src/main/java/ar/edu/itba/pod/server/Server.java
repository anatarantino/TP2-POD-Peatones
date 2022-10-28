package ar.edu.itba.pod.server;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class Server {

   private static final Logger logger = LoggerFactory.getLogger(Server.class);

   public static void main(String[] args) {
       logger.info("hz-config ar.edu.itba.pod.server.Server Starting ...");

       System.setProperty("java.net.preferIPv4Stack", "true");
       // Config
       Config config = new Config();

       // Group Config
       GroupConfig groupConfig = new GroupConfig().setName("g13").setPassword("g13-pass");
       config.setGroupConfig(groupConfig);

       // Network Config
       MulticastConfig multicastConfig = new MulticastConfig();

       JoinConfig joinConfig = new JoinConfig().setMulticastConfig(multicastConfig);

       InterfacesConfig interfacesConfig = new InterfacesConfig()
               .setInterfaces(Collections.singletonList("*.*.*.*")).setEnabled(true);

       NetworkConfig networkConfig = new NetworkConfig().setInterfaces(interfacesConfig).setJoin(joinConfig);

       config.setNetworkConfig(networkConfig);

       //config logger
       config.setProperty("hazelcast.logging.type","slf4j");

       //change multimap value type to list instead of set
       final MultiMapConfig multiMapConfig = new MultiMapConfig();
       multiMapConfig.setName("default");
       multiMapConfig.setValueCollectionType("LIST");

       config.addMultiMapConfig(multiMapConfig);
       // Start cluster
       Hazelcast.newHazelcastInstance(config);
   }

}
