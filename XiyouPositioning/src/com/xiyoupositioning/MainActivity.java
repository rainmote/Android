package com.xiyoupositioning;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ImageView backgroundImage;
	private TextView route1, route2;
	
	private void findView() {
		backgroundImage = (ImageView)findViewById(R.id.backgroundImage);
		route1 = (TextView)findViewById(R.id.route1);
		route2 = (TextView)findViewById(R.id.route2);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findView();
		backgroundImage.setImageResource(R.drawable.map);
		backgroundImage.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int dw = backgroundImage.getDrawable().getBounds().width();
				int dh = backgroundImage.getDrawable().getBounds().height();
				
				int[] img_coordinates = new int[2];
				backgroundImage.getLocationInWindow(img_coordinates);
				
				getWindow().findViewById(Window.ID_ANDROID_CONTENT);
				int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
				
				DisplayMetrics dm = new DisplayMetrics();
				dm = getResources().getDisplayMetrics();
				float densityDPI = dm.densityDpi;
				// route1
				route1.setX((float) (img_coordinates[0]+backgroundImage.getWidth()*99.0/470));
				route1.setY((float) (img_coordinates[1]-contentTop+backgroundImage.getHeight()*151.0/618));
				// route2
				route2.setX((float) (img_coordinates[0]+backgroundImage.getWidth()*397.0/470));
				route2.setY((float) (img_coordinates[1]-contentTop+backgroundImage.getHeight()*452.0/618));
			}
			
		});
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