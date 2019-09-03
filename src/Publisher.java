import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class Publisher {
private static ArrayList<Route>routes=new ArrayList<>();
private static ArrayList<BusLine> busLines=new ArrayList<>();
private static ArrayList<BusPosition> buspositions=new ArrayList<>();
private static HashMap<String,ArrayList<BusLine>> broker=new HashMap<>();
private static String brokername[]=new String[3];
private static ServerSocket []serverSocket=new ServerSocket[3];
//topic=LineId
 public static void main(String[] args) throws IOException {
   CreateRoutes();
   CreateBusPositions();
   CreateBusLines();
   Spread();
   Push();
 }
    private static void Push()throws IOException{

        serverSocket[0]=new ServerSocket(1234);
        serverSocket[1]=new ServerSocket(1235);
        serverSocket[2]=new ServerSocket(1236);
        Thread thread[]=new Thread[3];
        for(int i=0; i<3; i++){
            thread[i]=new Thread(new PubBro(i,broker.get(brokername[i])));
        }
        for(int i=0; i<3; i++) thread[i].start();
    }
    private static void Spread(){
        ArrayList<BusLine> A = new ArrayList<>();
        ArrayList<BusLine> B = new ArrayList<>();
        ArrayList<BusLine> C = new ArrayList<>();
        byte Byte=0;
        for (BusLine busLine: busLines){
            if(Byte==0) A.add(busLine);
            else if(Byte==1) B.add(busLine);
            else C.add(busLine);
            Byte=Up(Byte);
        }
        broker.put("BrokerA",A);
        broker.put("BrokerB",B);
        broker.put("BrokerC",C);
        brokername[0]="BrokerA";
        brokername[1]="BrokerB";
        brokername[2]="BrokerC";
    }
    private  static void CreateRoutes() throws IOException {
        String line;
        FileReader fr=new FileReader("RouteCodesNew.txt");
        BufferedReader br=new BufferedReader(fr);
        line=br.readLine();
        while(line!=null){
            List<String> RouteCharacteristics= Arrays.asList(line.split(","));
            routes.add(new Route(RouteCharacteristics.get(0),RouteCharacteristics.get(1),RouteCharacteristics.get(2),RouteCharacteristics.get(3)));
            line=br.readLine();
        }

    }
    private static void CreateBusLines() throws IOException {
        String line;
        FileReader fr=new FileReader("BusLinesNew.txt");
        BufferedReader br=new BufferedReader(fr);
        line=br.readLine();
        while(line!=null){
            List<String> BusLineCharacteristics= Arrays.asList(line.split(","));
            busLines.add(new BusLine(BusLineCharacteristics.get(1)));
            for(BusPosition busPosition:buspositions)
            {
                ArrayList<Route> insideroutes=busPosition.getRoutes();
               for(Route route:insideroutes){
                   if(route.getLineCode().equals(BusLineCharacteristics.get(0))){
                       busLines.get(busLines.size()-1).addBusPosition(busPosition);
                       break;
                   }
               }
            }
            line=br.readLine();
        }
    }
    private static void CreateBusPositions() throws IOException {
        String line;
        FileReader fr=new FileReader("BusPositionsNew.txt");
        BufferedReader br=new BufferedReader(fr);
        line=br.readLine();
        while(line!=null){
            List<String> BusPositionCharacteristics= Arrays.asList(line.split(","));
            buspositions.add(new BusPosition(Double.parseDouble(BusPositionCharacteristics.get(4)),Double.parseDouble(BusPositionCharacteristics.get(3)),BusPositionCharacteristics.get(5),BusPositionCharacteristics.get(2)));
            for(Route r:routes){
                if(BusPositionCharacteristics.get(1).equals(r.getRouteCode())) buspositions.get(buspositions.size()-1).addRoute(r);
            }
            line=br.readLine();
        }
    }
    private static byte Up(byte x){
     if(x<2)x++;
     else x=0;
     return x;
    }
    private static class PubBro implements Runnable{
     private int pos;
        private ArrayList<BusLine> data;
     public PubBro(int pos,ArrayList<BusLine> data){
         this.pos=pos;
         this.data=data;
     }
        @Override
        public void run() {
            try {
                Socket socket=serverSocket[pos].accept();
                ObjectOutputStream printWriter=new ObjectOutputStream(socket.getOutputStream());
                for(BusLine bl:data) {
                    printWriter.writeObject(bl);
                    Thread.sleep(2000);
                }
                printWriter.writeObject(new BusLine("UV"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
