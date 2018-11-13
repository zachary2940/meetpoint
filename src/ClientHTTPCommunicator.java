import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import com.sun.net.httpserver.Headers;
//import jdk.nashorn.internal.runtime.ECMAException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * do i need to keep refreshing
 */
public class ClientHTTPCommunicator {

    public static void main(String[] args) throws Exception {

        SessionManager.sm.setUserArr(UserDetailsSerialiser.Parser());

        SessionsLog.setSessions(SessionsLogSerialiser.Parser());


        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        System.out.println("Listening for connection on port 8080 ....");
        server.createContext("/meetpoint", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
//            String response = "This is the response";
            System.out.println("\n" + t.getRemoteAddress() + " Server Connected");// prints out ip
            OutputStream out = t.getResponseBody(); //should i use printwriter here
//            out.write(response.getBytes()); //writes to the http page
//            System.out.println(t.getResponseBody());
//            out.close();
            //Headers h = t.getResponseHeaders();
            //h.add("Content-Type", "application/json");

            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            int b;
            StringBuilder buf = new StringBuilder(512); // From now on, the right way of moving from bytes to utf-8 characters:
            try {
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }
            } catch (Exception e) {
                if (buf == null) {
                    System.out.println("shit");
                }
            }
//            System.out.println(buf);

            br.close();
            isr.close();
            String jsonstring = buf.toString(); //works for json
            JSONObject json = new JSONObject(jsonstring);
            System.out.println(json);
            String choice = json.getString("method");
            System.out.println("Method called: "+choice);
            switch (choice) {
                case "updateUser":
                    Object userId_0 = json.get("userId");
                    print("user id is " + userId_0);
                    if(userId_0.equals(null)) {
                        String name = json.getString("name");
                        String defaultStartAddress = json.getString("defaultStartAddress");
                        String defaultTravelMode = json.getString("defaultTravelMode");
                        print("adding user...");
                        String userId = SessionManager.sm.addUser(name, defaultStartAddress, defaultTravelMode);
                        print("UserID: " + userId);
                        print("Updated User");
                        JSONObject userDetails = new JSONObject();
                        userDetails.put("result", "O");
                        userDetails.put("userId", userId);
                        t.sendResponseHeaders(200, userDetails.toString().length());
                        out.write(userDetails.toString().getBytes());
                    }
                    else {
                        String name = json.getString("name");
                        String defaultStartAddress = json.getString("defaultStartAddress");
                        String defaultTravelMode = json.getString("defaultTravelMode");
                        SessionManager.sm.editUser(userId_0.toString(),name,defaultStartAddress,defaultTravelMode);
                        JSONObject userDetails = new JSONObject();
                        userDetails.put("result", "O");
                        userDetails.put("userId", userId_0);
                        t.sendResponseHeaders(200, userDetails.toString().length());
                        out.write(userDetails.toString().getBytes());
                    }

                    break;

                case "createSession":
                    String userId_2 = json.getString("userId");
                    print("userid: " + userId_2);
                    String sessionTitle = json.getString("sessionTitle");
                    print((SessionManager.sm.getUserArray()));
                    UserDetails user1 = SessionManager.sm.getUserDet(userId_2);
                    print(user1.getName());
                    try{
                        print(SessionManager.sm.getUserDet(userId_2).getName()); // failed here, get userDet failed
                    }
                    catch (Exception e){
                        print(e.getMessage());
                    }
                    try {
                        print("creating session");
                        Session newSession = SessionManager.sm.createSession(sessionTitle, userId_2); // maybe user details not stored properly. YES, searching function spoil?
//                        print("testing");
                        String sessionId = newSession.getSessionID();
                        UserDetails user_2 = SessionManager.sm.getUserDet(userId_2);
                        String name_2 = user_2.getName();
                        String prefTravelMode = user_2.getPrefTravelMode();
                        String prefStartLocation = user_2.getPrefStartLocation().getName();
                        JSONObject sessionJson = new JSONObject();
                        sessionJson.put("result", "O");
                        sessionJson.put("sessionId", sessionId);
                        sessionJson.put("U1N", name_2);
                        sessionJson.put("U1T", prefTravelMode);
                        sessionJson.put("U1A", prefStartLocation);
                        t.sendResponseHeaders(200, sessionJson.toString().length());
                        out.write(sessionJson.toString().getBytes());
                    }
                    catch (Exception e){
                        print(e.getMessage());
                    }
                    break;

                case "getSessions": //might have null pointer for get sessions
                    String userId_getSessions = json.getString("userId");
                    print(userId_getSessions);
                    ArrayList<Session> sessions = SessionManager.sm.getUserSessions(userId_getSessions);
                    JSONObject OverLordList = new JSONObject();
                    OverLordList.put("result", "O");
                    System.out.println("Retrieved Sessions");
                    JSONArray sessionArray = new JSONArray();
                    print("session size: " + sessions.size());
                    for(Session sesh: sessions){
                        print(sesh.getTitle());
                    }
                    for(int i=0; i<sessions.size();i++){
                        JSONObject sessionObject = new JSONObject();
                        Session sesh = sessions.get(i); // getting session object
                        String sessionId_2 = sesh.getSessionID(); //so nithya has a method
                        String title = sesh.getTitle();
                        MeetPoint chosenMeetPoint = sesh.getChosenMeetPoint();
                        String meetpointIndex = String.valueOf(sesh.getChosenMeetPointIndex(chosenMeetPoint));
                        sessionObject.put("sessionId" , sessionId_2);
                        print(sessionId_2); //
                        sessionObject.put("title", title);
                        print(title); //
                        sessionObject.put("chosenMeetpoint", meetpointIndex);
                        print(meetpointIndex); //
                        JSONArray meetpointArray = new JSONArray();
                        MeetPoint[] meetPoints = sesh.getMeetPoints();
                        if (meetPoints[0]!=null) {
                            for(int j= 0; j<meetPoints.length;j++ ){
                                print("fetching meetpoint of index " + j);
                                JSONObject meetpointObject = new JSONObject();
                                MeetPoint meetPoint = meetPoints[j];
                                meetpointObject.put("routeImage", meetPoint.getMeetPointImage());
//                                meetpointObject.put("routeImage", "");
                                meetpointObject.put("routeImage2", meetPoint.getMeetPointImage2());
//                                meetpointObject.put("routeImage2", "");
                                meetpointObject.put("name", meetPoint.getName());
                                JSONObject coordinates = new JSONObject();
                                double[] coord = meetPoint.getCoordinates();
                                String lat = String.valueOf(coord[0]);
                                String lon = String.valueOf(coord[1]);
                                coordinates.put("lat", lat);
                                coordinates.put("lon", lon);
                                meetpointObject.put("coordinates", coordinates);
                                meetpointArray.put(meetpointObject);
                            }
                        }

                        print("got meeetpoint array");
                        sessionObject.put("meetpoints", meetpointArray);

                        /**
                         * 						prefLocationType : <pref_location_type> of session?
                         * 						U1N : <name_of_first_user>
                         * 						U1T : <travel_mode_of_first_user> //we are only allowing 1 same mode first
                         * 						U1A : <address_of_first_user>
                         * 						U2N : <name_of_second_user> 				OR empty string ''
                         * 						U2T : <travel_mode_of_first_user> 	OR empty string ''
                         * 						U2A : <address_of_first_user> 			OR empty string ''
                         */
                        print("preparing to get location type");
                        String prefLocationType = sesh.getPrefLocationType();
                        print(prefLocationType);
                        sessionObject.put("prefLocationType", prefLocationType);
                        String[] userIds = sesh.getUserIDs();
                        print(userIds[0] + "," + userIds[1]);

                        String userName1 = "";
                        String userName2 = "";
                        if (userIds[0] != null) {
                            UserDetails userDetails1 = SessionManager.sm.getUserDet(userIds[0]);
                            userName1 = userDetails1.getName();
                        }
                        if (userIds[1] != null) {
                            UserDetails userDetails2 = SessionManager.sm.getUserDet(userIds[1]);
                            userName2 = userDetails2.getName();
                        }
                        String user1LocationName = sesh.getStartLocations()[0].getName();
                        print("user1loc: " +user1LocationName);
                        String user1PrefTravelMode = sesh.getPrefTravelModes()[0]
                                ;
                        print("user1trav: " +user1PrefTravelMode);
                        sessionObject.put("U1N", userName1).put("U1T", user1PrefTravelMode).put("U1A", user1LocationName);
                        String user2LocationName = sesh.getStartLocations()[1] != null ? sesh.getStartLocations()[1].getName() : "";
                        String user2PrefTravelMode = sesh.getPrefTravelModes()[1];
                        sessionObject.put("U2N", userName2).put("U2T", user2PrefTravelMode).put("U2A", user2LocationName);
                        sessionArray.put(sessionObject);
                    }
                    OverLordList.put("sessions", sessionArray);
                    print(OverLordList);
                    t.sendResponseHeaders(200, OverLordList.toString().length());
                    out.write(OverLordList.toString().getBytes());
                    break;

                case "joinSession":
                    String userId_4 = json.getString("userId");
                    String sessionId_2 = json.getString("sessionId");
                    Session joinedSession = SessionManager.sm.getSession(sessionId_2);
                    if(joinedSession==null){
                        JSONObject noSession = new JSONObject();
                        noSession.put("result", "X");
                        t.sendResponseHeaders(200, noSession.toString().length());
                        out.write(noSession.toString().getBytes());
                    }
                    JSONObject sessionJson_2 = new JSONObject();
                    if(joinedSession.getUserIDs()[0]!= null) {
                        String userId_joinSession = joinedSession.getUserIDs()[0];
                        String name2_3 = SessionManager.sm.getUserDet(userId_joinSession).getName();
                        String prefTravelMode2_3 = SessionManager.sm.getUserDet(userId_joinSession).getPrefTravelMode();
                        String prefStartLocation2_3 = SessionManager.sm.getUserDet(userId_joinSession).getPrefStartLocation().getName();
                        sessionJson_2.put("U1N", name2_3);
                        sessionJson_2.put("U1T", prefTravelMode2_3);
                        sessionJson_2.put("U1A", prefStartLocation2_3);
                    }
                    else {
                        String userId_joinSession = joinedSession.getUserIDs()[1];
                        String name2_3 = SessionManager.sm.getUserDet(userId_joinSession).getName();
                        String prefTravelMode2_3 = SessionManager.sm.getUserDet(userId_joinSession).getPrefTravelMode();
                        String prefStartLocation2_3 = SessionManager.sm.getUserDet(userId_joinSession).getPrefStartLocation().getName();
                        sessionJson_2.put("U1N", name2_3);
                        sessionJson_2.put("U1T", prefTravelMode2_3);
                        sessionJson_2.put("U1A", prefStartLocation2_3);
                    }

                    if(!SessionManager.sm.joinSession(sessionId_2,userId_4)){
                        JSONObject tooManyUsers = new JSONObject();
                        t.sendResponseHeaders(200, tooManyUsers.toString().length());
                        out.write(tooManyUsers.toString().getBytes());
                    }
                    UserDetails user_3 = SessionManager.sm.getUserDet(userId_4);
                    String name_3 = user_3.getName();
                    String prefTravelMode_3 = user_3.getPrefTravelMode();
                    String prefStartLocation_3 = user_3.getPrefStartLocation().getName();
                    String sessionTitle_2 = joinedSession.getTitle();
                    MeetPoint chosenMeetpoint = joinedSession.getChosenMeetPoint();
                    String meetpointIndex = String.valueOf(joinedSession.getChosenMeetPointIndex(chosenMeetpoint));

                    sessionJson_2.put("result", "O");
                    sessionJson_2.put("sessionId", sessionId_2);
                    sessionJson_2.put("title", sessionTitle_2);
                    sessionJson_2.put("chosenMeetpoint", meetpointIndex);

                    sessionJson_2.put("U2N", name_3);
                    sessionJson_2.put("U2T", prefTravelMode_3);
                    sessionJson_2.put("U2A", prefStartLocation_3);
                    MeetPoint[] meetPoints = joinedSession.getMeetPoints();

                    JSONArray meetpointArray = new JSONArray();
                    if (meetPoints[0]!=null) {
                        for (int i = 0; i < meetPoints.length; i++) {
                            JSONObject meetpointObject = new JSONObject();
                            MeetPoint meetPoint = meetPoints[i];
                            meetpointObject.put("routeImage", meetPoint.getMeetPointImage());
                            meetpointObject.put("routeImage2", meetPoint.getMeetPointImage2());
                            meetpointObject.put("name", meetPoint.getName());
                            JSONObject coordinates = new JSONObject();
                            double[] coord = meetPoint.getCoordinates();
                            String lat = String.valueOf(coord[0]);
                            String lon = String.valueOf(coord[1]);
                            coordinates.put("lat", lat);
                            coordinates.put("lon", lon);
                            meetpointObject.put("coordinates", coordinates);
                            meetpointArray.put(meetpointObject);
                        }
                    }
                    sessionJson_2.put("meetpoints", meetpointArray);
                    print(sessionJson_2);
                    t.sendResponseHeaders(200, sessionJson_2.toString().length());
                    out.write(sessionJson_2.toString().getBytes());
                    break;

                case "deleteSession":
                    String sessionId_1 = json.getString("sessionId");
                    String userId_5 = json.getString("userId");
                    print("deleting");
                    if(SessionManager.sm.removeSession(sessionId_1, userId_5)) {
                        JSONObject deleteSession = new JSONObject();
                        deleteSession.put("result", "O");
                        t.sendResponseHeaders(200, deleteSession.toString().length());
                        out.write(deleteSession.toString().getBytes());
                        print("session or user deleted");
                    }
                    else {
                        JSONObject failedDelete = new JSONObject();
                        failedDelete.put("result", "X");
                        t.sendResponseHeaders(200, failedDelete.toString().length());
                        out.write(failedDelete.toString().getBytes());
                        print("session not deleted");
                    }
                    break;

                case "calculate":
                    String sessionId_3 = json.getString("sessionId"); // how to get correct session
                    try {
                        SessionManager.sm.calculateResult(sessionId_3); //check if start location is filled in
                    }
                    catch (Exception e){
                        print(e.getMessage());
                        print(SessionManager.sm.getSession(sessionId_3).getStartLocations()[0].getName());
                        print(SessionManager.sm.getSession(sessionId_3).getStartLocations()[1].getName());
                        JSONObject nullException = new JSONObject();
                        print("XXXXX");
                        nullException.put("result","X");
                        t.sendResponseHeaders(200, nullException.toString().length());
                        out.write(nullException.toString().getBytes());
                    }
                    print("calculated");
                    Session calculatedSession = SessionsLog.getSession(sessionId_3);
                    MeetPoint[] meetPoints_2 = calculatedSession.getMeetPoints();
                    if(meetPoints_2[0]==null){
                        JSONObject failedCalculate = new JSONObject();
                        failedCalculate.put("result", "X");
                        t.sendResponseHeaders(200, failedCalculate.toString().length());
                        out.write(failedCalculate.toString().getBytes());
                    }
                    print(meetPoints_2[0].getName());
                    JSONObject meetpointList_2 = new JSONObject();
                    JSONArray meetpointArray_2 = new JSONArray();
                    for(int i=0; i<meetPoints_2.length; i++) {
                        JSONObject meetpointObject = new JSONObject();
                        MeetPoint meetPoint = meetPoints_2[i];
                        meetpointObject.put("routeImage", "");
                        meetpointObject.put("routeImage", meetPoint.getMeetPointImage());
                        meetpointObject.put("routeImage2", "");
                        meetpointObject.put("routeImage2", meetPoint.getMeetPointImage2());
                        meetpointObject.put("name", meetPoint.getName());
                        JSONObject coordinates = new JSONObject();
                        double[] coord = meetPoint.getCoordinates();
                        String lat = String.valueOf(coord[0]);
                        String lon = String.valueOf(coord[1]);
                        coordinates.put("lat", lat);
                        coordinates.put("lon", lon);
                        meetpointObject.put("coordinates", coordinates);
                        meetpointArray_2.put(meetpointObject);
                    }
                    meetpointList_2.put("result", "O");
                    meetpointList_2.put("meetpoints", meetpointArray_2);
                    print(meetpointList_2);
                    t.sendResponseHeaders(200, meetpointList_2.toString().length());
                    out.write(meetpointList_2.toString().getBytes());
                    print("done");
                    break;

                case "editSession":
                    String userId_6 = json.getString("userId"); // i dont need to take this unless it is to verify
                    String sessionId_4 = json.getString("sessionId");
                    String fieldType = json.getString("field");
                    print(fieldType);
                    String value = json.getString("value"); //chosen meetpoint index
                    print(value);
                    Session editingSession = SessionManager.sm.getSession(sessionId_4);
                    String[] userIds = editingSession.getUserIDs();
                    if(userId_6.equals(userIds[0]) || userId_6.equals(userIds[1]) ) {
                        /**
                         * Editing is based on field index
                         */
                        switch (fieldType) {
                            case "LT":
                                SessionManager.sm.editSessionprefLocationType(sessionId_4, userId_6, value);
                                break;
                            case "CM":
                                int chosenMeetPointIndex = Integer.valueOf(value);
                                SessionManager.sm.updateChosenMeetPoint(userId_6, sessionId_4, chosenMeetPointIndex);
                                break;
                            case "U1A":
                                SessionManager.sm.editSessionstartLocations(sessionId_4, 0, value);
                                break;
                            case "U1T":
                                SessionManager.sm.editSessionsprefTravelModes(sessionId_4, 0, value);
                                break;
                            case "U2A":
                                SessionManager.sm.editSessionstartLocations(sessionId_4, 1, value); // even if user 2 field is edited by user 1, it still belongs to user 2
                                break;
                            case "U2T":
                                SessionManager.sm.editSessionsprefTravelModes(sessionId_4, 1, value);
                                break;
                            default:
                                JSONObject defaultJson = new JSONObject();
                                defaultJson.put("result", "X");
                                t.sendResponseHeaders(200, defaultJson.toString().length());
                                out.write(defaultJson.toString().getBytes());
                        }
                    }
                    else {
                        JSONObject userNotFoundJson = new JSONObject();
                        userNotFoundJson.put("result", "X");
                        t.sendResponseHeaders(200, userNotFoundJson.toString().length());
                        out.write(userNotFoundJson.toString().getBytes());
                    }

                    JSONObject editSession = new JSONObject();
                    editSession.put("result", "O");
                    t.sendResponseHeaders(200, editSession.toString().length());
                    out.write(editSession.toString().getBytes());
                    break;

                default:
                    JSONObject defaultJSON = new JSONObject();
                    defaultJSON.put("result", "X");
                    t.sendResponseHeaders(200, defaultJSON.toString().length());
                    out.write(defaultJSON.toString().getBytes());
                }
            }
        }

    static void print(Object o) {
        System.out.println(o);
    }
}

// The resulting string is: buf.toString()
// and the number of BYTES (not utf-8 characters) from the body is: buf.length()





/**
 * http://localhost:8080/meetpoint
 *
 * JSONObject obj = new JSONObject();
 * JSONArray arr = new JSONArray();
 *
 * LEGEND (for fields):
 * LT ==> Location type
 * CM ==> chosenmeetpoint (index on meetpoints array)
 * U1A ==> address of user 1
 * U1T ==> travel mode of user 1
 * U2A ==> address of user 2 //how do i know which is user 1 or 2
 * U2T ==> travel mode of user 2
 */
