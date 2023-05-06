package it.unimore.fum.iot.SmartObjects.utilities;

import it.unimore.fum.iot.configuration.MqttConfigurationParameters;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;
import java.util.UUID;

public class Utils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static  int countOutputDataCollectorEmulator=1;

    public static int getCountOutputDataCollectorEmulator() {
        return countOutputDataCollectorEmulator;
    }

    public static void incrementaCountDataCollector(){
        countOutputDataCollectorEmulator++;
    }

    public static Random random=new Random();
    public static MqttClient creaClient() throws MqttException {
        String clientId = UUID.randomUUID().toString();
        MqttClientPersistence persistence = new MemoryPersistence();
        IMqttClient client = new MqttClient(String.format("tcp://%s:%d", MqttConfigurationParameters.BROKER_ADDRESS, MqttConfigurationParameters.BROKER_PORT), clientId, persistence);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(MqttConfigurationParameters.MQTT_USERNAME);
        options.setPassword(MqttConfigurationParameters.MQTT_PASSWORD.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect(options);

        return (MqttClient) client;
    }
}
