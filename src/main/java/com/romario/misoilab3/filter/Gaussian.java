package com.romario.misoilab3.filter;

import java.awt.image.BufferedImage;

/**
 * Created by romario on 11/23/14.
 */
public class Gaussian {

	private final float[][] matrixLaplacian = {
			{0, 1, 0},
			{1, -4, 1},
			{0, 1, 0}
	};

	private static final double PHI = 1.4;

	public double[][] generateGaussianMatrix(double phi) {
		int sizeArray = (int)(3 * 2 * phi);
		double[][] matrixGaussian = new double[sizeArray][sizeArray];

		for (int i = 0; i < sizeArray/2; i++) {
			for (int j = 0; j < sizeArray/2; j++) {
				matrixGaussian[i][j] = calculateGaussian(phi, i, j);
			}
		}

		return matrixGaussian;
	}

	private double calculateGaussian(double phi, int x, int y) {
		return ((1 / (2 * Math.PI * phi * phi))) * Math.exp(-((x * x + y * y) / (2 * phi * phi)));
	}

	private double[][] normalizeMatrix(double[][] matrixGaussian, int sizeMatrix, double sumMatrixGaussian) {
		for (int i = 0; i < sizeMatrix; i++) {
			for (int j = 0; j < sizeMatrix; j++) {
				matrixGaussian[i][j] /= sumMatrixGaussian;
			}
		}
		return matrixGaussian;
	}

	private double calculateSumMatrix(double[][] matrix, int sizeMatrix) {
		double sum = 0;
		for (int i = 0; i < sizeMatrix; i++) {
			for (int j = 0; j < sizeMatrix; j++) {
				sum += matrix[i][j];
			}
		}
		return sum;
	}


	public BufferedImage convertImage(BufferedImage sourceImage, int standardDeviation) {
		BufferedImage resultImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());


		return resultImage;
	}

}
