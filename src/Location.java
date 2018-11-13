import java.io.Serializable;

public class Location implements Serializable {
    private String name;
    private double[] coordinates;

    public Location(){}
    public Location(String name){
        this.name=name;
        this.coordinates = new double[2];
    }
    public Location(String name, double[] coordinates){
        this.name=name;
        this.coordinates = new double[2];
        this.coordinates=coordinates;
    }

    public String getName(){
        return name;
    }

    public double[] getCoordinates(){
        return coordinates;
        }

    public void setName(String newName){
        this.name=newName;
    }

    public void setCoordinates(double[] newCoords){
        this.coordinates=newCoords;
    }
}
