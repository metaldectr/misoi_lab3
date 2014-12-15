package com.romario.misoilab3.common;

import com.romario.misoilab3.filter.LogFilter;
import com.romario.misoilab3.form.Blob;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by romario on 12/8/14.
 */
public class BlobDetector {

	private static final double START_T = 1.0d;
	private static final double STEP = 0.1d;
	private static final double END_T = 3.0d;

	private BufferedImage originalImage;
	private ImageWrapper originalImageWrapper;
	private ArrayList<ScaleLevel> scaleLevels;

	public BlobDetector(BufferedImage image) {
		originalImage = image;
		originalImageWrapper = new ImageWrapper(image);
		scaleLevels = new ArrayList<ScaleLevel>((int)(((END_T - START_T) / STEP) + 1));
	}

	public BufferedImage detectBlobs() {
		createScaleSpace();
		ArrayList<Blob> blobs = ImageUtil.separateBlobs(scaleLevels);
		blobs = cleanBlobs(blobs);
		BufferedImage result = applyBlobs(blobs);
		return result;
	}

	private ArrayList<Blob> cleanBlobs(ArrayList<Blob> blobs) {

		ArrayList<Blob> tmp = new ArrayList<>();
		ArrayList<Blob> delBlob = new ArrayList<>();
		for (Blob blob : blobs) {
			for (Blob blobk : blobs) {
				if (blob.getRadius() > blobk.getRadius()
						&& (getDistance(blob.getX(), blob.getY(), blobk.getX(), blobk.getY()) < blob.getRadius())) {
						delBlob.add(blob);
					continue;
				} else {
					tmp.add(blob);
				}
			}
		}
		tmp.removeAll(delBlob);
		/*for (Blob blob : tmp) {
			blob.setX(blob.getX() + 4);
		}*/
		return tmp;
	}

	private double getDistance(double x, double y, double x1, double y1) {
		return Math.sqrt(((x - x1) * (x - x1)) + ((y - y1) * (y - y1)));
	}

	private BufferedImage applyBlobs(ArrayList<Blob> blobs) {
		int red = Color.RED.getRGB();
		for (Blob b: blobs) {
			ArrayList<Point> points = b.getPoints();
			for (Point p: points) {
				if (p.getX() > 0 && p.getX() < originalImage.getHeight() && p.getY() > 0 && p.getY() + 7 < originalImage.getWidth()) {
					originalImage.setRGB((int)p.getY() + 7, (int)p.getX(), red);
				}
			}
		}
		return originalImage;
	}

	private void createScaleSpace() {
		int count = (int)(((END_T - START_T) / STEP) + 1);
		for (int i = 0; i < count; i++) {
			LogFilter logFilter = new LogFilter(START_T + (i * STEP));
			ScaleLevel scaleLevel = new ScaleLevel(logFilter, originalImageWrapper.getCopy());
			scaleLevel.applyTransform().findBlobs();
			scaleLevels.add(scaleLevel);
		}
	}
}
