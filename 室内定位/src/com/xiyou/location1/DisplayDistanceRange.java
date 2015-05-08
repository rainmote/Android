package com.xiyou.location1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class DisplayDistanceRange extends View {

	public DisplayDistanceRange(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}
	
	public DisplayDistanceRange(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
	}

	protected void OnDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		canvas.drawRect(new Rect(10, 10, 100, 100), paint);
		invalidate();
	}

}
