package com.xiyoupositioning.util;

public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Point A) {
		if(this.x == A.x && this.y == A.y) {
			return true;
		}
		return false;
	}
}
