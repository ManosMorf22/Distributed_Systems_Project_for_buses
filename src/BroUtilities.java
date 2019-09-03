import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class BroUtilities {
    public static void BrokerFunction(int port,String name,int[] portconsumer) throws IOException, ClassNotFoundException {
            BusLine data;
            Socket socket=new Socket("localhost",port) ;
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            HashMap<String,ArrayList<BusPosition>> buses=new HashMap<>();
            data= (BusLine) objectInputStream.readObject();
            while(!data.getLineId().equals("UV")) {
                buses.put(data.getLineId(),data.getBusPositions());
                data = (BusLine) objectInputStream.readObject();
            }
            socket.close();
        Thread []thread=new Thread[2];
        for(int i=0; i<2; i++)thread[i]=new Thread(new BroCon(portconsumer[i],name,buses));
        for(int i=0; i<2; i++)thread[i].start();

    }
    private static class BroCon implements  Runnable{
        private int port;
        private String name;
        private HashMap<String,ArrayList<BusPosition>>buses;
        public BroCon(int port,String name,HashMap<String,ArrayList<BusPosition>>buses){
            this.port=port;
            this.name=name;
            this.buses=buses;
        }
        public void run() {

            try {
                while (true) {
                    ServerSocket serverSocket = new ServerSocket(port);
                    while (true) {
                        Socket socket = serverSocket.accept();
                        ObjectOutputStream printWriter = new ObjectOutputStream(socket.getOutputStream());
                        printWriter.writeObject("I am " + name + " and I am responsible for these keys:");
                        printWriter.flush();
                        for (String busId : buses.keySet()) {
                            printWriter.writeObject(busId);
                            printWriter.flush();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                            printWriter.writeObject("Done");
                            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                            String answer = "";
                            try {
                                answer = (String) objectInputStream.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            boolean catched = false;
                            ArrayList<BusPosition> busPositions = null;

                                busPositions = buses.get(answer);
                            if(busPositions==null) {
                                printWriter.writeObject("I am not responsible for this line,ask another broker");
                                catched = true;
                            }
                            if (!catched) {
                                printWriter.writeObject("Information for line: " + answer + "\n");
                                for (BusPosition busPosition1 : busPositions)
                                    printWriter.writeObject(busPosition1.toString());
                                printWriter.writeObject("Done");
                            }else{
                                socket.close();
                                break;
                            }
                            printWriter.close();
                        }
                    }
                }catch(IOException e){
                e.printStackTrace();
            }
                }


        }
    }

