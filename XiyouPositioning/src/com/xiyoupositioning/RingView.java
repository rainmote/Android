package com.xiyoupositioning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RingView extends View {

	private final Paint paint;
	private final Context context;
	private float centerX = 0;
	private float centerY = 0;
	private float radius = 0.1f;

	public RingView(Context context) {
		this(context, null);
	}
	
	public RingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setStyle(Paint.Style.FILL);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int innerCircle = dip2px(context, this.radius);
		this.paint.setARGB(155, 167, 190, 206);
		canvas.drawCircle(this.centerX, this.centerY, this.radius, this.paint);
		super.onDraw(canvas);
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
	}
	
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dpValue * scale + 0.5f);
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
}
