import org.json.JSONObject;
import org.json.JSONString;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ClientCommunicator {
//
//    public String sendToClient(String data){
//
//    }
//
//    public void getSession(Session session){
//
//    }
//
//    public void getSessions( Session sessions){
//
//    }
//
//    public void parseRequest(){
//
//    }

    /**
     * Runs the server.
//     */
//    public static JSONObject setupReceiver() throws IOException{
//        System.out.println("Enter IP address of Client:");
//        Scanner sc = new Scanner(System.in);
//        String serverAddress = sc.nextLine();
//        Socket s = new Socket(serverAddress, 9090);
//        System.out.println("Ready to receive...");
//        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
//        String json = input.readLine(); //works for json
//        System.out.println(json);
//        return new JSONObject(json);
//    }
//
//    public static PrintWriter setupTransmitter() throws IOException {
//        ServerSocket listener = new ServerSocket(8080);
//        while (true) {
//            Socket socket = listener.accept();
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String json = input.readLine(); //works for json
//            System.out.println(json);
//            return out;
//        }
//    }



    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        MapsAPICommunicator mappie = new MapsAPICommunicator();
        STBAPICommunicator govie = new STBAPICommunicator();
        ServerSocket listener = new ServerSocket(9090); //server socket is diff from socket, dont need ip whereas the other one needs
        System.out.println("Server is listning on port: 9090...");
        while (true) {
            Socket socket = listener.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String jsonstring = input.readLine(); //works for json
            System.out.println(jsonstring);
//            JSONObject json = new JSONObject(jsonstring);
//            System.out.println(json);
//            String choice = json.getString("method");
//            System.out.println(choice);
//            switch (choice) {
//                case "updateUser":
//                    System.out.println("Updated User");
//                    UserDetails user = new UserDetails(json.getString("name"), Double.parseDouble(json.getString("userId")), SessionManager.createLocation(json.getString("defaultStartAddress")), json.getString("defaultTravelMode"), SessionManager.getSessions(json.getDouble("userId"))); //might have null pointer for get sessions
//                    SessionManager.addUser(user);
//                    JSONObject userDetails = new JSONObject();
//                    userDetails.put("result", "O");
//                    out.println(userDetails);
//                    break;
//
//                case "getSessions": //might have null pointer for get sessions
//                    System.out.println("Retrieved Sessions");
//
//                    ArrayList<Session> sessions = SessionManager.getSessions(json.getString("userId"));
//                    for(int i=0; i<sessions.size();i++){
//                        Session sesh = sessions.get(i);
//                        ArrayList<MeetPoint> meetPointArrayList = sesh.getMeetPoints();
//                        meetPointArrayList.get(i).getCoordinates();
//                    }
//                    sessionList.put("result", "O");
//                    out.println();
//                    break;
//
//                case "1":
//                    System.out.println("Enter origin, meetpoint, mode of transport");
//                    String origin_2 = sc.nextLine();
//                    origin = origin_2.replaceAll("\\s+", "");
//                    String meetpoint_2 = sc.nextLine();
//                    meetpoint = meetpoint_2.replaceAll("\\s+", "");
//                    String modeoftransport_2 = sc.nextLine();
//                    modeoftransport = modeoftransport_2.replaceAll("\\s+", "");
//                    System.out.println(mappie.getDuration(origin, meetpoint, modeoftransport));
//                    break;
//
//                case "2":
//                    System.out.println("Enter place, latitude, longitude, radius");
//                    String place = sc.nextLine();
//                    String latitude = sc.nextLine();
//                    String longitude = sc.nextLine();
//                    String radius = sc.nextLine();
//                    govie.getAddress(place, latitude, longitude, radius);
//                    break;
            }
        }
    }
//}


/**
 *
 *192.168.0.103
 *
 * Zachary's list of what the server needs
 * To Search:
 * 1. origin of both users
 * 2. preferred location type
 * 3. mode of transportation
 * 4. user id
 *
 * To update user:
 * method : updateUser
 * userId : <user_id>
 * name : <name_of_user>
 * defaultTravelMode : <default_travel_mode>
 * defaultStartName : <name_of_default_location>
 * defaultStartAddress : <literal_address_of_default_location>
 *
 * To Get Sessions:
 * method : getSessions
 * userId : <user_id>
 * Return a JSON of the list of sessions within which is a list of meetpoints
 *
 *	result : 'O'
 * 	0 :	{
 * 				sessionId : <id>
 * 				title : <title>
 * 				chosenMeetpoint :	<index_in_meetpoints_array>
 * 				meetpoints :	{
 * 												0 : {
 * 															routeImage : <url_to_image>
 * 															name : <name>
 * 															coordinates : {
 * 																							lat : <latitude>
 * 																							lon : <longitude>
 *                                                                                                                                                                                }
 * 														}
 *
 * To create Sessions
 * request body:
 * {
 * 	method : createSession
 * 	userId : <user_id>
 * 	sessionTitle : <title_of_session>
 * }
 *
 * 	result : 'O'
 * 	sessionId : <id>
 * 	U1N : <name_of_first_user>
 * 	U1T : <travel_mode_of_first_user>
 * 	U1A : <address_of_first_user>
 *
 * Zachary's list of what the client needs
 * From Meetpoint object:
 * 1. Name
 * 2. Duration
 * 3. MapURL
 *
 * From ???:
 * 1. Full map URL
 * 2.
 *
 *
 *         -generateToken() : String
 *         +getSessions() : List<Session_Client>
 *         +getLoadedSession : Session_List
 *         +fetchSession() : Future
 *         -_findSession(sessionId : String) : int
 *         +createSession(sessionTitle : String) : Future
 *         +joinSession(sessionId : String) : Future
 *         +deleteSession(sessionId : String) : Future
 *         +loadSession(sessionId : String) : Session_Client
 *         +requestSessionEdit(sessionId : String,  editCallback  : String, params : List<Map<String,String>>) : Future
 *         +sendLocalUserInfo(userDetails : UserDetails_Client) : Future
 *         +updateSession(edit : String) : void
 */