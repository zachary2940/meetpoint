//import java.util.ArrayList;
//
//public class SerialTesterApp {
//
//    public static void main(String[] args) {
//
//        String[] userIDs = new String[2];
//        userIDs[0] = "fdgh";
//        userIDs[1] = "retykjli";
//
//        double[] coordinates1 = new double[2];
//        coordinates1[0] = 12.34;
//        coordinates1[1] = 23.44;
//
//        double[] coordinates2 = new double[2];
//        coordinates2[0] = 56.98;
//        coordinates2[1] = 12.67;
//
//        Location test1 = new Location("location1", coordinates1);
//        Location test2 = new Location("location2", coordinates2);
//
//        Location[] locationarrray = new Location[2];
//        locationarrray[0] = test1;
//        locationarrray[1] = test2;
//
//        String[] travelarray = new String[2];
//        travelarray[0] = "Bus";
//        travelarray[1] = "Car";
//
//
//        //creating the MeetPoints and ChosenMeetPoint
//        //String name, double[] coordinates, String type, double[] travelDurations, String meetPointImage
//
//        double travelduration = 5.5;
//
//        MeetPoint meetpoint1 = new MeetPoint("name",coordinates1,"Mall",travelduration,"thisurlisgreat.com");
//        MeetPoint meetpoint2 = new MeetPoint("please",coordinates1,"Mall",travelduration,"thisurlisgreat.com");
//
//        MeetPoint[] meetpointarray = new MeetPoint[5];
//        meetpointarray[0] = meetpoint1;
//        meetpointarray[1] = meetpoint1;
//        meetpointarray[2] = meetpoint1;
//        meetpointarray[3] = meetpoint1;
//        meetpointarray[4] = meetpoint2;
//
//        //String sessionID, String title, Location[] startLocations, String[] prefTravelModes, MeetPoint[] meetPoints,
//        //                   MeetPoint chosenMeetPoint, String prefLocationType, double[] userIDs, int numOfUsers, boolean calculated
//        Session testSession1 = new Session("please work", "test", locationarrray, travelarray, meetpointarray,meetpoint2,"Mall",userIDs,2,true);
//        Session testSession2 = new Session("number 2", "what is this shit", locationarrray, travelarray,"Mall",userIDs,2,true);
//        Session testSession3 = new Session("please", "this is great", locationarrray, travelarray, meetpointarray,meetpoint2,"Mall",userIDs,1,false);
//
//        ArrayList<Session> testlog = new ArrayList<>();
//        testlog.add(testSession1);
//        testlog.add(testSession2);
//        testlog.add(testSession3);
//
//        //SessionsLogSerialiser.Writer(testlog);
//        SessionsLogSerialiser.Parser();
//    }
//}
