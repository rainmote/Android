package com.xiyoupositioning;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.xiyoupositioning.util.sendWifi2Server;

public class WifiSniffer {
	
	private WifiManager wm;
	private BroadcastReceiver wifiReceiver;
	private Handler mHandler;
	private Context context;
	private boolean running = false;
	
	private RingView[] view;
	private double scale;
	private float damping;
	private int metaRSSI;
	private sendWifi2Server sender;
	private boolean sendRunning = false;

	
	public boolean isSendRunning() {
		return sendRunning;
	}

	public void setSendRunning(boolean sendRunning) {
		this.sendRunning = sendRunning;
	}

	public WifiSniffer(Context ctx, final TextView message) {
		context = ctx;
		mHandler = new Handler();
		
		wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		wifiReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				List<ScanResult> results = wm.getScanResults();
				Collections.sort(results, new Comparator<ScanResult>() {
					public int compare(ScanResult a, ScanResult b) {
						return b.level - a.level;
					}
				});
				try {
					sender = new sendWifi2Server();
					for(int i = 0; i < 1; ++i) {
						ScanResult route = results.get(i);
						double rssi = route.level;
						double n = damping;
						int A = metaRSSI;
						
						float d = (float) Math.pow(Math.E, (A-rssi)*Math.log(10)/(10*n));
						//view[i].setRadius((float) (d*491/8.7)); // 491 photo width. 8.7 meter width
						message.setText("Damping:"+damping+"\n"+
								"Meta RSSI:"+metaRSSI+"\n");
						message.append("BSSID:"+route.BSSID+"\n");
						message.append("SSID:"+route.SSID+"\n");
						message.append("level:"+route.level+"\n");
						message.append("frequency:"+route.frequency+"\n");
						message.append("capabilities:"+route.capabilities+"\n");		
						message.append("timestamp:"+route.timestamp+"\n");
						if(route.BSSID.equalsIgnoreCase("d0:c7:c0:dc:7a:14"))
						{
							
							view[1].setRadius((float) (d*scale));
							view[1].invalidate();
						} else if(route.BSSID.equalsIgnoreCase("14:e6:e4:57:81:3c"))
						{
							view[2].setRadius((float) (d*scale));
							view[2].invalidate();
						}
						sender.append(route); // send data to server database
					}
				} catch(Exception e) { System.out.println(e); }
			}
		};
	}
	
	public void Start() {
		if(!running) {
			context.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
			mHandler.post(new TimerProcess());
			running = true;
		}
	}
	
	public void Stop() {
		if(running) {
			context.unregisterReceiver(wifiReceiver);
			running = false;
		}
	}

	private class TimerProcess implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			wm.startScan();
			mHandler.postDelayed(this, 1000);
		}
	}

	public RingView[] getView() {
		return view;
	}

	public void setView(RingView[] view) {
		this.view = view;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public float getDamping() {
		return damping;
	}

	public void setDamping(float damping) {
		this.damping = damping;
	}

	public int getMetaRSSI() {
		return metaRSSI;
	}

	public void setMetaRSSI(int metaRSSI) {
		this.metaRSSI = metaRSSI;
	}
	
}
