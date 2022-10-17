public class Server {

   private static final Logger logger = LoggerFactory.getLogger(Server.class);

   public static void main(String[] args) {
       logger.info("hz-config Server Starting ...");

       // Config
       Config config = new Config();

       // Group Config
       GroupConfig groupConfig = new GroupConfig().setName("g0").setPassword("g0-pass");
       config.setGroupConfig(groupConfig);

       // Network Config
       MulticastConfig multicastConfig = new MulticastConfig();

       JoinConfig joinConfig = new JoinConfig().setMulticastConfig(multicastConfig);

       InterfacesConfig interfacesConfig = new InterfacesConfig()
               .setInterfaces(Collections.singletonList("192.168.1.*")).setEnabled(true);

       NetworkConfig networkConfig = new NetworkConfig().setInterfaces(interfacesConfig).setJoin(joinConfig);

       config.setNetworkConfig(networkConfig);

       // Management Center Config
       ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig()
               .setUrl("http://localhost:32768/mancenter/")
               .setEnabled(true);
       config.setManagementCenterConfig(managementCenterConfig);

       // Start cluster
       Hazelcast.newHazelcastInstance(config);
   }

}
