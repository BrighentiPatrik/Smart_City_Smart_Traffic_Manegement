package temp;

import it.unimore.fum.iot.models.*;

public class Dati {
    public static void main(String [] args){
        //GPS
        //ZONA 1
        GPS gpsSC1Z1 = new GPS(60,50);
        GPS gpsSC2Z1 = new GPS(50,55);
        GPS gpsSC3Z1 = new GPS(30,50);
        GPS gpsTL1Z1 = new GPS(50,50);
        //ZONA 2
        GPS gpsSC1Z2 = new GPS(60,70);
        GPS gpsSC2Z2 = new GPS(50,80);
        GPS gpsSC3Z2 = new GPS(40,70);
        GPS gpsSC4Z2 = new GPS(60,90);
        GPS gpsSC5Z2 = new GPS(30,90);
        GPS gpsSC6Z2 = new GPS(50,100);
        GPS gpsTL1Z2 = new GPS(50,70);
        GPS gpsTL2Z2 = new GPS(50,90);

        //Vie
        Via viaFinlandia= new Via("Finlandia");
        Via viaGermania= new Via("Germania");
        Via viaFrancia= new Via("Francia");
        Via viaGrecia= new Via("Grecia");
        Via viaInghilterra= new Via("Inghilterra");
        Via viaSpagna= new Via("Spagna");

        //Policy
        Policy policyT1Z1=new Policy("1",10,10,10);
        Policy policyT1Z2=new Policy("2",20,20,20);
        Policy policyT2Z2=new Policy("3",30,30,30);

        //TrafficLight
        //ZONA 1
        TrafficLight semaforoZ1_1=new TrafficLight(gpsTL1Z1,policyT1Z1);
        //ZONA 2
        TrafficLight semaforoZ2_1=new TrafficLight(gpsTL1Z2,policyT1Z2);
        TrafficLight semaforoZ2_2=new TrafficLight(gpsTL2Z2,policyT2Z2);

        //Smart camera
        //ZONA 1
        SmartCamera sc1z1=new SmartCamera(gpsSC1Z1,viaGermania);
        SmartCamera sc2z1=new SmartCamera(gpsSC2Z1,viaFrancia);
        SmartCamera sc3z1=new SmartCamera(gpsSC3Z1,viaGermania);
        //ZONA2
        SmartCamera sc1z2=new SmartCamera(gpsSC1Z2,viaGrecia);
        SmartCamera sc2z2=new SmartCamera(gpsSC2Z2,viaFrancia);
        SmartCamera sc3z2=new SmartCamera(gpsSC3Z2,viaGrecia);
        SmartCamera sc4z2=new SmartCamera(gpsSC4Z2,viaInghilterra);
        SmartCamera sc5z2=new SmartCamera(gpsSC5Z2,viaInghilterra);
        SmartCamera sc6z2=new SmartCamera(gpsSC6Z2,viaFrancia);

        //Smart camera T1Z1
        SmartCamera [] smartCamerasT1Z1 = new SmartCamera[3];
        smartCamerasT1Z1[0]=sc1z1;
        smartCamerasT1Z1[1]=sc2z1;
        smartCamerasT1Z1[2]=sc3z1;
        //Smart camera T1Z2
        SmartCamera [] smartCamerasT1Z2 = new SmartCamera[4];
        smartCamerasT1Z2[0]=sc1z2;
        smartCamerasT1Z2[1]=sc2z2;
        smartCamerasT1Z2[2]=sc3z2;
        smartCamerasT1Z2[3]=sc2z1;
        //Smart camera T2Z2
        SmartCamera [] smartCamerasT2Z2 = new SmartCamera[4];
        smartCamerasT2Z2[0]=sc4z2;
        smartCamerasT2Z2[1]=sc5z2;
        smartCamerasT2Z2[2]=sc6z2;
        smartCamerasT2Z2[3]=sc2z2;

        //Incroci
        //Incrocio1Z1
        Via [] vieIncrocio1Z1 = new Via[2];
        vieIncrocio1Z1[0]=viaGermania;
        vieIncrocio1Z1[1]=viaFrancia;
        Incrocio incrocio1Z1=new Incrocio(vieIncrocio1Z1,null, smartCamerasT1Z1);
        //Incrocio 1Z2
        Via [] vieIncrocio1Z2= new Via[2];
        vieIncrocio1Z2[0]=viaGrecia;
        vieIncrocio1Z2[1]=viaFrancia;
        Incrocio incrocio1Z2=new Incrocio(vieIncrocio1Z2,null, smartCamerasT1Z2);
        //Incrocio 2Z2
        Via [] vieIncrocio2Z2= new Via[2];
        vieIncrocio2Z2[0]=viaInghilterra;
        vieIncrocio2Z2[1]=viaFrancia;
        Incrocio incrocio2Z2=new Incrocio(vieIncrocio2Z2,null, smartCamerasT2Z2);

        //ZONE
        //Zona 1
        Incrocio [] incrociZ1=new Incrocio[1];
        incrociZ1[0]=incrocio1Z1;

        SmartCamera [] smartCameraZ1 = new SmartCamera[3];
        smartCameraZ1[0]=sc1z1;
        smartCameraZ1[1]=sc2z1;
        smartCameraZ1[2]=sc3z1;

        Policy [] policyZ1=new Policy[1];
        policyZ1[0]=policyT1Z1;

        Via [] vieZ1=new Via[3];
        vieZ1[0]=viaFinlandia;
        vieZ1[1]=viaFrancia;
        vieZ1[2]=viaGermania;

        Zona zona1=new Zona("Zona1",incrociZ1,smartCameraZ1,policyZ1,vieZ1);

        //Zona2
        Incrocio [] incrociZ2=new Incrocio[2];
        incrociZ2[0]=incrocio1Z2;
        incrociZ2[1]=incrocio2Z2;

        SmartCamera [] smartCameraZ2 = new SmartCamera[6];
        smartCameraZ2[0]=sc1z2;
        smartCameraZ2[1]=sc2z2;
        smartCameraZ2[2]=sc3z2;
        smartCameraZ2[3]=sc4z2;
        smartCameraZ2[4]=sc5z2;
        smartCameraZ2[5]=sc6z2;

        Policy [] policyZ2=new Policy[2];
        policyZ2[0]=policyT1Z2;
        policyZ2[1]=policyT2Z2;

        Via [] vieZ2=new Via[4];
        vieZ2[0]=viaGrecia;
        vieZ2[1]=viaInghilterra;
        vieZ2[2]=viaFrancia;
        vieZ2[3]=viaSpagna;

        Zona zona2=new Zona("Zona2",incrociZ2,smartCameraZ2,policyZ2,vieZ2);

        //MANAGER
        Zona [] zone=new Zona[2];
        zone[0]=zona1;
        zone[1]=zona2;
        String [] targheMonitorate=new String[3];
        targheMonitorate[0]="AA11AA";
        targheMonitorate[1]="BB22BB";
        targheMonitorate[2]="CC33CC";
        DataCollector manager=new DataCollector(zone,targheMonitorate);
    }
}
