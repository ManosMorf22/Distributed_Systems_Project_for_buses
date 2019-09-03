import java.io.Serializable;
import java.util.ArrayList;

public class BusLine implements Serializable {
    private String LineId;
    private ArrayList<BusPosition> busPositions=new ArrayList<>();
    public BusLine(String LineId) {
        this.LineId = LineId;
    }

    public String getLineId() {
        return LineId;
    }
    public void addBusPosition(BusPosition bp){
        busPositions.add(bp);
    }
    private String BusPosition(){
        String bp="";
        for(BusPosition busPosition:busPositions)bp=bp+busPosition.toString();
        return bp;
    }
    public ArrayList<BusPosition> getBusPositions(){
        return busPositions;
    }
    public String toString(){
        return "This busline has \nLineId:"+LineId+"\nThe buses of these lines are:\n"+BusPosition()+"\n";
    }

}