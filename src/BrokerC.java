import java.io.IOException;


public class BrokerC {
    private static int port = 1236;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int[]portconsumer={3331,3332};
        BroUtilities.BrokerFunction(port,"BrokerC",portconsumer);
    }
}