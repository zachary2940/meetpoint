import java.io.Serializable;

public class MeetPoint extends Location implements Serializable {

    private String type;
    private double travelDuration;
    private double travelDuration2;
    private String meetPointImage;
    private String meetPointImage2;

    public MeetPoint(){}
    public MeetPoint(String name, double[] coordinates, String type, double travelDuration, double travelDuration2, String meetPointImage, String meetPointImage2){
        super(name, coordinates);
        this.type=type;
        this.travelDuration=travelDuration;
        this.travelDuration2=travelDuration2;
        this.meetPointImage=meetPointImage;
        this.meetPointImage2=meetPointImage2;

    }

    public String getType(){
        return type;
    }

    public double getTravelDuration() {
        return travelDuration;
    }

    public double getTravelDuration2() {
        return travelDuration2;
    }

    public String getMeetPointImage(){
        return meetPointImage;
    }

    public String getMeetPointImage2(){
        return meetPointImage2;
    }

    public void setType(String newType){
        type=newType;
    }

    public void setTravelDuration(double newDuration){
        travelDuration=newDuration;
    }

    public void setTravelDuration2(double newDuration){
        travelDuration2=newDuration;
    }

    public void setMeetPointImage(String newImage) {
        meetPointImage=newImage;
    }

    public void setMeetPointImage2(String newImage) {
        meetPointImage2=newImage;
    }
}
