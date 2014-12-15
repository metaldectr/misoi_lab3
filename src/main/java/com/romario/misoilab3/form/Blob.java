package com.romario.misoilab3.form;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by romario on 12/8/14.
 */
public class Blob {

	private static final int POINT_NUMBER = 200;
	private static double STEP = Math.PI / POINT_NUMBER;
	private int x;
	private int y;
	private double sigma;
	private double intensity;

	public Blob(int x, int y, double sigma, double intensity) {
		this.x = x;
		this.y = y;
		this.sigma = sigma;
		this.intensity = intensity;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


	public double getSigma() {
		return sigma;
	}

	public double getRadius() {
		return sigma * 1.5d;
	}

	public double getIntensity() {
		return intensity;
	}

	public double getArea() {
		return Math.PI * getRadius() * getRadius();
	}

	public ArrayList<Point> getPoints() {
		ArrayList<Point> result = new ArrayList<Point>(POINT_NUMBER);
		for (double angle = 0.0d; angle < 2 * Math.PI; angle += STEP) {
			int x = this.x + (int)(getRadius() * Math.cos(angle));
			int y = this.y + (int)(getRadius() * Math.sin(angle));
			result.add(new Point(x, y));
		}
		return result;
	}
}
