package com.xiyoupositioning.util;

import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.wifi.ScanResult;

public class sendWifi2Server {
	
	private String url;
	private String data = "";
	private boolean running = false;
	
	public sendWifi2Server() {
		url = "http://121.40.95.120/rainmote.php?";
	}
	
	public void append(ScanResult wifi) {
		if(running)
		{
			data = url;
			data += "BSSID=" + wifi.BSSID + "&";
			data += "SSID=" + wifi.SSID + "&";
			data += "level=" + wifi.level + "&";
			data += "frequency=" + wifi.frequency + "&";
			data += "timestamp=" + wifi.timestamp;
			HttpGet httpGet = new HttpGet(data);
			HttpClient httpClient = new DefaultHttpClient();
			try {
				httpClient.execute(httpGet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start() {
		running = true;
	}
	
	public void stop() {
		running = false;
	}
	
}
