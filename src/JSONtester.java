import org.json.JSONArray;
import org.json.JSONObject;

public class JSONtester {

    public static void main(String[] args) throws Exception {
        JSONObject OverLordList = new JSONObject();
        OverLordList.put("result", "O");
        System.out.println("Retrieved Sessions");
        JSONArray sessionArray = new JSONArray();
        for(int i=0; i<3;i++){
            JSONObject sessionObject = new JSONObject();
//            Session sesh = sessions.get(i); // getting session object
//            String sessionId_2 = sesh.getSessionID();
//            String title = sesh.getTitle();
//            MeetPoint chosenMeetPoint = sesh.getChosenMeetPoint();
//            int meetpointIndex = sesh.getChosenMeetPointIndex(chosenMeetPoint);
            sessionObject.put("sessionId" , "sessionId");
            sessionObject.put("title", "title");
            sessionObject.put("chosenMeetpoint", "chosenMeetpoint");
            JSONArray meetpointArray = new JSONArray();
            JSONObject meetpointObject = new JSONObject();
//            MeetPoint[] meetPoints = sesh.getMeetPoints();
//            for(int j=0; j<3; j++) {
////                MeetPoint meetPoint = meetPoints[j];
//                meetpointObject.put("routeImage", "routeImage");
//                meetpointObject.put("name", "name");
//                JSONObject coordinates = new JSONObject();
////                double[] coord = meetPoint.getCoordinates();
////                double lat = coord[0];
////                double lon = coord[1];
//                coordinates.put("lat", "lat");
//                coordinates.put("lon", "lon");
//                meetpointObject.put("coordinates", coordinates);
//                meetpointArray.put(meetpointObject);
//            }
            sessionObject.put("meetpoints", meetpointArray);
            sessionArray.put(sessionObject);
        }
        OverLordList.put("sessions", sessionArray);
        print(OverLordList);
    }

    static void print(Object o) {
        System.out.println(o);
    }
}
