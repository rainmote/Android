package com.xiyoupositioning;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;

public class WifiSniffer {
	
	private WifiManager wm;
	private BroadcastReceiver wifiReceiver;
	private Handler mHandler;
	private Map<String, Integer> wifi;
	private Context context;
	private boolean running = false;
	
	public WifiSniffer(Context ctx, final RingView[] view, final double scale) {
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
					for(int i = 0; i < 3; ++i) {
						double rssi = results.get(i).level;
						double n = 2.1;
						int A = -30;
						float d = (float) Math.pow(Math.E, (A-rssi)*Math.log(10)/(10*n));
						//view[i].setRadius((float) (d*491/8.7)); // 491 photo width. 8.7 meter width
						view[i].setRadius((float) (d*scale));
						view[i].invalidate();
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
	
	

}
