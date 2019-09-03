import java.io.Serializable;

public class Route implements Serializable {
    private String LineCode;
    private String RouteCode;
    private String RouteType;
    private String Description;

    public Route(String RouteCode,String LineCode,String RouteType,String Description){
        this.LineCode=LineCode;
        this.RouteCode=RouteCode;
        this.RouteType=RouteType;
        this.Description=Description;
    }
    public String getLineCode(){
        return LineCode;
    }
    public String getRouteCode(){
        return RouteCode;
    }

    public String toString(){
        return "This Route has:\nLineCode:"+LineCode+"\nRouteCode:"+RouteCode+"\nRouteType:"+RouteType+"\nDescription:"+Description+"\n";
    }
}
