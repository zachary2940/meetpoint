import java.util.ArrayList;
public abstract class SessionsLog {

    static private ArrayList<Session> sessions = new ArrayList<>();

    static public ArrayList<Session> getSessions(){
        return sessions;
    }

    static public Session getSession(String sessionID){
        for(int i=0;i<sessions.size();i++){
            if (sessions.get(i).getSessionID().equals(sessionID)) {
                return sessions.get(i);
            }
        }
        return null;
    }

    static public void addSession(Session session){
        sessions.add(session);
    }

    static public double getNumSessions(){
        return sessions.size();
    }

    static public void removeSession(Session session){
        sessions.remove(session);
    }

    static public String[] getSessionNames(){
        String[] sessionNames = new String[sessions.size()];
        for (int i=0;i<sessions.size();i++){
            sessionNames[i]=sessions.get(i).getTitle();
        }
        return sessionNames;
    }

    static public ArrayList<Session> getUserSessionList(String userID) {
        ArrayList<Session> list = new ArrayList<>();
        for (int i=0;i<sessions.size();i++){
            if (sessions.get(i).getUserIDs()[0] == userID || sessions.get(i).getUserIDs()[1] == userID) {
                list.add(sessions.get(i));
            }
        }
        return list;
    }

    static public String[] getChosenMeetPoints(){
        //is this method even needed?
        String[] chosenMeetPoints = new String[sessions.size()];
        for (int i=0;i<sessions.size();i++){
            chosenMeetPoints[i]=sessions.get(i).getChosenMeetPoint().getName();
        }
        return chosenMeetPoints;
    }

    static public void setSessions(ArrayList<Session> list){
        sessions.addAll(list);
    }

    static public ArrayList<Session> getAllSessions(){
        return sessions;
    }
}