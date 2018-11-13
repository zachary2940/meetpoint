import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserTesterApp {

    public static void main(String[] args) {

        String name = "yo";
        String userID = "1234";
        double[] coordinates1 = new double[2];
        coordinates1[0] = 12.34;
        coordinates1[1] = 23.44;
        Location prefStartLocation = new Location("location1", coordinates1);
        String prefTravelMode = "Bus";
        String session1  = "nice";
        String session2 = "why please";
        ArrayList<String> sessions = new ArrayList<>();
        sessions.add(session1);
        sessions.add(session2);
        UserDetails userdetail1 = new UserDetails(name,userID,prefStartLocation,prefTravelMode,sessions);
        UserDetails userdetail2 = new UserDetails("please work",userID,prefStartLocation,prefTravelMode,sessions);
        UserDetails userdetail3 = new UserDetails("cmon",userID,prefStartLocation,prefTravelMode,sessions);

        ArrayList<UserDetails> list = new ArrayList<>();

        list.add(userdetail1);
        list.add(userdetail2);
        list.add(userdetail3);
        //UserDetailsSerialiser.Writer(list);
        UserDetailsSerialiser.Parser();
    }
}
