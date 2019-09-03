import java.io.IOException;

public class BrokerA {
    private  static int port=1234;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int[]portconsumer={1111,1112};
        BroUtilities.BrokerFunction(port,"BrokerA",portconsumer);
    }

    }

