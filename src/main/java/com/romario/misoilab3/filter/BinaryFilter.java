package com.romario.misoilab3.filter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by romario on 11/14/14.
 */
public class BinaryFilter {

  public static BufferedImage bynaryImage(BufferedImage image) {

    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        int currentPixel = image.getRGB(i, j);

        int red = (currentPixel >> FilterConstant.RED_VALUE) & 0xFF;
        int green = (currentPixel >> FilterConstant.GREEN_VALUE) & 0xFF;
        int blue = (currentPixel & 0xFF);

        int y = (int) (0.3 * red + 0.59 * green + 0.11 * blue);

        if (y > FilterConstant.AVERAGE_COLOR_VALUE) {
          image.setRGB(i, j, colorValue(FilterConstant.WHITE_VALUE));
        } else {
          image.setRGB(i, j, colorValue(FilterConstant.BLACK_VALUE));
        }

      }
    }

    return image;
  }

  public static int colorValue(int val) {

    return ((val << FilterConstant.ALPHA_VALUE) | (val << FilterConstant.RED_VALUE))
        | ((val << FilterConstant.GREEN_VALUE)) | (val);
  }

	public static int colorRGBValue(int r, int g, int b) {
		return ((b << FilterConstant.ALPHA_VALUE) | (r << FilterConstant.RED_VALUE))
				| ((g << FilterConstant.GREEN_VALUE)) | (b);
	}

	private static BufferedImage cleanImage(BufferedImage image, List<Point> indexesMap) {

		int blackPixel = colorValue(FilterConstant.BLACK_VALUE);
		for (Point point : indexesMap) {
			image.setRGB((int)point.getX(), (int)point.getY(), blackPixel);
		}
		return image;
	}

	public static Map<Integer, List<Point>> simpleFilter(BufferedImage image, Map<Integer, List<Point>> areasIndexesMap) {
		Map<Integer, List<Point>> tmpMap = new HashMap<>();
		for (Integer indexMap : areasIndexesMap.keySet()) {
			if (areasIndexesMap.get(indexMap).size() > FilterConstant.SIMPLE_FILTER_VALUE) {
				tmpMap.put(indexMap, areasIndexesMap.get(indexMap));
			} else {
				cleanImage(image, areasIndexesMap.get(indexMap));
			}
		}

		return tmpMap;
	}

}
