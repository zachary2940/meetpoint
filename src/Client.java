import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Enter IP address of Server:");
        Scanner sc = new Scanner(System.in);
        String serverAddress = sc.nextLine();
        Socket s = new Socket(serverAddress, 9090);
        while (true) {
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            JSONObject test = new JSONObject();
            test.put("method", "updateUser");
            test.put("userId", "1234");
            test.put("name", "zach");
            test.put("defaultTravelMode", "driving");
            test.put("defaultStartName", "white sands");
            test.put("defaultStartAddress", "white sands");
            out.println(test);

            System.out.println("Ready to receive...");
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String json = input.readLine(); //works for json
            System.out.println(json);
        }
    }
}
