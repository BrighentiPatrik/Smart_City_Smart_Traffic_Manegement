package it.unimore.fum.iot.models;

public class Zona {
    private String nome;
    private Incrocio [] incroci;
    private SmartCamera [] smartCameras;
    private Policy [] policies;
    private Via [] vie;

    public Zona(String nome, Incrocio[] incroci, SmartCamera[] smartCameras, Policy[] policies,Via[] vie) {
        this.nome = nome;
        this.incroci = incroci;
        this.smartCameras = smartCameras;
        this.policies = policies;
        this.vie=vie;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Incrocio[] getIncroci() {
        return incroci;
    }

    public void setIncroci(Incrocio[] incroci) {
        this.incroci = incroci;
    }

    public SmartCamera[] getSmartCameras() {
        return smartCameras;
    }

    public void setSmartCameras(SmartCamera[] smartCameras) {
        this.smartCameras = smartCameras;
    }

    public Policy[] getPolicies() {
        return policies;
    }

    public void setPolicies(Policy[] policies) {
        this.policies = policies;
    }

    @Override
    public String toString() {
        String stringa=""+this.nome+"\n"+"Incroci:\n";
        for(int i=0;i<incroci.length;i++)
            stringa+=""+(i+1)+") "+incroci[i];
        stringa+="Smart camera:\n";
        for(int i=0;i<smartCameras.length;i++)
            stringa+=""+(i+1)+") "+smartCameras[i];
        stringa+="Policy:\n";
        if(this.policies!=null)
        for(int i=0;i<policies.length;i++)
            stringa+=""+(i+1)+") "+policies[i];

        return stringa;
    }
}
