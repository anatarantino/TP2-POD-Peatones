package ar.edu.itba.pod.client;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Utils {
    private static final int MAX_NUMBER = 5;

    public static ServerAddress serverAddressParser(final String serverAddress) throws NumberFormatException{
        final String[] addressTokens = serverAddress.split(":",2);
        return new ServerAddress(addressTokens[0], Integer.parseInt(addressTokens[1]));
    }

    public static class ServerAddress {
        private final String ip;
        private final Integer port;

        public ServerAddress(String ip, Integer port){
            this.ip = ip;
            this.port = port;
        }

        public Integer getPort(){
            return port;
        }

        public String getIp(){
            return ip;
        }

        public String getServerAddress(){
            return ip + ":" + port;
        }
    }

    public static List<String> parseAddresses(final String addreses, final String separator){
        return Arrays.asList(addreses.replaceAll("","").split(separator));

    }

    public static Integer parseQueryNumber(final String[] args){
        if(args.length != 1){
            throw new IllegalArgumentException();
        }
        return Optional.of(Integer.parseInt(args[0])).filter(n -> n > 0 && n <= MAX_NUMBER).orElseThrow(IllegalArgumentException::new);
    }


}
