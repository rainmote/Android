package com.xiyoupositioning.util;

public class Circle {
	private Point center;
	private double radius;
	private int color;
	private int strokeColor;
	private float strokeWidth;
	private boolean visible;
	private float zIndex;
	
	public Point getCenter() {
		return this.center;
	}
	
	public int getFillColor() {
		return this.color;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public int getStrokeColor() {
		return this.strokeColor;
	}
	
	public float getStrokeWidth() {
		return this.strokeWidth;
	}
	
	public float getZIndex() {
		return this.zIndex;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public void setFillColor(int color) {
		this.color = color;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public void setStrokeColor(int color) {
		this.strokeColor = color;
	}
	
	public void setStrokeWidth(float width) {
		this.strokeWidth = width;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setZIndex(float zIndex) {
		this.zIndex = zIndex;
	}
}
