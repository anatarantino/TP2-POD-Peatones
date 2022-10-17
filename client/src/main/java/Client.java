public class Client {

   private static final Logger logger = LoggerFactory.getLogger(Client.class);

   public static void main(String[] args) {
       logger.info("hz-config Client Starting ...");

       // Client Config
       ClientConfig clientConfig = new ClientConfig();

       // Group Config
       GroupConfig groupConfig = new GroupConfig().setName("g0").setPassword("g0-pass");
       clientConfig.setGroupConfig(groupConfig);

       // Client Network Config
       ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
       String[] addresses = {"192.168.1.51:5701"};
       clientNetworkConfig.addAddress(addresses);
       clientConfig.setNetworkConfig(clientNetworkConfig);

       HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

       String mapName = "testMap";

       IMap<Integer, String> testMapFromMember = hazelcastInstance.getMap(mapName);
       testMapFromMember.set(1, "test1");

       IMap<Integer, String> testMap = hazelcastInstance.getMap(mapName);
       System.out.println(testMap.get(1));

       // Shutdown
       HazelcastClient.shutdownAll();
   }

}