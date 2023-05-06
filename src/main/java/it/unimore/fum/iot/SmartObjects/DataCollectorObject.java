package it.unimore.fum.iot.SmartObjects;

import it.unimore.fum.iot.configuration.ManegementConfigurationParameter;
import it.unimore.fum.iot.configuration.TopicConfigurationParameters;
import it.unimore.fum.iot.models.DataCollector;
import it.unimore.fum.iot.models.Policy;
import it.unimore.fum.iot.models.SmartCamera;
import it.unimore.fum.iot.SmartObjects.utilities.Utils;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static java.lang.Thread.sleep;

public class DataCollectorObject implements Runnable{

    private DataCollector manager;
    private MqttClient client= Utils.creaClient();

    private int numVeicoliFermi1;
    private int numVeicoliFermi2;
    private int numVeicoliFermi3;
    private int numVeicoliFermi4;
    private int numVeicoliFermi5;

    private int numMacchineCongestione= ManegementConfigurationParameter.NUM_MACCHINE_CONGESTIONE;

    public DataCollectorObject(DataCollector manager) throws MqttException {
        this.manager = manager;
    }

    public void stampaVeicoliFermi(){
        System.out.println(Utils.ANSI_YELLOW+"Veicoli fermi\n"+"1:"+numVeicoliFermi1+"\n2:"+numVeicoliFermi2+"\n3:"+numVeicoliFermi3+"\n4:"+numVeicoliFermi4+"\n5:"+numVeicoliFermi5+Utils.ANSI_RESET);
    }
    public void eliminaMessaggiRetained() throws MqttException {

        String topic1;
        MqttMessage msg=new MqttMessage();
        msg.setQos(1);
        msg.setRetained(true);

        for(int id=1;id<=ManegementConfigurationParameter.NUM_DISPOSITIVI;id++) {
            if (client.isConnected()) {
                topic1 = String.format("%s/%s/%s/%s", TopicConfigurationParameters.SMART_CAMERAS_TOPIC, ("S0" + id), "TELEMETRY", "TARGHELETTE");
                client.publish(topic1, msg);
                System.out.println("Elimino retained Topic : " + topic1);

                topic1 = String.format("%s/%s/%s/%s", TopicConfigurationParameters.SMART_CAMERAS_TOPIC, ("S0" + id), "TELEMETRY", "VEICOLIPASSAGGIO");
                client.publish(topic1, msg);
                System.out.println("Elimino retained Topic : " + topic1);

                topic1 = String.format("%s/%s/%s/%s", TopicConfigurationParameters.SMART_CAMERAS_TOPIC, ("S0" + id), "TELEMETRY", "PERSONEPASSAGGIO");
                client.publish(topic1, msg);
                System.out.println("Elimino retained Topic : " + topic1);

                topic1 = String.format("%s/%s/%s/%s", TopicConfigurationParameters.SMART_CAMERAS_TOPIC, ("S0" + id), "TELEMETRY", "VEICOLIFERMI");
                client.publish(topic1, msg);
                System.out.println("Elimino retained Topic : " + topic1);

                topic1 = String.format("%s/%s/%s/%s", TopicConfigurationParameters.SMART_CAMERAS_TOPIC, ("S0" + id), "TELEMETRY", "BICICLETTEPASSAGGIO");
                client.publish(topic1, msg);
                System.out.println("Elimino retained Topic : " + topic1);

                topic1 = String.format("%s/%s/%s", TopicConfigurationParameters.TRAFFIC_LIGHT_TOPIC, ("T0" + id), "POLICY");
                client.publish(topic1, msg);
                System.out.println("Elimino retained Topic : " + topic1);


            } else {
                System.err.println("Error:Topic or Msg = Null or MQTT Client is not Connected !");
            }

        }
    }

