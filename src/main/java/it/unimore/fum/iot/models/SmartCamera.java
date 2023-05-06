package it.unimore.fum.iot.models;

public class SmartCamera extends SmartObject {
    private Via via;

    public SmartCamera(GPS gps, Via via) {
        super(gps);
        this.via = via;
        this.setType("SmartCamera");
        this.setId("S0"+getCountCamera());
        SmartObject.incrementCountCamera();
    }

    public Via getVia() {
        return via;
    }

    public void setVia(Via via) {
        this.via = via;
    }

    @Override
    public String toString() {
        return super.toString()+via;
    }

}
