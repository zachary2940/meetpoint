import java.io.*;
import java.util.ArrayList;

public class UserDetailsSerialiser {

    public static void Writer(ArrayList<UserDetails> list) {
        String serializeFileName = "userlogdata.ser";

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(serializeFileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            //The object is being persisted here
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            //statements
            System.out.println("A total of " + list.size() + " were serialised into " + serializeFileName + ".");
        } catch (IOException ioe) {
            //Close all I/O streams
            ioe.printStackTrace();
            //Handle the exception here
        }
    }

    public static ArrayList<UserDetails> Parser() {
        ArrayList<UserDetails> list = new ArrayList<>();
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        String serializedFileName = "userlogdata.ser";

        try {
            fileInputStream = new FileInputStream(serializedFileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            list = (ArrayList<UserDetails>) objectInputStream.readObject();
            objectInputStream.close();
            //System out to test
            for (int i=0;i<list.size();i++){
                System.out.println("User name: " + list.get(i).getName() + " was parsed successfully.");
                System.out.println("Below are the session ids for the user: ");
                for (int j = 0; j < list.get(i).getSessions().size(); j++) {
                    System.out.println("User: " + list.get(i).getName() + " has session title: " + list.get(i).getSessions().get(j) + " within.");
                }

            }
            System.out.println("A total of " + list.size() + " users were loaded from the SER file.");

        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe.getMessage());
            //Close all I/O streams
            //Handle the exception here
        } catch (IOException ioe) {
            ioe.printStackTrace();
            //Close all I/O streams
            //Handle the exception here
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            //Close all I/O streams
            //Handle the exception here
        }
        return list;
    }
}