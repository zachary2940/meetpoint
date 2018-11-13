
import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {
        ArrayList<String> sessions = new ArrayList<>();
        Location CityView = new Location("CityView", new double[2]);
        UserDetails nithya = new UserDetails("nithya", "123", CityView, "Car", sessions);
        UserDetails siHao = new UserDetails("sihao", "456", CityView, "Car", sessions);
        SessionManager.sm.updateUserArray(nithya);
        SessionManager.sm.updateUserArray(siHao);

        ArrayList<UserDetails> userArr = SessionManager.sm.getUserArray();
        for (UserDetails user: userArr) {
            System.out.println(user.getName());
        }

        UserDetails who = SessionManager.sm.getUserDet("123");
        System.out.println(who.getName());
    }
}
