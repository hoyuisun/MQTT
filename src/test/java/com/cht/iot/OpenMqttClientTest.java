package com.cht.iot;

import org.junit.Test;

import com.cht.iot.IRawdata;
import com.cht.iot.OpenMqttClient;

public class OpenMqttClientTest {
	private String url = "iot.cht.com.tw";
	private String apiKey = "PKPSR449EFZR5KWEGA";
	private String deviceId = "4538071860";
	private String sensorId = "co";
	
	@Test
	public void test() throws Exception{
		OpenMqttClient omc = new OpenMqttClient(url, 1883, apiKey);
		omc.Connect();
		
		//Subscribe the topic
		omc.Subscribe(deviceId, sensorId);
		
		//Publish the topic
	    IRawdata [] rawdata = new IRawdata[1];
		rawdata[0] = new IRawdata();
		rawdata[0].setId("co");
		rawdata[0].setValues(new String [] {"0902"});
		omc.Publish(deviceId, rawdata);
	    
		Thread.sleep(3000);
	    omc.Disconnect();
	}
}
