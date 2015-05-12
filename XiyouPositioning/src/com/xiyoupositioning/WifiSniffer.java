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
import android.widget.TextView;

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
					for(int i = 0; i < 3; ++i) {
						double rssi = results.get(i).level;
						double n = damping;
						int A = metaRSSI;
						float d = (float) Math.pow(Math.E, (A-rssi)*Math.log(10)/(10*n));
						//view[i].setRadius((float) (d*491/8.7)); // 491 photo width. 8.7 meter width
						message.setText("Damping:"+damping+"\n"+
								"Meta RSSI:"+metaRSSI);
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
