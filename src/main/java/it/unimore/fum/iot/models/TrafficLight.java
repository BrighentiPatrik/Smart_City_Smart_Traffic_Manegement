package it.unimore.fum.iot.models;

public class TrafficLight extends SmartObject{

    private Policy policyAttiva;
    private Policy[] policyPossibili;

    public TrafficLight(GPS gps, Policy policy) {
        super(gps);
        this.policyAttiva = policy;
        this.setType("TrafficLight");
        this.setId("T0"+getCountTrafficLight());
        SmartObject.incrementCountTrafficLight();
        this.policyPossibili= new Policy[3];
        policyPossibili[0]=new Policy("1",10,10,10);
        policyPossibili[1]=new Policy("2",20,20,20);
        policyPossibili[2]=new Policy("3",30,30,30);
    }

    @Override
    public String toString() {
        return super.toString()+policyAttiva;
    }

    public Policy getPolicyAttiva() {
        return policyAttiva;
    }

    public void setPolicyAttiva(Policy policy) {
        this.policyAttiva = policy;
    }

    public Policy[] getPolicyPossibili() {
        return policyPossibili;
    }

    public void setPolicyPossibili(Policy[] policyPossibili) {
        this.policyPossibili = policyPossibili;
    }
}
