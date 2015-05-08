package com.rainmote.wifi;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv;
	private WifiManager wm;
	private BroadcastReceiver wifiReceiver;
	private Handler mHandler;
	private Map<String, Integer> wifi;
	private int count = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		wifi = new HashMap<String, Integer>();
		tv = (TextView)findViewById(R.id.textView1);
		
		String wserviceName = Context.WIFI_SERVICE;
		wm = (WifiManager)getSystemService(wserviceName);
		wifiReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				List<ScanResult> results = wm.getScanResults();
				Collections.sort(results, new Comparator<ScanResult>() {
					public int compare(ScanResult a, ScanResult b) {
						return b.level - a.level;
					}
				});
				try {
					String data = "";
					for(ScanResult result : results) {
						data += result.level + "  " + result.SSID + "  ";
						if(wifi.get(result.BSSID) == null) {
							wifi.put(result.BSSID, result.level);
							data += "\n";
						} else {
							//double rssi = wifi.get(result.BSSID)/count;
							double rssi = result.level;
							double n = 2.1;
							int A = -30;
							double d = Math.pow(Math.E, (A-rssi)*Math.log(10)/(10*n));
							data += wifi.get(result.BSSID)/count + "  ->" + d +"\n";
							wifi.put(result.BSSID, wifi.get(result.BSSID) + result.level);	
						}
					}
					tv.setText(data);
				}catch (Exception e) {}
				count++;
			}
		};
		mHandler = new Handler();
		mHandler.post(new TimerProcess());
	}
	
	private class TimerProcess implements Runnable {
		public void run() {
			wm.startScan();
			mHandler.postDelayed(this, 1000);
		}
	}
	
	public void onResume() {
		super.onResume();
		registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}
	
	public void onPause() {
		super.onPause();
		unregisterReceiver(wifiReceiver);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
