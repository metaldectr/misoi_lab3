package com.romario.misoilab3.common;

import com.romario.misoilab3.form.Blob;

import java.util.ArrayList;

/**
 * Created by romario on 12/8/14.
 */
public class ImageUtil {

	private static final double OVERLAP_THRESHOLD = 0.25d;

	public static double[][] buildLogMatrix(double sigma) {
		int matrixSize = (int) Math.ceil(6 * sigma);
		matrixSize = matrixSize % 2 == 0 ? matrixSize + 1 : matrixSize;
		int width = matrixSize / 2 + 1;
		double[][] logMatrix = new double[matrixSize][matrixSize];
		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				logMatrix[i][j] = log(distance(i, j, width, width), sigma);
			}
		}
		return logMatrix;
	}

	public static ArrayList<Blob> separateBlobs(ArrayList<ScaleLevel> scaleLevels) {
		ArrayList<Blob> blobs = getBlobs(scaleLevels);
		boolean[] kept = new boolean[blobs.size()];
		for (int i = 0; i < kept.length; i++) {
			kept[i] = true;
		}
		int count = blobs.size();
		for (int i = 0; i < count - 1; i++) {
			for (int j = i + 1; j < count; j++) {
				if (kept[i] == false || kept[j] == false) {
					continue;
				}
				double overlapPercent = calculateOverlapPercent(blobs.get(i), blobs.get(j));
				if (overlapPercent > OVERLAP_THRESHOLD) {
					if (blobs.get(i).getIntensity() > blobs.get(j).getIntensity()) {
						kept[i] = true;
						kept[j] = false;
					}
					else {
						kept[i] = false;
						kept[j] = true;
					}
				}
			}
		}
		ArrayList<Blob> result = new ArrayList<Blob>();
		for (int i = 0; i < count; i++) {
			if (kept[i]) {
				result.add(blobs.get(i));
			}
		}
		return result;
	}

	private static ArrayList<Blob> getBlobs(ArrayList<ScaleLevel> scaleLevels) {
		ArrayList<Blob> blobs = new ArrayList<Blob>();
		for (ScaleLevel sl: scaleLevels) {
			blobs.addAll(sl.getBlobs());
		}
		return blobs;
	}

	private static double calculateOverlapPercent(Blob first, Blob second) {
		double centerDistance = distance(first.getX(), first.getY(),
				second.getX(), second.getY());
		double r1Sqaure = first.getRadius() * first.getRadius();
		double dSquare = centerDistance * centerDistance;
		double r2Square = second.getRadius() * second.getRadius();
		double overlapArea = r1Sqaure * Math.acos((dSquare + r1Sqaure - r2Square) / (2 * centerDistance * first.getRadius())) +
				r2Square * Math.acos((dSquare + r2Square - r1Sqaure) / (2 * centerDistance * second.getRadius())) -
				0.5 * Math.sqrt(
						(-centerDistance + first.getRadius() + second.getRadius()) * (centerDistance + first.getRadius() - second.getRadius()) *
								(centerDistance - first.getRadius() + second.getRadius()) * (centerDistance + first.getRadius() + second.getRadius()));
		double overlapPercent = Math.max(overlapArea / first.getArea(), overlapArea / second.getArea());
		return overlapPercent;
	}

	private static double log(double x, double sigma) {
		return (((x * x) - (2 * sigma * sigma)) / (2 * Math.PI * Math.pow(sigma, 4))) * gauss(x, sigma);
	}

	private static double distance(double x1, double y1, double x2, double y2) {
		return norm(x1 - x2, y1 - y2);
	}

	private static double norm(double x, double y) {
		return Math.sqrt((x * x) + (y * y));
	}

	private static double gauss(double x, double sigma) {
		return Math.exp((-x * x) / (2 * sigma * sigma));
	}
}
