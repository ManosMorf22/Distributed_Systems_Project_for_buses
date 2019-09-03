import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ConUtilities {
    public static void Confunction(int[] port) throws IOException, ClassNotFoundException {
        int choice;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Type 1 for brokerA 2 for BrokerB 3 for BrokerC");
            choice = Integer.parseInt(in.nextLine());
            choice--;
            while (true) {
                Socket socket = new Socket("localhost", port[choice]);
                ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
                String line = (String) objectInputStream.readObject();
                while (!line.equals("Done")) {
                    System.out.println(line);
                    line = (String) objectInputStream.readObject();
                }
                System.out.println("Choose busline");
                String answer = in.nextLine();
                ObjectOutputStream printWriter = new ObjectOutputStream(socket.getOutputStream());
                printWriter.writeObject(answer);
                printWriter.flush();
                line = (String) objectInputStream.readObject();
                if(line.equals("I am not responsible for this line,ask another broker")) {
                    System.out.println(line);
                    break;
                }
                while (!line.equals("Done")) {
                    System.out.println(line);
                    System.out.println("---------------------------");
                    line = (String) objectInputStream.readObject();
                }
            }
        }
    }
}
