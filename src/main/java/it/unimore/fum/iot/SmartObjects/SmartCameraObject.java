package it.unimore.fum.iot.SmartObjects;

import com.google.gson.Gson;
import it.unimore.fum.iot.Interfaces.Sensor;
import it.unimore.fum.iot.configuration.ManegementConfigurationParameter;
import it.unimore.fum.iot.configuration.TopicConfigurationParameters;
import it.unimore.fum.iot.SmartObjects.utilities.Utils;
import it.unimore.fum.iot.models.SmartCamera;
import org.eclipse.paho.client.mqttv3.*;

import static java.lang.Thread.sleep;

public class SmartCameraObject implements Sensor, Runnable {

    private SmartCamera smartCamera;
    private MqttClient mqttClient=Utils.creaClient();

    public SmartCameraObject(SmartCamera smartCamera) throws MqttException {
        this.smartCamera = smartCamera;
    }

    public SmartCamera getSmartCamera() {
        return smartCamera;
    }
    public void setSmartCamera(SmartCamera smartCamera) {
        this.smartCamera = smartCamera;
    }

    @Override
    public int generateNumVeicoliPassaggio() {
        return Utils.random.nextInt(ManegementConfigurationParameter.MAX_NUM_OGGETTI)+1;
    }
    @Override
    public String[] generateTarghe(int numTarghe) {
        String [] targhe=new String[numTarghe];

        for(int i=0;i<numTarghe;i++) {
            targhe[i] = "";
            targhe[i]+=(char) (Utils.random.nextInt(25)+65);
            targhe[i]+=(char) (Utils.random.nextInt(25)+65);
            targhe[i]+=(char) (Utils.random.nextInt(10)+48);
            targhe[i]+=(char) (Utils.random.nextInt(10)+48);
            targhe[i]+=(char) (Utils.random.nextInt(25)+65);
            targhe[i]+=(char) (Utils.random.nextInt(25)+65);
        }
        return targhe;
    }
    @Override
    public int generateNumPersonePassaggio() {
        return Utils.random.nextInt(ManegementConfigurationParameter.MAX_NUM_OGGETTI)+1;
    }
    @Override
    public int generateNumBiciclettePassaggio() {
        return Utils.random.nextInt(ManegementConfigurationParameter.MAX_NUM_OGGETTI)+1;
    }
    @Override
    public int generateNumVeicolifermi() {
        return Utils.random.nextInt(ManegementConfigurationParameter.MAX_NUM_OGGETTI)+1;
    }
    @Override
    public String toString() {
        return smartCamera.toString();
    }

    @Override
    public void run() {

        String colore=null;
        switch (getSmartCamera().getId()){
            case "S01":
                colore=Utils.ANSI_CYAN;
                break;
            case "S02":
                colore=Utils.ANSI_GREEN;
                break;
            case "S03":
                colore=Utils.ANSI_PURPLE;
                break;
            case "S04":
                colore=Utils.ANSI_YELLOW;
                break;
            case "S05":
                colore=Utils.ANSI_BLUE;
                break;
        }

        try {

            System.out.println(colore+"Smart Camera "+getSmartCamera().getId()+" Connected!"+Utils.ANSI_RESET);

            int numTarghe=0;
            int numIterazioni=ManegementConfigurationParameter.NUM_ITERAZIONI_SMART_CAMERA;
            int temp=0;
            while(temp<numIterazioni){
                numTarghe+=pubblicaMessaggio(mqttClient,this,"VEICOLIPASSAGGIO",1,0,colore);
                pubblicaMessaggio(mqttClient,this,"PERSONEPASSAGGIO",1,0,colore);
                pubblicaMessaggio(mqttClient,this,"BICICLETTEPASSAGGIO",1,0,colore);
                numTarghe+=pubblicaMessaggio(mqttClient,this,"VEICOLIFERMI",1,0,colore);
                pubblicaMessaggio(mqttClient,this,"TARGHELETTE",1,numTarghe,colore);
                sleep(ManegementConfigurationParameter.SECONDI_AGGIORNAMENTO*1000);
                temp+=1;
            }

            mqttClient.disconnect();
            mqttClient.close();
            System.out.println(colore+"Smart camera: "+getSmartCamera().getId()+" Disconnected !"+Utils.ANSI_RESET);

        } catch ( Exception e){
            e.printStackTrace();
        }
    }
    private static String generaTargaMonitorata(){

        int temp=Utils.random.nextInt(3);
        switch(temp){
            case 0:
                return "AA11AA";
            case 1:
                return "BB22BB";
            case 2:
                return "CC33CC";
        }
        return "Errore generazione targa monitorata";
    }
    private static int pubblicaMessaggio(IMqttClient mqttClient, SmartCameraObject sc, String topic, int qos,int numTarghe,String colore) throws MqttException {
        int count = 0;
        if (mqttClient.isConnected()) {
            Gson gson = new Gson();
            String topic2 = String.format("%s/%s/%s/%s", TopicConfigurationParameters.SMART_CAMERAS_TOPIC, sc.getSmartCamera().getId(), "TELEMETRY", topic);
            String payloadString = null;
            count = 0;
            switch (topic) {
                case "TARGHELETTE":
                    String [] targhe= sc.generateTarghe(numTarghe);
                    MqttMessage msg;
                    for(int i=0;i<numTarghe;i++) {
                        payloadString = gson.toJson(targhe[i]);
                        msg = new MqttMessage(payloadString.getBytes());
                        msg.setQos(qos);
                        msg.setRetained(false);
                        mqttClient.publish(topic2, msg);
                        System.out.println(colore+"Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString+Utils.ANSI_RESET);

                    }
                    if(Utils.random.nextInt(ManegementConfigurationParameter.PROBABILITA_TARGA_MONITORATA)==0) {
                        payloadString = gson.toJson(generaTargaMonitorata());
                        msg = new MqttMessage(payloadString.getBytes());
                        mqttClient.publish(topic2, msg);
                        System.out.println(Utils.ANSI_RED + "Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString + Utils.ANSI_RESET);
                    }
                    return 0;

                case "VEICOLIPASSAGGIO":
                    count=sc.generateNumVeicoliPassaggio();
                    payloadString = gson.toJson(count);
                    break;
                case "PERSONEPASSAGGIO":
                    payloadString = gson.toJson(sc.generateNumPersonePassaggio());
                    break;
                case "BICICLETTEPASSAGGIO":
                    payloadString = gson.toJson(sc.generateNumBiciclettePassaggio());
                    break;
                case "VEICOLIFERMI":
                    count= sc.generateNumVeicolifermi();
                    payloadString = gson.toJson(count);
                    break;
                default:
                    System.out.println("Errore passaggio topic");
                    return count;
            }

            MqttMessage msg = new MqttMessage(payloadString.getBytes());
            msg.setQos(qos);
            msg.setRetained(true);
            mqttClient.publish(topic2, msg);
            System.out.println(colore+"Device Data Correctly Published ! Topic : " + topic2 + " Payload: " + payloadString+Utils.ANSI_RESET);
        } else {
            System.err.println("Error:Topic or Msg = Null or MQTT Client is not Connected !");
        }

        return count;
    }
}
