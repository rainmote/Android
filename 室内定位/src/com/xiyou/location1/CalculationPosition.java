package com.xiyou.location1;

public class CalculationPosition {
	
	private int A;		// 参数A
	private double n;	// 参数n
	
	
	public CalculationPosition() {
	}
	
	public CalculationPosition(int A, double n) {
		this.A = A;
		this.n = n;
	}
	
	public double getRssiPosition(double rssi) {
		return Math.pow(Math.E, (A-rssi)*Math.log(10)/(10*n));
	}
	
	public double getPosition(double[] d) {
		return getTwoPointDistance(d[0], d[1], d[2], d[3]);
	}
	
	private double getTwoPointDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
}
