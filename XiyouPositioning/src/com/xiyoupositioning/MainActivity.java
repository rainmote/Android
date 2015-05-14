package com.xiyoupositioning;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	
	private WifiSniffer wifi;
	private ImageView backgroundImage;
	private TextView route1, route2, route3;
	private TextView message;
	private float[] abscissa = new float[32];
	private float[] ordinate = new float[32];
	private double scale;
	private float damping = 2.1f;
	private int metaRSSI = -50;
	
	private SeekBar mDampingBar;
	private SeekBar mMetaRssiBar;
	
	private Button startBtn;
	private Button stopBtn;
	
	private void findView() {
		backgroundImage = (ImageView)findViewById(R.id.backgroundImage);
		route1 = (TextView)findViewById(R.id.route1);
		route2 = (TextView)findViewById(R.id.route2);
		route3 = (TextView)findViewById(R.id.route3);
		message = (TextView)findViewById(R.id.message);
		startBtn = (Button) findViewById(R.id.startBtn);
		stopBtn = (Button) findViewById(R.id.stopBtn);
		
//		mDampingBar = (SeekBar) findViewById(R.id.damping);
//		mDampingBar.setProgress(40);
//		mMetaRssiBar = (SeekBar) findViewById(R.id.metaRssi);
//		mMetaRssiBar.setProgress(50);
		
		
//		SeekBarListener listener = new SeekBarListener();
//		mDampingBar.setOnSeekBarChangeListener(listener);
//		mMetaRssiBar.setOnSeekBarChangeListener(listener);
		
		ButtionListener buttonListener = new ButtionListener();
		startBtn.setOnClickListener(buttonListener);
		stopBtn.setOnClickListener(buttonListener);
	}
	
	class SeekBarListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			if (seekBar == mDampingBar) {
				damping = (float) (progress/20.0);
				wifi.setDamping(damping);
			} else if (seekBar == mMetaRssiBar) {
				metaRSSI = progress - 100;
				wifi.setMetaRSSI(metaRSSI);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
	}
	
	class ButtionListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
				case R.id.startBtn:
					if(!wifi.isSendRunning())
					{
						startBtn.setText("Running");
						wifi.setSendRunning(true);
					}
					break;
				case R.id.stopBtn:
					if(wifi.isSendRunning())
					{
						startBtn.setText("Start");
						wifi.setSendRunning(false);
					}
					break;
			}	
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		
		abscissa[0] = 242.66f;
		ordinate[0] = 371.39f;
		abscissa[1] = 973.07f;
		ordinate[1] = 1111.72f;
		abscissa[2] = 237.10f;
		ordinate[2] = 1200.08f;
		scale = 1152/8.7f;
		
		backgroundImage.setImageResource(R.drawable.map);
		backgroundImage.post(new Runnable() {
			@Override
			public void run() {
				int dw = backgroundImage.getDrawable().getBounds().width();
				int dh = backgroundImage.getDrawable().getBounds().height();
				
				int[] img_coordinates = new int[2];
				backgroundImage.getLocationInWindow(img_coordinates);
				
				getWindow().findViewById(Window.ID_ANDROID_CONTENT);
				int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
				
				DisplayMetrics dm = new DisplayMetrics();
				dm = getResources().getDisplayMetrics();
				float densityDPI = dm.densityDpi;
				abscissa[0] = (float) (img_coordinates[0]+backgroundImage.getWidth()*99.0/470);
				ordinate[0] = (float) (img_coordinates[1]-contentTop+backgroundImage.getHeight()*151.0/618);
				abscissa[1] = (float) (img_coordinates[0]+backgroundImage.getWidth()*397.0/470);
				ordinate[1] = (float) (img_coordinates[1]-contentTop+backgroundImage.getHeight()*452.0/618);
			
//				message.setText("Canvas Size:"+dw+" x "+dh+"\n"+
//						"Image Size:"+backgroundImage.getWidth()+" x "+backgroundImage.getHeight()+"\n"+
//						"Background location:("+img_coordinates[0]+", "+img_coordinates[1]+")\n"+
//						"Dpi:"+densityDPI+"\n"+
//						"Scale:"+String.format("%.2f", scale));
				// route1
				route1.setX(abscissa[0] - MainActivity.dip2px(MainActivity.this, 5));
				route1.setY(ordinate[0] - MainActivity.dip2px(MainActivity.this, 5));
				// route2
				route2.setX(abscissa[1] - MainActivity.dip2px(MainActivity.this, 5));
				route2.setY(ordinate[1] - MainActivity.dip2px(MainActivity.this, 5));
				// route3
				route3.setX(abscissa[2] - MainActivity.dip2px(MainActivity.this, 5));
				route3.setY(ordinate[2] - MainActivity.dip2px(MainActivity.this, 5));
			}
		});
		
		FrameLayout.LayoutParams flLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		RingView[] view = new RingView[3];
		view[0] = new RingView(this);
		view[1] = new RingView(this);
		view[2] = new RingView(this);
		this.addContentView(view[0], flLayoutParams);
		this.addContentView(view[1], flLayoutParams);
		this.addContentView(view[2], flLayoutParams);
		view[0].setCenterX(abscissa[0]);
		view[0].setCenterY(ordinate[0]);
		view[1].setCenterX(abscissa[1]);
		view[1].setCenterY(ordinate[1]);		
		view[2].setCenterX(abscissa[2]);
		view[2].setCenterY(ordinate[2]);
		wifi = new WifiSniffer(this, message);
		wifi.setView(view);
		wifi.setDamping(damping);
		wifi.setMetaRSSI(-50);
		wifi.setScale(scale);
		wifi.Start();
//		MainActivity.this.addContentView(view, flLayoutParams);
//		view.setCenterX(abscissa[0]);
//		view.setCenterY(ordinate[0]);
//		view.setRadius(650);
//		view.invalidate();
//		RingView view2 = new RingView(MainActivity.this);
//		MainActivity.this.addContentView(view2, flLayoutParams);
//		view2.setCenterX(abscissa[1]);
//		view2.setCenterY(ordinate[1]);
//		view2.setRadius(500);
//		view2.invalidate();
//		RingView view3 = new RingView(MainActivity.this);
//		MainActivity.this.addContentView(view3, flLayoutParams);
//		view3.setCenterX(abscissa[2]);
//		view3.setCenterY(ordinate[2]);
//		view3.setRadius(500);
//		view3.invalidate();
		
	}
	
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dpValue * scale + 0.5f);
	}
	
	public void onResume() {
		super.onResume();
		wifi.Start();
	}
	
	public void onPause() {
		super.onPause();
		wifi.Stop();
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