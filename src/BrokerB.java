import java.io.IOException;


public class BrokerB {
    private static int port = 1235;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int[]portconsumer={2221,2222};
       BroUtilities.BrokerFunction(port,"BrokerB",portconsumer);

    }
}
