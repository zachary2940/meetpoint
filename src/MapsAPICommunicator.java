import java.io.*;
import java.net.*;
import org.json.*;

public class MapsAPICommunicator {
    private String basicURL = "https://maps.googleapis.com/maps/api/";
    private String key = "&key=AIzaSyDTUpNA98QYCtbUyb3H_vMeUk3oroJysko";

    public MapsAPICommunicator(){}

    private JSONObject getHTML(String urlToRead) throws IOException{
        StringBuilder result = new StringBuilder();  //create new string builder
        URL url = new URL(urlToRead); //new object url
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //conn
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        String json = result.toString();
        return new JSONObject(json);
    }

    public double[] getCoordinates(String address) throws Exception { //method
        String new_address = address.replaceAll(" ","_");
        String urlToRead = basicURL + "geocode/json?address=Singapore%20" + new_address + "&sensor=false&components=country:SG" + key;
        JSONObject arr = getHTML(urlToRead);
        String status = getHTML(urlToRead).getString("status");
        if (status.equals("ZERO_RESULTS")){
            throw new notinSGException();
        }
        else if (arr.getJSONArray("results").getJSONObject(0).has("partial_match")){
            throw new notinSGException();
        }
        else{
            JSONObject obj1 = arr.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            double latitude = obj1.getDouble("lat");
            double longitude = obj1.getDouble("lng");
            double[] location= new double[2];
            location[0]= latitude;
            location[1]= longitude;
            return location;
        }
    }

    public String getImageURL(String origin, String meetpoint,String modeoftransport,int i) throws Exception {
//        if(modeoftransport=="transit"){
//            System.out.println("hello");
//            String urlToRead_1 = basicURL + "directions/json?origin=" + origin + "&destination=" + meetpoint + "&mode=" + modeoftransport + key;
//            JSONArray arr_1 = getHTML(urlToRead_1).getJSONArray("routes");
//            String polyline_data_1 = arr_1.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
//            System.out.println(urlToRead_1);
//            String urlToRead_2 = basicURL + "directions/json?origin=" + destination + "&destination=" + meetpoint + "&mode=" + modeoftransport + key;
//            JSONArray arr_2 = getHTML(urlToRead_2).getJSONArray("routes");
//            String polyline_data_2 = arr_2.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
//            System.out.println(urlToRead_2);
//            String image_details = "staticmap?size=400x400&markers=size:large|color:red|label:A|"+ origin + "&markers=size:large|color:blue|label:C|" + destination + "&markers=size:large|color:green|label:M|" + meetpoint + "&center=1.3521,103.8198&zoom=10.5&path=weight:4|color:red|enc:";
//            System.out.println(basicURL + image_details + polyline_data_1 /*+ polyline_data_2*/ + key);
//            return basicURL + image_details  + polyline_data_1 + polyline_data_2 + key;
//        }
//        else {
        String urlToRead = basicURL + "directions/json?origin=" + origin + "&destination=" + meetpoint + "&mode=" + modeoftransport + key;
        JSONArray arr = getHTML(urlToRead).getJSONArray("routes");
        String polyline_data = arr.getJSONObject(0).getJSONObject("overview_polyline").getString("points");
        String image_details = "staticmap?" + "center=" + meetpoint + "zoom=18&size=500x500&markers=size:large|color:red|label:" + i + "|" + origin + "&markers=size:large|color:green|label:M|" + meetpoint + "&center=1.3521,103.8198&path=weight:5|color:red|enc:";
        //System.out.println(basicURL + image_details + polyline_data + key);
        return basicURL + image_details + polyline_data + key;
//        }
    }

    public Double getDuration(String origin, String meetpointlat, String meetpointlong,String modeoftransport) throws Exception{
        String urlToRead = basicURL + "distancematrix/json?origins=" + origin + "&destinations=" + meetpointlat +"," + meetpointlong + "&mode=" + modeoftransport + key;
        JSONArray arr = getHTML(urlToRead).getJSONArray("rows");
        return arr.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getDouble("value");
    }
}


/**
 * modeoftransport
 * bicycling (few), transit, walking, driving
 * get 2 polyline datas from transit and combine to get route, keep markers the same
 *https://maps.googleapis.com/maps/api/distancematrix/json?origins= +&destinations=1.3725,103.9496|1.3404,%20103.7090&key=AIzaSyDTUpNA98QYCtbUyb3H_vMeUk3oroJysko
 */
