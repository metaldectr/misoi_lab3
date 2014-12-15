package com.romario.misoilab3.filter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by romario on 10/7/14.
 */
public class ImageConverter {

  public static BufferedImage convertRGBImageToBWImage(BufferedImage image) {
    BufferedImage tmpImage =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
	  Graphics g = tmpImage.getGraphics();
	  g.drawImage(image, 0, 0, null);
	  g.dispose();

    return tmpImage;
  }

}
