
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RouteOptimiser {
    private ArrayList<MeetPoint> meetPointArrayList = new ArrayList<>();
    private STBAPICommunicator sAPI = new STBAPICommunicator();
    private MapsAPICommunicator mAPI = new MapsAPICommunicator();

    private double[] fetchDoubleCoordinates(String origin) throws Exception{
        return mAPI.getCoordinates(origin);
    }
    private String fetchStringCoordinates(String origin) throws Exception{
        double[] coordinates = mAPI.getCoordinates(origin);
        return coordinates[0] + "," + coordinates[1];
    }
    public double[] findMidPoint(String org1, String org2, String prefTravelMode1, String prefTravelMode2) throws Exception{

        double[] origin1 = fetchDoubleCoordinates(org1);
        System.out.println(fetchStringCoordinates(org1));
        double[] origin2 = fetchDoubleCoordinates(org2);

        double w1 = weightassignment(prefTravelMode1);
        double w2 = weightassignment(prefTravelMode2);

        double ww1 = w1/(w1+w2);
        double ww2 = w2/(w1+w2);

        System.out.println(ww1);
        System.out.println(ww2);

        double midlat = (ww1*origin1[0] + ww2*origin2[0]);
        double midlon = (ww1*origin1[1] + ww2*origin2[1]);

        double midpoint[];
        midpoint = new double[]{midlat,midlon};
        System.out.println(midpoint[0]);
        System.out.println(midpoint[1]);
        return midpoint;
    }

    private double weightassignment(String preftravelmode){
        switch (preftravelmode){
            case "driving":
                return 1;
            case "transit":
                return 2;
            case "walking":
                return 7;
            default:
                return 1;
        }
    }
    private MeetPoint fetchMeetPoint(String destination, double destlat, double destlon, String origin1, String origin2, String prefTravelMode1, String prefTravelMode2)throws Exception{
        MeetPoint mp = new MeetPoint();
        double[] coord = new double[]{destlat,destlon};
        mp.setCoordinates(coord);
        mp.setName(destination);
        double duration1 = mAPI.getDuration(fetchStringCoordinates(origin1),String.valueOf(destlat),String.valueOf(destlon),prefTravelMode1);
        double duration2 = mAPI.getDuration(fetchStringCoordinates(origin2),String.valueOf(destlat),String.valueOf(destlon),prefTravelMode2);
        mp.setTravelDuration(duration1);
        mp.setTravelDuration2(duration2);
        String dest = coord[0] + "," + coord[1];
        String url1 = mAPI.getImageURL(fetchStringCoordinates(origin1), dest, prefTravelMode1,1);
        mp.setMeetPointImage(url1);
        String url2 = mAPI.getImageURL(fetchStringCoordinates(origin2), dest, prefTravelMode2,2);
        mp.setMeetPointImage2(url2);
        return mp;
    }
    public ArrayList<MeetPoint> findPossibleMP(String origin1, String origin2, String prefLocationType, String prefTravelMode1, String prefTravelMode2)throws Exception{
        double[] midpoint = findMidPoint(origin1,origin2,prefTravelMode1,prefTravelMode2);
        int radius = 1000; //in metres
        ArrayList<JSONObject> possibleMP = new ArrayList<>();
        NumberFormat coordformat = new DecimalFormat("#0.000000");
        while (possibleMP.size() < 6){
            radius+=500;
            try{
                possibleMP = sAPI.getAddress(prefLocationType,coordformat.format(midpoint[0]),coordformat.format(midpoint[1]),String.valueOf(radius));
            } catch(RecordnotfoundException e){
                System.out.println("Record Not Found Exception");
            }
        }
        for (int i=0;i<possibleMP.size();i++){
            meetPointArrayList.add(i,fetchMeetPoint(possibleMP.get(i).getString("name"),possibleMP.get(i).getDouble("latitude"),possibleMP.get(i).getDouble("longitude"), origin1, origin2, prefTravelMode1,prefTravelMode2));
        }
        sortTravelDuration();
        return meetPointArrayList;
    }

    public void sortTravelDuration(){
        Collections.sort(meetPointArrayList, new CustomComparator());
    }

    class CustomComparator implements Comparator<MeetPoint> {
        public int compare(MeetPoint mp1, MeetPoint mp2) {
            double travelduration1 = mp1.getTravelDuration() + mp1.getTravelDuration2();
            double travelduration2 = mp2.getTravelDuration() + mp2.getTravelDuration2();
            return (int)(travelduration1 - travelduration2);
        }
    }

}
