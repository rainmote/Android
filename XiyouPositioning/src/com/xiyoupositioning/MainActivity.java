package com.xiyoupositioning;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.api.maps2d.model.CircleOptions;

public class MainActivity extends Activity {
	
	private ImageView backgroundImage;
	private TextView route1, route2;
	private float[] abscissa = new float[32];
	private float[] ordinate = new float[32];
	
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
		
		abscissa[0] = 500;
		ordinate[0] = 500;
		
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
				abscissa[0] = (float) (img_coordinates[0]+backgroundImage.getWidth()*99.0/470);
				ordinate[0] = (float) (img_coordinates[1]-contentTop+backgroundImage.getHeight()*151.0/618);
				abscissa[1] = (float) (img_coordinates[0]+backgroundImage.getWidth()*397.0/470);
				ordinate[1] = (float) (img_coordinates[1]-contentTop+backgroundImage.getHeight()*452.0/618);
				// route1
				route1.setX(abscissa[0] - MainActivity.dip2px(MainActivity.this, 5));
				route1.setY(ordinate[0] - MainActivity.dip2px(MainActivity.this, 5));
				// route2
				route2.setX(abscissa[1] - MainActivity.dip2px(MainActivity.this, 5));
				route2.setY(ordinate[1] - MainActivity.dip2px(MainActivity.this, 5));
				
				FrameLayout.LayoutParams flLayoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				RingView view = new RingView(MainActivity.this);
				MainActivity.this.addContentView(view, flLayoutParams);
				view.setCenterX(abscissa[0]);
				view.setCenterY(ordinate[0]);
				view.setRadius(70);
				view.invalidate();
				RingView view2 = new RingView(MainActivity.this);
				MainActivity.this.addContentView(view2, flLayoutParams);
				view2.setCenterX(abscissa[1]);
				view2.setCenterY(ordinate[1]);
				view2.setRadius(10*1.414f);
				view2.invalidate();
			}
		});
		
	}
	
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dpValue * scale + 0.5f);
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