package it.unimore.fum.iot.models;

public abstract class SmartObject {

    private static int countCamera=1;
    private static int countTrafficLight=1;

    private String id;
    private GPS gps;
    private String type;

    public SmartObject(GPS gps) {
        this.gps = gps;
    }

    public static int getCountCamera() {
        return countCamera;
    }

    public static void setCountCamera(int countCamera) {
        SmartObject.countCamera = countCamera;
    }

    public static void incrementCountCamera(){
        SmartObject.countCamera++;
    }

    public static int getCountTrafficLight() {
        return countTrafficLight;
    }

    public static void setCountTrafficLight(int countTrafficLight) {
        SmartObject.countTrafficLight = countTrafficLight;
    }

    public static void incrementCountTrafficLight(){
        SmartObject.countTrafficLight++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }

    @Override
    public String toString() {
        String stringa="";

        if(this.type.compareTo("TrafficLight")==0)
            stringa += "Traffic light: "+this.id+"\n";
        else if(this.type.compareTo("SmartCamera")==0)
            stringa += "Smart camera: "+this.id+"\n";
        else
            System.out.println("Errore nel tipo di smart object");

        stringa+=gps;


        return stringa;

    }
}
