import java.io.Serializable;
import java.util.ArrayList;
public class UserDetails implements Serializable {
    private String name;
    private String userID;
    private Location prefStartLocation;
    private String prefTravelMode;
    private ArrayList<String> sessions = new ArrayList<>();

    public UserDetails(){}

    public UserDetails(String name, String userID, Location prefStartLocation, String prefTravelMode){
        this.name=name;
        this.userID=userID;
        this.prefStartLocation=prefStartLocation;
        this.prefTravelMode=prefTravelMode;
    }
    public UserDetails(String name, String userID, Location prefStartLocation, String prefTravelMode, ArrayList<String> sessions){
       this.name=name;
       this.userID=userID;
       this.prefStartLocation=prefStartLocation;
       this.prefTravelMode=prefTravelMode;
       this.sessions=sessions;
    }

    public String getName(){
        return name;
    }

    public String getUserID(){
        return userID;
    }

    public Location getPrefStartLocation(){
        return prefStartLocation;
    }

    public String getPrefTravelMode(){
        return prefTravelMode;
    }

    public ArrayList<String> getSessions(){
        return sessions;
    }

    public void setName(String newName){
        name=newName;
    }

    public void setPrefStartLocation(Location newLocation){
        prefStartLocation=newLocation;
    }

    public void setPrefTravelMode(String newMode){
        prefTravelMode=newMode;
    }

    public void addSession(String newSessionId){
        sessions.add(newSessionId);
    }

    public void removeSession(String session){
        sessions.remove(session);
    }


}
