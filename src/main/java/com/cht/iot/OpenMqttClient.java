package com.cht.iot;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OpenMqttClient {
	
	private String url;
	private String apiKey;
	private MqttClient client;
	private Gson gson = new GsonBuilder().create();
	
	public OpenMqttClient(){
	}
	
	public OpenMqttClient(String host, int port, String apiKey){
		this(host, port, apiKey, false);
	}
	
	public OpenMqttClient(String host, int port, String apiKey, boolean tls){
		this.url = String.format("%s://%s:%d", tls? "ssl" : "tcp", host, port);
		this.apiKey = apiKey;
	}
	
	public String getRawDataTopics(String deviceId, String sensorId){
		return String.format("/v1/device/%s/sensor/%s/rawdata", deviceId, sensorId);
	}
	
	public String getPublishToptics(String deviceId){
		return String.format("/v1/device/%s/rawdata", deviceId);
	}
	
	public void Connect() throws MqttException{
		String clientId = MqttClient.generateClientId();
		MqttClientPersistence mcp = new MemoryPersistence();
		client = new MqttClient(url, clientId, mcp);
		
		client.setCallback(new MqttCallback() {
			
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				String json = new String(message.getPayload(), "UTF-8");
				System.out.println(json);		
			}
			
			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {				
			}
			
			@Override
			public void connectionLost(Throwable e) {
			}
		});
		
		MqttConnectOptions opts = new MqttConnectOptions();
		opts.setUserName(apiKey);
		opts.setPassword(apiKey.toCharArray());
		opts.setConnectionTimeout(5);
		opts.setKeepAliveInterval(30);	
        opts.setCleanSession(true);
        
        client.connect(opts);
        System.out.println("Connected with broker");
	}
	
	public void Disconnect() throws MqttException{
		client.disconnect();
		System.out.println("Disconnected with broker");
	}
	
	public void Subscribe(String deviceId, String sensorId) throws MqttException, InterruptedException{
		String topic = getRawDataTopics(deviceId, sensorId);
		client.subscribe(topic, 0);
	}
	
	public void Publish(String deviceId, IRawdata [] rawdata) throws MqttPersistenceException, UnsupportedEncodingException, MqttException{
		String topic = getPublishToptics(deviceId);
		MqttTopic mt = client.getTopic(topic);
		String json = gson.toJson(rawdata);
		mt.publish(json.getBytes("UTF-8"), 1, false);
	}
}
