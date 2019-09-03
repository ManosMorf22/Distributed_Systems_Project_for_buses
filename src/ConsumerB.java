import java.io.IOException;

public class ConsumerB {
    private static int []port={1112,2222,3332};
    public static void main(String[]args) throws IOException, ClassNotFoundException{
        ConUtilities.Confunction(port);
    }
}
