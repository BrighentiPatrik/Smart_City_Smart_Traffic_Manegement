package it.unimore.fum.iot.models;

public class GPS {
    private double latitudine;
    private double longitudine;

    public GPS(double latitudine, double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    @Override
    public String toString() {
        return "GPS{" +
                "latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                '}'+'\n';
    }
}
