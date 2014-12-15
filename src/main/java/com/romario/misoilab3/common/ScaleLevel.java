package com.romario.misoilab3.common;

import com.romario.misoilab3.filter.LogFilter;
import com.romario.misoilab3.form.Blob;

import java.util.ArrayList;

/**
 * Created by romario on 12/8/14.
 */
public class ScaleLevel {

	private static final double INTENSITY_THRESHOLD = 100;
	private static final double SIZE_THRESHOLD = 1000;
	private static final double SHIFT_THRESHOLD = 20;

	private LogFilter logFilter;
	private ImageWrapper imageWrapper;
	private ArrayList<Blob> blobs;

	public ScaleLevel(LogFilter logFilter, ImageWrapper imageWrapper) {
		this.logFilter = logFilter;
		this.imageWrapper = imageWrapper;
		blobs = new ArrayList<Blob>();
	}

	public ArrayList<Blob> getBlobs() {
		return blobs;
	}

	public ImageWrapper getImageWrapper() {
		return imageWrapper;
	}

	public double getT() {
		return logFilter.getT();
	}

	public ScaleLevel applyTransform() {
		logFilter.applyFilter(imageWrapper);
		return this;
	}

	public void findBlobs() {
		for (int i = 1; i < imageWrapper.getHeight() - 1; i++) {
			for (int j = 1; j < imageWrapper.getWidth() - 1; j++) {
				if (isMaximum(i, j)) {
					int y = j - (int)(getT() * SHIFT_THRESHOLD);
					Blob blob = new Blob(i, y, logFilter.getSigma(), getImageWrapper().getImageData()[i][j]);
					if (blob.getArea() > SIZE_THRESHOLD) {
						blobs.add(blob);
					}
				}
			}
		}
	}

	private boolean isMaximum(int x, int y) {
		return getImageWrapper().getImageData()[x - 1][y - 1] <= getImageWrapper().getImageData()[x][y] &&
				getImageWrapper().getImageData()[x - 1][y] <= getImageWrapper().getImageData()[x][y] &&
				getImageWrapper().getImageData()[x - 1][y + 1] <= getImageWrapper().getImageData()[x][y] &&
				getImageWrapper().getImageData()[x][y - 1] <= getImageWrapper().getImageData()[x][y] &&
				getImageWrapper().getImageData()[x][y + 1] <= getImageWrapper().getImageData()[x][y] &&
				getImageWrapper().getImageData()[x + 1][y - 1] <= getImageWrapper().getImageData()[x][y] &&
				getImageWrapper().getImageData()[x + 1][y] <= getImageWrapper().getImageData()[x][y] &&
				getImageWrapper().getImageData()[x + 1][y + 1] <= getImageWrapper().getImageData()[x][y] &&
				getImageWrapper().getImageData()[x][y] < INTENSITY_THRESHOLD;
	}



}
