import java.io.Serializable;
import java.util.ArrayList;

public class BusPosition implements Serializable {
    private double x;
    private double y;
    private String timestamp;
    private ArrayList<Route> routes=new ArrayList<>();
    private String vehicleId;

    public BusPosition(double x,double y,String timestamp,String vehicleId){
        this.x=x;
        this.y=y;
        this.timestamp=timestamp;
        this.vehicleId=vehicleId;
    }
    public void addRoute(Route r){
        routes.add(r);
    }
    public ArrayList<Route> getRoutes(){
        return routes;
    }
    private String Routes(){
        String r="";
        for(Route route:routes) r=r+route.toString();
        return r;
    }
    public String getTimestamp(){
        return timestamp;
    }
    public String toString(){
        return "This Bus has \nid:"+vehicleId+"\nYdimension:"+x+"\nXdimension:"+y+"\ntimestamp:"+timestamp+"\nRoutes\n"+Routes()+"\n";
    }
}
