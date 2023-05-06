package it.unimore.fum.iot.process;

import it.unimore.fum.iot.SmartObjects.DataCollectorObject;
import it.unimore.fum.iot.SmartObjects.TrafficLightObject;
import it.unimore.fum.iot.SmartObjects.SmartCameraObject;
import it.unimore.fum.iot.models.*;
import org.eclipse.paho.client.mqttv3.*;

public class DataCollectorEmulator{

    public static void main(String[] args) throws MqttException {

        DataCollectorObject dataCollectorObject=inizializzazione();

        Thread t1=new Thread(dataCollectorObject);
        t1.start();
    }

    public static DataCollectorObject inizializzazione() throws MqttException {
        //ZONA 3
        //GPS
        GPS gpsTL_1_2_Z3=new GPS(200,220);
        GPS gpsTL_3_4_Z3=new GPS(200,300);
        GPS gpsSC1Z3=new GPS(200,200);
        GPS gpsSC2Z3=new GPS(180,220);
        GPS gpsSC3Z3=new GPS(200,280);
        GPS gpsSC4Z3=new GPS(180,280);
        GPS gpsSC5Z3=new GPS(200,320);

        //Via
        Via viaMonetegro=new Via("Via Montenegro");
        Via viaRomania=new Via("Via Romania");
        Via viaAlbania=new Via("Via Albania");

        //Policy
        Policy veloce=new Policy("1",10,10,10);
        Policy normale=new Policy("2",20,20,20);
        Policy lento=new Policy("3",30,30,30);

        //TrafficLight
        TrafficLightObject tl1z3=new TrafficLightObject(new TrafficLight(gpsTL_1_2_Z3,normale));
        TrafficLightObject tl2z3=new TrafficLightObject(new TrafficLight(gpsTL_1_2_Z3,normale));
        TrafficLightObject tl3z3=new TrafficLightObject(new TrafficLight(gpsTL_3_4_Z3,normale));
        TrafficLightObject tl4z3=new TrafficLightObject(new TrafficLight(gpsTL_3_4_Z3,normale));

        //SmartCamera Object
        SmartCameraObject sc1z3=new SmartCameraObject(new SmartCamera(gpsSC1Z3,viaMonetegro));
        SmartCameraObject sc2z3=new SmartCameraObject(new SmartCamera(gpsSC2Z3,viaRomania));
        SmartCameraObject sc3z3=new SmartCameraObject(new SmartCamera(gpsSC3Z3,viaMonetegro));
        SmartCameraObject sc4z3=new SmartCameraObject(new SmartCamera(gpsSC4Z3,viaAlbania));
        SmartCameraObject sc5z3=new SmartCameraObject(new SmartCamera(gpsSC5Z3,viaMonetegro));

        //Incroci
        //Incrocio1Z3
        Via [] vieIncrocio1Z3 = new Via[2];
        vieIncrocio1Z3[0]=viaMonetegro;
        vieIncrocio1Z3[1]=viaRomania;

        SmartCamera [] smartCamerasIncrocio1Z3 =new SmartCamera[3];
        smartCamerasIncrocio1Z3[0]=sc1z3.getSmartCamera();
        smartCamerasIncrocio1Z3[1]=sc2z3.getSmartCamera();
        smartCamerasIncrocio1Z3[2]=sc3z3.getSmartCamera();

        TrafficLight [] tlIncrocio1Z3=new TrafficLight[2];
        tlIncrocio1Z3[0]=tl1z3.getTrafficLight();
        tlIncrocio1Z3[1]=tl2z3.getTrafficLight();

        Incrocio incrocio1Z3=new Incrocio(vieIncrocio1Z3,tlIncrocio1Z3, smartCamerasIncrocio1Z3);

        //Incrocio 2Z3
        Via [] vieIncrocio2Z3= new Via[2];
        vieIncrocio2Z3[0]=viaMonetegro;
        vieIncrocio2Z3[1]=viaAlbania;

        SmartCamera [] smartCamerasIncrocio2Z3 =new SmartCamera[3];
        smartCamerasIncrocio2Z3[0]=sc3z3.getSmartCamera();
        smartCamerasIncrocio2Z3[1]=sc4z3.getSmartCamera();
        smartCamerasIncrocio2Z3[2]=sc5z3.getSmartCamera();

        TrafficLight [] tlIncrocio2Z3=new TrafficLight[2];
        tlIncrocio2Z3[0]=tl3z3.getTrafficLight();
        tlIncrocio2Z3[1]=tl4z3.getTrafficLight();

        Incrocio incrocio2Z3=new Incrocio(vieIncrocio2Z3,tlIncrocio2Z3, smartCamerasIncrocio2Z3);

        //Zona3
        Incrocio [] incrociZ3 =new Incrocio[2];
        incrociZ3[0]=incrocio1Z3;
        incrociZ3[1]=incrocio2Z3;

        SmartCamera [] smartCameraZ3 = new SmartCamera[5];
        smartCameraZ3[0]=sc1z3.getSmartCamera();
        smartCameraZ3[1]=sc2z3.getSmartCamera();
        smartCameraZ3[2]=sc3z3.getSmartCamera();
        smartCameraZ3[3]=sc4z3.getSmartCamera();
        smartCameraZ3[4]=sc5z3.getSmartCamera();


        Policy [] policyZ3=new Policy[3];
        policyZ3[0]=veloce;
        policyZ3[1]=normale;
        policyZ3[2]=lento;

        Via [] vieZ3 =new Via[3];
        vieZ3[0]=viaMonetegro;
        vieZ3[1]=viaAlbania;
        vieZ3[2]=viaRomania;

        Zona zona3=new Zona("Zona3", incrociZ3,smartCameraZ3,policyZ3, vieZ3);

        //MANAGER
        Zona [] zone=new Zona[1];
        zone[0]=zona3;

        String [] targheMonitorate=new String[3];
        targheMonitorate[0]="AA11AA";
        targheMonitorate[1]="BB22BB";
        targheMonitorate[2]="CC33CC";

        DataCollector manager=new DataCollector(zone,targheMonitorate);
        DataCollectorObject dataCollectorObject=new DataCollectorObject(manager);

        return dataCollectorObject;
    }
}
