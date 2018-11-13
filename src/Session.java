import java.io.Serializable;
import java.util.Scanner;

public class Session implements Serializable {
    private String sessionID;
    private String title;
    private Location[] startLocations;
    private String[] prefTravelModes;
    private MeetPoint[] meetPoints;
    private MeetPoint chosenMeetPoint;
    private String prefLocationType;
    private String[] userIDs;
    private int numOfUsers;
    private boolean calculated;

    public Session(){}
    //constructor for create session
    public Session(String sessionID, String title, Location startLocation, String prefTravelMode, String userID){
        this.sessionID=sessionID;
        this.title=title;
        this.startLocations = new Location[2];
        this.startLocations[0]=startLocation;
        this.prefTravelModes = new String[2];
        this.prefTravelModes[0]=prefTravelMode;
        this.userIDs=new String[2];
        this.userIDs[0]=userID;
        this.numOfUsers=1;
        this.calculated = false;
        this.meetPoints = new MeetPoint[5];
    }
    //constructor for csv parsing - calculated
    public Session(String sessionID, String title, Location[] startLocations, String[] prefTravelModes, MeetPoint[] meetPoints,
                   MeetPoint chosenMeetPoint, String prefLocationType, String[] userIDs, int numOfUsers, boolean calculated){
        this.sessionID=sessionID;
        this.title=title;
        this.startLocations = new Location[2];
        this.startLocations=startLocations;
        this.prefTravelModes = new String[2];
        this.prefTravelModes=prefTravelModes;
        this.meetPoints = new MeetPoint[5];
        this.meetPoints = meetPoints;
        this.chosenMeetPoint = chosenMeetPoint;
        this.prefLocationType = prefLocationType;
        this.userIDs=new String[2];
        this.userIDs = userIDs;
        this.numOfUsers = numOfUsers;
        this.calculated = calculated;
    }
    //constructor for csv parsing - not calculated
    public Session(String sessionID, String title, Location[] startLocations, String[] prefTravelModes, String prefLocationType,
                   String[] userIDs, int numOfUsers, boolean calculated){
        this.sessionID=sessionID;
        this.title=title;
        this.startLocations = new Location[2];
        this.startLocations=startLocations;
        this.prefTravelModes = new String[2];
        this.prefTravelModes=prefTravelModes;
        this.meetPoints = new MeetPoint[5];
        this.prefLocationType = prefLocationType;
        this.userIDs=new String[2];
        this.userIDs = userIDs;
        this.numOfUsers = numOfUsers;
        this.calculated = calculated;
    }

    public String getSessionID(){
        return sessionID;
    }
    public String getTitle(){
        return title;
    }
    public Location[] getStartLocations(){
        return startLocations;
    }

    public Location getUserStartLocation(String userID) {
        return startLocations[userIDToIndex(userID)];
    }

    public String[] getPrefTravelModes() {
        return prefTravelModes;
    }

    public String getUserPrefTravelMode(String userID){
        return prefTravelModes[userIDToIndex(userID)];
    }

    public MeetPoint[] getMeetPoints(){
        return meetPoints;
    }
    public MeetPoint getChosenMeetPoint(){
        return chosenMeetPoint;
    }
    public String getPrefLocationType(){
        return prefLocationType;
    }
    public String[] getUserIDs(){
        return userIDs;
    }
    public int getNumOfUsers(){
        return numOfUsers;
    }
    public boolean getCalculated() {
        return calculated;
    }
    public int getChosenMeetPointIndex(MeetPoint meetpoint){
        for (int i=0;i< meetPoints.length;i++){
            if (meetPoints[i]==meetpoint) {
                return i;
            }
        }
        return -1;
    }

    public void setTitle(String newTitle){
        title=newTitle;
    }
    public void setStartLocation(int fieldIndex, Location newLocation){ //technically i can use this to find out which usr enter which location but no
        startLocations[fieldIndex]=newLocation;
    }
    public void setPrefTravelModes(int fieldIndex, String newPrefTravelMode) {
        prefTravelModes[fieldIndex]=newPrefTravelMode;
    }
    public void setMeetPoints(MeetPoint[ ] newMeetPoints){
        meetPoints=newMeetPoints;
        chosenMeetPoint = meetPoints[0];
    }
    public void setChosenMeetPoint(int newChosenMeetPointIndex){
        chosenMeetPoint=meetPoints[newChosenMeetPointIndex];
    }
    public void setPrefLocationType(String newLocationType){ // means each session only has 1 location type
        prefLocationType=newLocationType;
    }
    public void setUserID(String joinUserID,Location startLocation, String prefTravelMode){
        if (userIDs[0]==null) {
            userIDs[0]=joinUserID;
        } else {
            userIDs[1]=joinUserID;
        }
        startLocations[userIDToIndex(joinUserID)]=startLocation;
        prefTravelModes[userIDToIndex(joinUserID)]=prefTravelMode;
        numOfUsers++;
    }
    public void setCalculated (boolean bool) {
        calculated = bool;
    }
    public boolean removeUser(String userID){
        for (int i=0;i<numOfUsers;i++){
            if (userIDs[i].equals(userID)){
                userIDs[i]=null;
                numOfUsers--;
                return true;
            }
        }
        return false;

    }
    public int userIDToIndex (String userID){
        if (userIDs[0].equals(userID)){
            return 0;
        } else if (userIDs[1].equals(userID)) {
            return 1;
        }
        return -1;
    }

    public void print(Object o){System.out.println(o);}
}