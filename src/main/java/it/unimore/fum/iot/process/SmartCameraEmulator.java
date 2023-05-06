package it.unimore.fum.iot.process;

import it.unimore.fum.iot.SmartObjects.SmartCameraObject;
import it.unimore.fum.iot.models.GPS;
import it.unimore.fum.iot.models.SmartCamera;
import it.unimore.fum.iot.models.Via;
import org.eclipse.paho.client.mqttv3.MqttException;

import static java.lang.Thread.sleep;


public class SmartCameraEmulator {

    public static void main(String[] args) throws Exception {

        SmartCameraObject [] smartCameraObjects=inizializzazione();

        //Thread
        Thread t1=new Thread(smartCameraObjects[0]);
        t1.start();
        Thread t2=new Thread(smartCameraObjects[1]);
        t2.start();
        Thread t3=new Thread(smartCameraObjects[2]);
        t3.start();
        Thread t4=new Thread(smartCameraObjects[3]);
        t4.start();
        Thread t5=new Thread(smartCameraObjects[4]);
        t5.start();
    }

    public static SmartCameraObject [] inizializzazione() throws MqttException {
        SmartCameraObject [] smartCameraObjects=new SmartCameraObject[5];
        //GPS
        GPS gpsSC1Z3 = new GPS(200, 200);
        GPS gpsSC2Z3 = new GPS(180, 220);
        GPS gpsSC3Z3 = new GPS(200, 280);
        GPS gpsSC4Z3 = new GPS(180, 280);
        GPS gpsSC5Z3 = new GPS(200, 320);

        //Via
        Via viaMonetegro = new Via("Via Montenegro");
        Via viaRomania = new Via("Via Romania");
        Via viaAlbania = new Via("Via Albania");

        //SmartCamera Object
        smartCameraObjects[0] = new SmartCameraObject(new SmartCamera(gpsSC1Z3, viaMonetegro));
        smartCameraObjects[1] = new SmartCameraObject(new SmartCamera(gpsSC2Z3, viaRomania));
        smartCameraObjects[2] = new SmartCameraObject(new SmartCamera(gpsSC3Z3, viaMonetegro));
        smartCameraObjects[3] = new SmartCameraObject(new SmartCamera(gpsSC4Z3, viaAlbania));
        smartCameraObjects[4] = new SmartCameraObject(new SmartCamera(gpsSC5Z3, viaMonetegro));

        return smartCameraObjects;
    }
}
