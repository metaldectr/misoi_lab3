package com.romario.misoilab3.filter;

import java.awt.image.BufferedImage;

/**
 * Created by romario on 11/23/14.
 */
public class Gaussian {


	public BufferedImage convertImage(BufferedImage sourceImage, double sigma) {
		BufferedImage resultImage =
				new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());


		double[][] filterMatrix = generateLaplassianGaussianMatrix(sigma);

		double sum = 0;
		for (int i = 0; i < filterMatrix.length; i++) {
			for (int j = 0; j < filterMatrix.length; j++) {
				sum += filterMatrix[i][j];
			}
		}
		System.out.println(sum);
		convolveImage(sourceImage, resultImage, filterMatrix);

		return resultImage;
	}

	private double[][] generateLaplassianGaussianMatrix(double sigma) {
		int sizeArray = (int) (6 * sigma);
		sizeArray = sizeArray % 2 == 0 ? sizeArray : sizeArray + 1;
		int width = sizeArray / 2 + 1;
		double[][] matrixGaussian = new double[sizeArray][sizeArray];

		for (int i = 0; i < sizeArray; i++) {
			for (int j = 0; j < sizeArray; j++) {
				matrixGaussian[i][j] = calculateLoG(calculateDistance(i, j, width), sigma);
			}
		}

		return matrixGaussian;
	}

	private BufferedImage convolveImage(BufferedImage sourceImage, BufferedImage destinationImage, double[][] filterMatrix) {

		int halfSize = filterMatrix.length / 2;
		int logMatrixSize = filterMatrix.length;

		for (int i = 0; i < sourceImage.getHeight(); i++) {
			for (int j = 0; j < sourceImage.getWidth(); j++) {
				double value = 0;
				for (int ii = 0; ii < logMatrixSize; ii++) {
					int n = i - halfSize + ii;
					if (n < 0 || n >= sourceImage.getHeight()) {
						continue;
					}
					for (int jj = 0; jj < logMatrixSize; jj++) {
						int m = j - halfSize + jj;
						if (m < 0 || m >= sourceImage.getWidth()) {
							continue;
						}
						//int pixel = newPixel(sourceImage.getRGB(m, n), filterMatrix[ii][jj]);
						//value += pixel;
						value += sourceImage.getRGB(n, m) * filterMatrix[ii][jj];
					}
				}
				destinationImage.setRGB(i, j, (int)value);
			}
		}

		return destinationImage;
	}

	private int newPixel(int currentPixel, double filterValue) {

		int red = (currentPixel >> FilterConstant.RED_VALUE) & 0xFF;
		int green = (currentPixel >> FilterConstant.GREEN_VALUE) & 0xFF;
		int blue = (currentPixel & 0xFF);

		red *= red * filterValue;
		green *= green * filterValue;
		blue *= blue * filterValue;

		return colorRGBValue(red, green, blue);
	}

	private int colorRGBValue(int r, int g, int b) {
		return (255 | (r << FilterConstant.RED_VALUE))
				| ((g << FilterConstant.GREEN_VALUE)) | (b);
	}

	private double calculateDistance(int x, int y, int width) {
		double result = 0;

		result = Math.sqrt((x - width) * (x - width) + (y - width) * (y - width));

		return result;
	}

	private double calculateLoG(double x, double sigma) {
		return (((x * x) - (2 * sigma * sigma)) / (2 * Math.PI * Math.pow(sigma, 4))) * calculateGauss(
				x, sigma);
	}

	private double calculateGauss(double x, double sigma) {
		return Math.exp((-x * x) / (2 * sigma * sigma));
	}

	private double[][] normalizeMatrix(double[][] matrixGaussian, int sizeMatrix,
			double sumMatrixGaussian) {
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




}
