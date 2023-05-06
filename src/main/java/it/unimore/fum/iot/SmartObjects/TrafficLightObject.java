package it.unimore.fum.iot.SmartObjects;

import it.unimore.fum.iot.Interfaces.Actuator;
import it.unimore.fum.iot.configuration.TopicConfigurationParameters;
import it.unimore.fum.iot.models.Policy;
import it.unimore.fum.iot.models.TrafficLight;
import it.unimore.fum.iot.SmartObjects.utilities.Utils;
import org.eclipse.paho.client.mqttv3.*;

public class TrafficLightObject implements Actuator,Runnable{

    private TrafficLight trafficLight;
    private MqttClient mqttClient= Utils.creaClient();

    public TrafficLightObject(TrafficLight trafficLight) throws MqttException {
        this.trafficLight = trafficLight;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void setpolicy(Policy policyNuova) {
        this.trafficLight.setPolicyAttiva(policyNuova);
    }

    @Override
    public String toString() {
        return trafficLight.toString();
    }

    @Override
    public void run() {

        System.out.println("Traffic Light: "+this.getTrafficLight().getId()+" online!");

        try {

            String topic1 = String.format("%s/%s/%s", TopicConfigurationParameters.TRAFFIC_LIGHT_TOPIC,this.getTrafficLight().getId(),"POLICY");

            mqttClient.subscribe ( topic1, new IMqttMessageListener() {
                @Override
                public void messageArrived ( String topic1 , MqttMessage message ) throws Exception
                {
                    String colore=null;
                    switch (getTrafficLight().getId()){
                        case "T01":
                            colore=Utils.ANSI_CYAN;
                            break;
                        case "T02":
                            colore=Utils.ANSI_GREEN;
                            break;
                        case "T03":
                            colore=Utils.ANSI_PURPLE;
                            break;
                        case "T04":
                            colore=Utils.ANSI_YELLOW;
                            break;
                    }

                    byte [] payload = message.getPayload();
                    String nuovaPolicy=new String(payload);
                    //if(nuovaPolicy.compareTo(getTrafficLight().getPolicyAttiva().getNome())!=0) {
                        //System.out.println(""+Utils.getCountOutputDataCollectorEmulator()+")");
                        System.out.println("Message Received (" + topic1 + ") Message Received : " + nuovaPolicy);
                        System.out.println(colore+"Policy del semaforo: " + getTrafficLight().getId() + " cambiata\nDA: "+getTrafficLight().getPolicyAttiva()
                                +"A : " + getTrafficLight().getPolicyPossibili()[Integer.parseInt(nuovaPolicy) - 1] + Utils.ANSI_RESET);
                        getTrafficLight().setPolicyAttiva(getTrafficLight().getPolicyPossibili()[Integer.parseInt(nuovaPolicy) - 1]);
                   // }
                }
            });

        } catch ( Exception e){
            e.printStackTrace();
        }
    }
}
