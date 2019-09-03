

import java.io.IOException;

public class ConsumerA {
    private static int []port={1111,2221,3331};
    public static void main(String[]args) throws IOException, ClassNotFoundException{
       ConUtilities.Confunction(port);
    }

}
