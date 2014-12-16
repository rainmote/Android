package com.xiyou.xiyoumap;
  
import com.xiyou.xiyoumap.ImageControl.ICustomMethod;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
	}
	
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		init();
	}
	
	ImageControl imgControl;
	LinearLayout llTitle;
	TextView tvTitle;
	
	private void findView() {
		imgControl = (ImageControl)findViewById(R.id.common_imageview_imageControl1);
		llTitle = (LinearLayout)findViewById(R.id.common_imageview_llTitle);
		tvTitle = (TextView)findViewById(R.id.common_imageview_title);
	}
	
	private void init() {
		tvTitle.setText("test");
		//ImageView.setImageResource(R.drawable.map);
		//imgControl.setImageResource(R.drawable.map);
		Bitmap bmp;
		if (imgControl.getDrawingCache() != null) {
			bmp = Bitmap.createBitmap(imgControl.getDrawingCache());
		} else {
			bmp = ((BitmapDrawable)imgControl.getDrawable()).getBitmap();
		}
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		Display display = getWindowManager().getDefaultDisplay(); 
		Point size = new Point(); 
		display.getSize(size); 
		int screenW = size.x; 
		int screenH = size.y;
//		int screenW = this.getWindowManager().getDefaultDisplay().getSize();
//		int screenH = this.getWindowManager().getDefaultDisplay().getHeight() - statusBarHeight;
		if (bmp != null) {
			imgControl.imageInit(bmp, screenW, screenH, statusBarHeight, 
					new ICustomMethod() {
						public void customMethod(Boolean currentStatus) {
							// 当图片处于放大或缩小状态时，控制标题是否显示   
                            if  (currentStatus) {  
                            	llTitle.setVisibility(View.GONE);  
                            }  else  {  
                            	llTitle.setVisibility(View.VISIBLE);  
                            }  
						}
			});
		} else {
			Toast.makeText(this, "图片加载失败，请稍候再试！", Toast.LENGTH_SHORT).show();
		}
	}
	

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			imgControl.mouseDown(event);
			break;
		
		//非第一个点按下
		case MotionEvent.ACTION_POINTER_DOWN:
			imgControl.mousePointDown(event);
			break;
			
		case MotionEvent.ACTION_MOVE:
			imgControl.mouseMove(event);
			break;
			
		case MotionEvent.ACTION_UP:
			imgControl.mouseUp();
			break;
		}
		return super.onTouchEvent(event);
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
