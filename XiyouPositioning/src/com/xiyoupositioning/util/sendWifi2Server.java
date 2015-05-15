package com.xiyoupositioning.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.net.wifi.ScanResult;
import android.util.Log;

public class sendWifi2Server {
	
	private String url;
	private String data;
	private RequestParams params;
	private boolean running = false;
	private double distance = 0;
	
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
			data += "timestamp=" + wifi.timestamp + "&";
			data += "distance=" + this.distance;
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(data, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
					Log.w("rainmote", "post failed");
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					// TODO Auto-generated method stub
					Log.i("rainmote", "post success");
				}
				
			});
//			data = url;
//			data += "BSSID=" + wifi.BSSID + "&";
//			data += "SSID=" + wifi.SSID + "&";
//			data += "level=" + wifi.level + "&";
//			data += "frequency=" + wifi.frequency + "&";
//			data += "timestamp=" + wifi.timestamp;
//			HttpGet httpGet = new HttpGet(data);
//			HttpClient httpClient = new DefaultHttpClient();
//			try {
//				httpClient.execute(httpGet);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	public void start() {
		running = true;
	}
	
	public void stop() {
		running = false;
	}
	
	public void setDistance(double d) {
		this.distance = d;
	}
	
}
