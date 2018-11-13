
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class SessionManager implements SessionDirectAccessObject {

    public final static SessionManager sm = new SessionManager();

    private final static ArrayList<UserDetails> userArr = new ArrayList<>();
    //new attribute in class


    public ArrayList<UserDetails> getUserArray() {
        return userArr;
    }

    public void updateUserArray(UserDetails newUser) {
        userArr.add(newUser);
        UserDetailsSerialiser.Writer(userArr);

    }

    public String addUser(String name, String address, String travelMode) {
        Location newLoc = new Location(address);
        ArrayList<String> sessions = new ArrayList<>(); /**/
        String id = generateUserID();
        UserDetails newUser = new UserDetails(name, id, newLoc, travelMode, sessions);
        updateUserArray(newUser);
//        printUserDet(id);
        return id;
    }

    public void editUser(String userID, String name, String address, String travelMode) {
        UserDetails change = getUserDet(userID);
        Location newLoc = new Location(address);
        change.setName(name);
        change.setPrefStartLocation(newLoc);
        change.setPrefTravelMode(travelMode);
        UserDetailsSerialiser.Writer(userArr);
    }

    public ArrayList<Session> getUserSessions(String userID) {
        UserDetails user = getUserDet(userID);
        ArrayList<String> sessionIDs = user.getSessions();
        print("sessionid size: " + sessionIDs.size());
        for(String temp : sessionIDs){
            print(temp);
        }
        ArrayList<Session> sesslist = new ArrayList<>();
        for (String temp: sessionIDs) {
            Session thisSess = SessionsLog.getSession(temp);
            sesslist.add(thisSess);
        }
        return sesslist;
    }
    public Session createSession(String sessionTitle, String userID) {
//        printUserDet(userID);
        print("generating session id");
        String sessID = generateSessionID();
        print("session id: " + sessID);
        UserDetails thisUser = getUserDet(userID);
        Session s1 = new Session(sessID, sessionTitle, thisUser.getPrefStartLocation(), thisUser.getPrefTravelMode(), userID); // check the auto adding of start location
        thisUser.addSession(sessID);
        updateSessionsLog(s1);
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
        UserDetailsSerialiser.Writer(userArr);
        return s1;
    }

    public void editSessionTitle(String sessionID, String title) {
        Session changeSess = SessionsLog.getSession(sessionID);
        changeSess.setTitle(title);
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
    }

    public void editSessionprefLocationType(String sessionID, String userID, String prefLocType) { //no need userid?
        Session changeSess = SessionsLog.getSession(sessionID);
        changeSess.setPrefLocationType(prefLocType);
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
    }

    public void editSessionstartLocations(String sessionID, int fieldIndex, String address) {
        Session changeSess = SessionsLog.getSession(sessionID);
        Location newLoc = new Location(address);
        changeSess.setStartLocation(fieldIndex, newLoc);
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
    }

    public void editSessionsprefTravelModes(String sessionID, int fieldIndex, String travelMode) {
        Session changeSess = SessionsLog.getSession(sessionID);
        changeSess.setPrefTravelModes(fieldIndex, travelMode);
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
    }

    public void updateSessionsLog(Session sess) {
        if (SessionsLog.getSession(sess.getSessionID()) == null) {
            SessionsLog.addSession(sess);
            SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
        }
    }

    public boolean joinSession(String sessID, String userID) {
        UserDetails secondUser = getUserDet(userID);
        secondUser.addSession(sessID);
        Session thisSess = SessionsLog.getSession(sessID);
        if (thisSess.getNumOfUsers() == 1) {
            thisSess.setUserID(userID, secondUser.getPrefStartLocation(), secondUser.getPrefTravelMode());
            SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
            UserDetailsSerialiser.Writer(userArr);
            return true;
        }
        return false;
    }

    public Session getSession(String sessID) {
        return SessionsLog.getSession(sessID);
    }

    public void changeTravelMode(String userID, String newMode) {
        UserDetails thisUser = getUserDet(userID);
        thisUser.setPrefTravelMode(newMode);
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
    }


    public void calculateResult(String sessID) throws Exception {
        Session thisSession = SessionsLog.getSession(sessID);
        RouteOptimiser ro = new RouteOptimiser();
        //RO wants separate strings but this is an array.
        Location[] loc = thisSession.getStartLocations();
        ArrayList<MeetPoint> possibleMPs = ro.findPossibleMP(loc[0].getName(), loc[1].getName(),
                thisSession.getPrefLocationType(), thisSession.getPrefTravelModes()[0], thisSession.getPrefTravelModes()[1]);
        MeetPoint[] mpArr = getMPArr(possibleMPs);
        thisSession.setMeetPoints(mpArr);
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
    }


    public MeetPoint[] getMPArr(ArrayList<MeetPoint> mpAList) {
        MeetPoint[] mpArr = new MeetPoint[5];
        for (int i=0; i<5; i++) {
            mpArr[i] = mpAList.get(i);
        }
        return mpArr;
    }


    public void updateChosenMeetPoint(String userID, String sessID, int BestResult) {
        Session newMP = SessionsLog.getSession(sessID);
        newMP.setChosenMeetPoint(BestResult);
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
    }


    public boolean removeSession(String sessID, String userID) {
        boolean removed = false;
        UserDetails thisUser = getUserDet(userID);
        thisUser.removeSession(sessID);
        Session checkSess = SessionsLog.getSession(sessID);
        removed = checkSess.removeUser(userID);
        if (checkSess.getNumOfUsers() == 0) {
            SessionsLog.removeSession(checkSess);
            removed = true;
        }
        SessionsLogSerialiser.Writer(SessionsLog.getAllSessions());
        UserDetailsSerialiser.Writer(userArr);
        return removed;
    }


    //added method to generate session ID.
    private String generateSessionID() {
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();

        char a = alpha.charAt(rand.nextInt(52));
        String b = Integer.toString(rand.nextInt(1000));
        char c = alpha.charAt(rand.nextInt(52));
        String sessID = a + b + c;

        if (SessionsLog.getSession(sessID) == null) {
            return sessID;
        } else {
            print("please");
            return generateSessionID();
        }

    }

    private String generateUserID() {
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();

        char a = alpha.charAt(rand.nextInt(52));
        String b = Integer.toString(rand.nextInt(100));
        char c = alpha.charAt(rand.nextInt(52));
        String userID = a + b + c;

        if (!userArr.contains(userID)) {
            return userID;
        } else {
            return generateUserID();
        }

    }


    //adedd method to get UserDetails
    public UserDetails getUserDet(String userID) {
        for (UserDetails temp : userArr) {
            if (temp.getUserID().equals(userID) ) {
                return temp;
            }
        }
        return null;
    }
    public void setUserArr(ArrayList<UserDetails> list){
        userArr.addAll(list);
    }

    static void print(Object o) {
        System.out.println(o);
    }

    private void saveSession(ArrayList<Session> sessList) {
        SessionsLogSerialiser.Writer(sessList);
    }

    private ArrayList<Session> readSession() {
        return SessionsLogSerialiser.Parser();
    }

    private void saveUser(ArrayList<UserDetails> userlist) {
        UserDetailsSerialiser.Writer(userlist);

    }

    private ArrayList<UserDetails> readUser(){
        return UserDetailsSerialiser.Parser();

    }

//    public void printUserDet(String userID) {
//        for (UserDetails temp : userArr) {
//            System.out.println(temp.getUserID());
//            System.out.println(temp.getName());
//        }

    }

