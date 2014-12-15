package com.romario.misoilab3.filter;

import com.romario.misoilab3.common.ImageUtil;
import com.romario.misoilab3.common.ImageWrapper;

/**
 * Created by romario on 12/8/14.
 */
public class LogFilter {

	private double t;
	private double sigma;
	private double[][] logMatrix;

	public LogFilter(double t) {
		this.t = t;
		this.sigma = Math.exp(t);
	}

	public double getT() {
		return t;
	}

	public double getSigma() {
		return sigma;
	}

	public void applyFilter(ImageWrapper imageWrapper) {
		logMatrix = ImageUtil.buildLogMatrix(sigma);
		convolve(imageWrapper);
	}

	private void convolve(ImageWrapper imageWrapper) {
		ImageWrapper imageCopy = imageWrapper.getCopy();
		int halfSize = logMatrix[0].length / 2;
		int logMatrixSize = logMatrix[0].length;
		for (int i = 0; i < imageWrapper.getHeight(); i++) {
			for (int j = 0; j < imageWrapper.getWidth(); j++) {
				double value = 0.0;
				for (int ii = 0; ii < logMatrixSize; ii++) {
					int n = i - halfSize + ii;
					if (n < 0 || n >= imageWrapper.getHeight()) {
						continue;
					}
					for (int jj = 0; jj < logMatrixSize; jj++) {
						int m = j - logMatrixSize + jj;
						if (m < 0 || m >= imageWrapper.getWidth()) {
							continue;
						}
						value += logMatrix[ii][jj] * imageCopy.getImageData()[n][m];
					}
				}
				imageWrapper.getImageData()[i][j] = value;
			}
		}
	}
}
