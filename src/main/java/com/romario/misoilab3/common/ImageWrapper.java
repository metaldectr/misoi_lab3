package com.romario.misoilab3.common;

import java.awt.image.BufferedImage;

/**
 * Created by romario on 12/8/14.
 */
public class ImageWrapper {

	private double[][] imageData;
	private int width;
	private int height;

	public ImageWrapper(BufferedImage image) {
		initialize(image);
	}

	private ImageWrapper(ImageWrapper imageWrapper) {
		width = imageWrapper.getWidth();
		height = imageWrapper.getHeight();
		imageData = new double[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				imageData[i][j] = imageWrapper.getImageData()[i][j];
			}
		}
	}

	public double[][] getImageData() {
		return imageData;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ImageWrapper getCopy() {
		return new ImageWrapper(this);
	}

	private void initialize(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		imageData = new double[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rgb = image.getRGB(j, i);
				int red = (rgb >> 16) & 0xFF, green = (rgb >> 8) & 0xFF, blue = rgb & 0xFF;
				imageData[i][j] = (double) (red + green + blue) / 3.0d;
			}
		}
	}
}
