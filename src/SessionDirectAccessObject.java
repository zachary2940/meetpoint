
import java.util.ArrayList;

public interface SessionDirectAccessObject {

     Session createSession(String sessionTitle, String userID);
     void updateSessionsLog(Session sess);
     boolean joinSession(String sessID, String userID);
     boolean removeSession(String sessionID, String userID);

}
