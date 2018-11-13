import java.util.ArrayList;
import java.io.*;

public class SessionsLogSerialiser {

    public static void Writer(ArrayList<Session> sessions) {
        String serializeFileName = "sessionslogdata.ser";
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(serializeFileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            //The object is being persisted here
            objectOutputStream.writeObject(sessions);
            objectOutputStream.close();
            //statements
            System.out.println("A total of " + sessions.size() + " were serialised into " + serializeFileName + ".");
        } catch (IOException ioe) {
            //Close all I/O streams
            ioe.printStackTrace();
            //Handle the exception here
        }
    }

    public static ArrayList<Session> Parser() {
        ArrayList<Session> sessions = new ArrayList<>();
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        String serializedFileName = "sessionslogdata.ser";
        try
        {
            fileInputStream = new FileInputStream(serializedFileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            sessions = (ArrayList<Session>) objectInputStream.readObject();
            objectInputStream.close();
            //System out to test
            for (int i=0;i<sessions.size();i++){
                System.out.println("Session title: " + sessions.get(i).getTitle() + " was parsed successfully.");
            }
            System.out.println("A total of " + sessions.size() + " sessions were loaded from the SER file.");

        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("File not found: "+fnfe.getMessage());
            //Close all I/O streams
            //Handle the exception here
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            //Close all I/O streams
            //Handle the exception here
        }
        catch(ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
            //Close all I/O streams
            //Handle the exception here
        }
        return sessions;
    }
}

