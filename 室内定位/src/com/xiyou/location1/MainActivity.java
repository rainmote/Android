package com.xiyou.location1;

import android.app.Activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageView imageView;
	private TextView text;
	private TextView route1,route2;
//	private WifiManager wm;
//	private Handler mHandler;
//	private CalculationPosition calc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		findView();
		imageView.setImageResource(R.drawable.map);
		imageView.post(new Runnable() {
			public void run() {
				
				int dw = imageView.getDrawable().getBounds().width();
				int dh = imageView.getDrawable().getBounds().height();
			
				
				int[] img_corrdinates = new int[2];
				imageView.getLocationInWindow(img_corrdinates);
				
				getWindow().findViewById(Window.ID_ANDROID_CONTENT);
				int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
				
				DisplayMetrics dm = new DisplayMetrics();
				dm = getResources().getDisplayMetrics();
				float densityDPI = dm.densityDpi;
				text.setText(imageView.getWidth()+"\t"+imageView.getHeight()+
						"\n"+dw+"\t"+dh+
						"\n"+img_corrdinates[0]+"\t"+img_corrdinates[1]+
						"\ndpi:"+densityDPI);
				//路由器1
				route1.setX((float) (img_corrdinates[0]+imageView.getWidth()*99.0/470));
				route1.setY((float) (img_corrdinates[1]-contentTop+imageView.getHeight()*151.0/618));
				
				//路由器2
				route2.setX((float) (img_corrdinates[0]+imageView.getWidth()*397.0/470));
				route2.setY((float) (img_corrdinates[1]-contentTop+imageView.getHeight()*452.0/618));
			}
		});
		
		setContentView(new DisplayDistanceRange(this));
		//calc = new CalculationPosition(-30, 2.1);
		
//		String wserviceName = Context.WIFI_SERVICE;
//		wm = (WifiManager)getSystemService(wserviceName);
//		new BroadcastReceiver() {
//			public void onReceive(Context context, Intent intent) {
//				List<ScanResult> results = wm.getScanResults();
//				Collections.sort(results, new Comparator<ScanResult>() {
//					public int compare(ScanResult a, ScanResult b) {
//						return b.level - a.level;
//					}
//				});
//				double[] d = new double[3];
//				for(int i = 0; i < 3; i++) {
//					d[i] = calc.getRssiPosition(results.get(i).level);
//				}	
//			}
//		};
//		mHandler = new Handler();
//		mHandler.post(new TimerProcess());
	}
	
	private void findView() {
		route1 = (TextView)findViewById(R.id.route1);
		route2 = (TextView)findViewById(R.id.route2);
		imageView = (ImageView)findViewById(R.id.imageView1);
		text = (TextView)findViewById(R.id.text);
	}
	
//	private class TimerProcess implements Runnable {
//		public void run() {
//			wm.startScan();
//			mHandler.postDelayed(this, 3000);
//		}
//	}
	
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
