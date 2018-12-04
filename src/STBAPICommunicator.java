

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import org.json.*;

public class STBAPICommunicator {
    private String basicURL = "https://tih-api.stb.gov.sg/map/v1/search/dataset/";
    private String key = "&apikey=";

    public ArrayList<JSONObject> getAddress(String place, String latitude_m, String longitude_m, String radius) throws Exception {
        String urlToRead = basicURL + place + "?location=" + latitude_m + "%2C" + longitude_m + "&radius=" + radius + key;
        StringBuilder result = new StringBuilder();  //create new string builder
        URL url = new URL(urlToRead); //new object url
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //conn
        conn.setRequestMethod("GET");
        int statusCode = conn.getResponseCode();
        if (statusCode == 404){
            throw new RecordnotfoundException();
        } else if (statusCode == 200){
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String json = result.toString();
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("data");
            ArrayList<JSONObject> meetpointList = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                String name = arr.getJSONObject(i).getString("name");
                Double latitude = arr.getJSONObject(i).getJSONObject("location").getDouble("latitude");
                Double longitude = arr.getJSONObject(i).getJSONObject("location").getDouble("longitude");
                JSONObject meetpoint = new JSONObject();
                meetpoint.put("name", new String(name));
                meetpoint.put("latitude", new Double(latitude));
                meetpoint.put("longitude", new Double(longitude)); // can i directly put inside a meetpint object???
                meetpointList.add(meetpoint);
            }
            /**How to Parse JSON object
             * Access arraylist by using get, then run a for loop to get all the json objects, then get string(whatever u want)
             * System.out.println(meetpointList.get(0).getString("name"));
             * */
            return meetpointList;
        } else {
            throw new RecordnotfoundException();
        }
    }
}

//1.282302, 103.8585308 up to 4dp
//https://tih-api.stb.gov.sg/map/v1/search/dataset/shops?location=1.282302%2C103.8585308&apikey=10ddFoEJRJQTJLvpxaF1nmQ6AClmSI2W