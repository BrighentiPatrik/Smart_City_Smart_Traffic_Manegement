package it.unimore.fum.iot.models;

public class Incrocio {

    private static int count=0;

    private String id;
    private Via [] vie;
    private TrafficLight [] trafficLight;
    private SmartCamera [] smartCameras;

    public Incrocio(Via[] vie, TrafficLight [] trafficLight, SmartCamera[] smartCameras) {
        this.vie = vie;
        this.id="000"+count;
        this.trafficLight = trafficLight;
        this.smartCameras = smartCameras;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Incrocio.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Via[] getVie() {
        return vie;
    }

    public void setVie(Via[] vie) {
        this.vie = vie;
    }

    public TrafficLight[] getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLight[] trafficLight) {
        this.trafficLight = trafficLight;
    }

    public SmartCamera[] getSmartCameras() {
        return smartCameras;
    }

    public void setSmartCameras(SmartCamera[] smartCameras) {
        this.smartCameras = smartCameras;
    }

    @Override
    public String toString() {
        String stringa="Incrocio: "+id+"\n";
        stringa += "Vie\n";
        for(int i=0;i<vie.length;i++)
            stringa+=vie[i];
        stringa += "Traffic Light:\n";
        for(int i=0;i<trafficLight.length;i++)
            stringa+="\n"+trafficLight[i]+"\n";
        stringa += "Smart camera:\n";
        for(int i=0;i<smartCameras.length;i++)
            stringa+=""+(i+1)+") "+smartCameras[i]+"\n";
        return stringa;
    }
}
