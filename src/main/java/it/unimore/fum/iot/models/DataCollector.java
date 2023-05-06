package it.unimore.fum.iot.models;

public class DataCollector {
    private Zona [] zone;
    private String [] targheMonitorate;

    public DataCollector(Zona[] zone, String[] targheMonitorate) {
        this.zone = zone;
        this.targheMonitorate = targheMonitorate;
    }

    public Zona[] getZone() {
        return zone;
    }

    public void setZone(Zona[] zone) {
        this.zone = zone;
    }

    public String[] getTargheMonitorate() {
        return targheMonitorate;
    }

    public void setTargheMonitorate(String[] targheMonitorate) {
        this.targheMonitorate = targheMonitorate;
    }

    @Override
    public String toString() {
        String stringa="Targhe monitorate:\n";
        for(int i=0;i<targheMonitorate.length;i++)
            stringa+=targheMonitorate[i]+"\n";
        stringa+="\nZONE\n";
        for(int i=0;i<zone.length;i++)
            stringa+=zone[i]+"\n";
        return stringa;
    }
}