    @Override
    public void run() {

        //Policy
        Policy veloce=new Policy("1",10,10,10);
        Policy normale=new Policy("2",20,20,20);
        Policy lento=new Policy("3",30,30,30);

        String [] targheMonitorate=manager.getTargheMonitorate();
        SmartCamera [] smartCameraZ3=manager.getZone()[0].getSmartCameras();

        try{
            eliminaMessaggiRetained();

            String topic1 = String.format("%s/%s", TopicConfigurationParameters.SMART_CAMERAS_TOPIC,"#");

            client.subscribe ( topic1, new IMqttMessageListener() {
                @Override
                public void messageArrived ( String topic1 , MqttMessage message ) throws Exception
                {
                    byte [] payload = message.getPayload();
                    String payloadStringa= new String(payload);
                    //System.out.println(" Message Received ("+ topic1 +") Message Received : " + payloadStringa);
                    if(topic1.contains("TARGHELETTE")){
                        String smartCameraCheLaHaRilevata=topic1.substring(64,67);
                        for(int i=0;i< targheMonitorate.length;i++) {
                            String targaLetta=new String(payload);
                            targaLetta=targaLetta.substring(1,targaLetta.length()-1);
                            if (targheMonitorate[i].compareTo(targaLetta) == 0) {
                                System.out.println(Utils.ANSI_RED+"!!!!!!!!!!!!!!!ATTENZIONE!!!!!!!!!!!!!\n"+"Letta targa monitorata: "+targaLetta+" da smartCamera: "+smartCameraCheLaHaRilevata+Utils.ANSI_RESET);
                            }
                        }
                    }

                    if(topic1.contains("VEICOLIFERMI")) {

                        if (topic1.contains(smartCameraZ3[0].getId())) {
                            numVeicoliFermi1=Integer.parseInt(payloadStringa);
                        }

                        if (topic1.contains(smartCameraZ3[1].getId())) {
                            numVeicoliFermi2=Integer.parseInt(payloadStringa);
                        }

                        if (topic1.contains(smartCameraZ3[2].getId())) {
                            numVeicoliFermi3=Integer.parseInt(payloadStringa);
                        }

                        if (topic1.contains(smartCameraZ3[3].getId())) {
                            numVeicoliFermi4=Integer.parseInt(payloadStringa);
                        }

                        if (topic1.contains(smartCameraZ3[4].getId())) {
                            numVeicoliFermi5=Integer.parseInt(payloadStringa);
                        }
                    }
                }
            });


            while(true){
                stampaVeicoliFermi();

                if(numVeicoliFermi1>numMacchineCongestione && numVeicoliFermi1>numVeicoliFermi2){
                    if (client.isConnected()) {
                        System.out.println(""+Utils.getCountOutputDataCollectorEmulator()+")");
                        Utils.incrementaCountDataCollector();
                        String topic2 = String.format("%s/%s/%s",TopicConfigurationParameters.TRAFFIC_LIGHT_TOPIC,"T02","POLICY");
                        String payloadString = "3";
                        MqttMessage msg = new MqttMessage(payloadString.getBytes());
                        msg.setQos(1);
                        msg.setRetained(true);
                        client.publish(topic2, msg);
                        System.out.println("Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString);
                        topic2 = String.format("%s/%s/%s",TopicConfigurationParameters.TRAFFIC_LIGHT_TOPIC,"T01","POLICY");
                        payloadString = "1";
                        msg = new MqttMessage(payloadString.getBytes());
                        msg.setQos(1);
                        msg.setRetained(true);
                        client.publish(topic2, msg);
                        System.out.println("Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString);
                        System.out.println(Utils.ANSI_CYAN+"Dato che la Smart camera 1 ha rilevato "+numVeicoliFermi1+" veicoli fermi, che è maggore della soglia dei veicoli per rilevare una congestione("+
                                numMacchineCongestione+")\ne la smart camera 2 ne ha rilevati di meno("+numVeicoliFermi2+") cambio la policy del Traffic light 1 in 1(veloce/sblocca) e del traffic light 2 in 3(lento/blocca)"+Utils.ANSI_RESET);

                        manager.getZone()[0].getIncroci()[0].getTrafficLight()[0].setPolicyAttiva(veloce);
                        manager.getZone()[0].getIncroci()[0].getTrafficLight()[1].setPolicyAttiva(lento);

                    } else {
                        System.err.println("Error:Topic or Msg = Null or MQTT Client is not Connected !");
                    }
                }else if(numVeicoliFermi2>numMacchineCongestione && numVeicoliFermi2>numVeicoliFermi1){
                    if (client.isConnected()) {
                        System.out.println(""+Utils.getCountOutputDataCollectorEmulator()+")");
                        Utils.incrementaCountDataCollector();
                        String topic2 = String.format("%s/%s/%s",TopicConfigurationParameters.TRAFFIC_LIGHT_TOPIC,"T01","POLICY");
                        String payloadString = "3";
                        MqttMessage msg = new MqttMessage(payloadString.getBytes());
                        msg.setQos(1);
                        msg.setRetained(true);
                        client.publish(topic2, msg);
                        System.out.println("Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString);
                        topic2 = String.format("%s/%s/%s",TopicConfigurationParameters.TRAFFIC_LIGHT_TOPIC,"T02","POLICY");
                        payloadString = "1";
                        msg = new MqttMessage(payloadString.getBytes());
                        msg.setQos(1);
                        msg.setRetained(true);
                        client.publish(topic2, msg);
                        System.out.println("Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString);
                        System.out.println(Utils.ANSI_PURPLE+"Dato che la Smart camera 2 ha rilevato "+numVeicoliFermi2+" veicoli fermi, che è maggore della soglia dei veicoli per rilevare una congestione("+
                                numMacchineCongestione+")\ne la smart camera 1 ne ha rilevati di meno("+numVeicoliFermi1+") cambio la policy del Traffic light 2 in 1(veloce/sblocca) e del traffic light 1 in 3(lento/blocca)"+Utils.ANSI_RESET);

                        manager.getZone()[0].getIncroci()[0].getTrafficLight()[0].setPolicyAttiva(lento);
                        manager.getZone()[0].getIncroci()[0].getTrafficLight()[1].setPolicyAttiva(veloce);


                    } else {
                        System.err.println("Error:Topic or Msg = Null or MQTT Client is not Connected !");
                    }
                }

                if(numVeicoliFermi3>numMacchineCongestione && numVeicoliFermi5<numVeicoliFermi3){
                    if (client.isConnected()) {
                        System.out.println(""+Utils.getCountOutputDataCollectorEmulator()+")");
                        Utils.incrementaCountDataCollector();
                        String topic2 = String.format("%s/%s/%s",TopicConfigurationParameters.TRAFFIC_LIGHT_TOPIC,"T04","POLICY");
                        String payloadString = "3";
                        MqttMessage msg = new MqttMessage(payloadString.getBytes());
                        msg.setQos(1);
                        msg.setRetained(true);
                        client.publish(topic2, msg);
                        System.out.println("Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString);
                        topic2 = String.format("%s/%s/%s",TopicConfigurationParameters.TRAFFIC_LIGHT_TOPIC,"T03","POLICY");
                        payloadString = "1";
                        msg = new MqttMessage(payloadString.getBytes());
                        msg.setQos(1);
                        msg.setRetained(true);
                        client.publish(topic2, msg);
                        System.out.println("Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString);
                        System.out.println(Utils.ANSI_GREEN+"Dato che la Smart camera 3 ha rilevato "+numVeicoliFermi3+" veicoli fermi, che è maggore della soglia dei veicoli per rilevare una congestione("+
                                numMacchineCongestione+")\ne la smart camera 5 ne ha rilevati di meno("+numVeicoliFermi5+") cambio la policy del Traffic light 3 in 1(veloce/sblocca) e del traffic light 4 in 3(lento/blocca)"+Utils.ANSI_RESET);

                        manager.getZone()[0].getIncroci()[1].getTrafficLight()[0].setPolicyAttiva(veloce);
                        manager.getZone()[0].getIncroci()[1].getTrafficLight()[1].setPolicyAttiva(lento);

                    } else {
                        System.err.println("Error:Topic or Msg = Null or MQTT Client is not Connected !");
                    }
                }
                sleep(ManegementConfigurationParameter.SECONDI_AGGIORNAMENTO*1000);
            }
        } catch ( Exception e){
            e.printStackTrace();
        }
    }
}
