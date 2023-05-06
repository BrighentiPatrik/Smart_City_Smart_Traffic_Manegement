package it.unimore.fum.iot.configuration;

public class TopicConfigurationParameters {
    public static final String MQTT_BASIC_TOPIC = String.format ("/iot/user/%s", MqttConfigurationParameters.MQTT_USERNAME );
    public static final String SMART_OBJECTS_INFO_TOPIC = String.format("%s/SMART_OBJECT/INFO",MQTT_BASIC_TOPIC);
    public static final String SMART_CAMERAS_TOPIC = String.format("%s/SMART_OBJECT/SMART_CAMERAS",MQTT_BASIC_TOPIC);
    public static final String TRAFFIC_LIGHT_TOPIC = String.format("%s/SMART_OBJECT/TRAFFIC_LIGHTS",MQTT_BASIC_TOPIC);
}
