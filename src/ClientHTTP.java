
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ClientHTTP {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        ClientHTTP http = new ClientHTTP();

//        System.out.println("Testing 1 - Send Http GET request");
//        http.sendGet();

        System.out.println("Testing 2 - Send Http POST request");
        http.sendPost();

    }

//    // HTTP GET request
//    private void sendGet() throws Exception {
//
//        String url = "http://localhost:8080/meetpoint";
//
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        // optional default is GET
//        con.setRequestMethod("GET");
//
//        //add request header
//        con.setRequestProperty("User-Agent", USER_AGENT);
//
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        //print result
//        System.out.println(response.toString());
//
//    }

    // HTTP POST request
    private void sendPost() throws Exception {
//        while (true) {
//            String urlParameters = "param1=a&param2=b&param3=c";

        String request = "http://localhost:8080/meetpoint?";
        URL url = new URL(request);

        while (true) {
            JSONObject test = new JSONObject();
            System.out.println("Enter your choice:");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
//                    print("Enter user id:");
                    test.put("method", "updateUser");
//                    test.put("userId", sc.nextLine());
                    print("Enter name");
                    test.put("name", sc.nextLine());
                    test.put("defaultTravelMode", "driving");
                    test.put("defaultStartName", "white sands");
                    test.put("defaultStartAddress", "white sands");
                    break;

                case 2: //create session
                    test.put("method", "createSession");
                    test.put("userId", "1234");
                    test.put("sessionTitle", "tester");
                    break;

                case 3: //calculate
                    test.put("method", "calculate");
                    test.put("sessionId", "1234");
                    break;


            }

            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setUseCaches(false);
                byte[] postData = test.toString().getBytes(StandardCharsets.UTF_8);
                conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(test.toString().getBytes());
//                wr.flush();
//                wr.close();

                try {
                    int responseCode = conn.getResponseCode(); /*persistent connection is handled transparently*/

                    print("wth");
                    System.out.println("\nSending 'POST' request to URL : " + url);
                    System.out.println("Post parameters : " + test);
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
//                    in.close();
//                    conn.disconnect();

                    //print result
                    System.out.println(response.toString()+"\n");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }




//        }create a new HttpURLConnection for each HTTP request
        }
    }

    static void print(Object o) {
        System.out.println(o);
    }
}