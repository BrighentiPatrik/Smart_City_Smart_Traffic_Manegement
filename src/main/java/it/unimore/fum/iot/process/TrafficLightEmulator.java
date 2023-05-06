package it.unimore.fum.iot.process;

import it.unimore.fum.iot.SmartObjects.TrafficLightObject;
import it.unimore.fum.iot.models.*;
import org.eclipse.paho.client.mqttv3.*;


public class TrafficLightEmulator {
    public static void main(String[] args) throws MqttException {

        TrafficLightObject [] trafficLightObjects=inizializzazione();

        //THREAD
        Thread t1=new Thread(trafficLightObjects[0]);
        t1.start();
        Thread t2=new Thread(trafficLightObjects[1]);
        t2.start();
        Thread t3=new Thread(trafficLightObjects[2]);
        t3.start();
        Thread t4=new Thread(trafficLightObjects[3]);
        t4.start();

    }

    public static TrafficLightObject [] inizializzazione() throws MqttException {

        TrafficLightObject [] trafficLightObjects=new TrafficLightObject[4];

        GPS gpsTL_1_2_Z3=new GPS(200,220);
        GPS gpsTL_3_4_Z3=new GPS(200,300);

        Policy policy1=new Policy("1",10,10,10);
        Policy policy2=new Policy("2",20,20,20);
        Policy policy3=new Policy("3",30,30,30);
        Policy policy4=new Policy("4",40,40,40);

        trafficLightObjects[0]=new TrafficLightObject(new TrafficLight(gpsTL_1_2_Z3,policy1));
        trafficLightObjects[1]=new TrafficLightObject(new TrafficLight(gpsTL_1_2_Z3,policy2));
        trafficLightObjects[2]=new TrafficLightObject(new TrafficLight(gpsTL_3_4_Z3,policy3));
        trafficLightObjects[3]=new TrafficLightObject(new TrafficLight(gpsTL_3_4_Z3,policy4));

        return  trafficLightObjects;
    }
}
