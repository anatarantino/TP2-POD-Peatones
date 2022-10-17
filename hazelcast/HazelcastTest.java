public class HazelcastTest {

   private TestHazelcastFactory hazelcastFactory;
   private HazelcastInstance member, client;

   @Before
   public void setUp() {
       hazelcastFactory = new TestHazelcastFactory();

       // Group Config
       GroupConfig groupConfig = new GroupConfig().setName("gX").setPassword("gX-pass");

       // Config
       Config config = new Config().setGroupConfig(groupConfig);

       member = hazelcastFactory.newHazelcastInstance(config);

       // Client Config
       ClientConfig clientConfig = new ClientConfig().setGroupConfig(groupConfig);

       client = hazelcastFactory.newHazelcastClient(clientConfig);
   }

   @Test
   public void simpleTest() {
       String mapName = "testMap";

       IMap<Integer, String> testMapFromMember = member.getMap(mapName);
       testMapFromMember.set(1, "test1");

       IMap<Integer, String> testMap = client.getMap(mapName);
       String value = testMap.get(1);
       Assert.assertEquals("test1", value);
   }

   @After
   public void tearDown() {
       hazelcastFactory.shutdownAll();
   }

}
