package com.romario.misoilab3.form;

import java.awt.image.BufferedImage;

/**
 * Created by romario on 11/21/14.
 */
public class Form {

  private BufferedImage sourceBufferedImage;
  private BufferedImage resultBufferedImage;


  private static Form instance;

  private Form() {

  }

  public static Form getInstance() {
    if (instance == null) {
      instance = new Form();
    }
    return instance;
  }

  public BufferedImage getSourceBufferedImage() {
    return sourceBufferedImage;
  }

  public void setSourceBufferedImage(BufferedImage sourceBufferedImage) {
    this.sourceBufferedImage = sourceBufferedImage;
  }

  public BufferedImage getResultBufferedImage() {
    return resultBufferedImage;
  }

  public void setResultBufferedImage(BufferedImage resultBufferedImage) {
    this.resultBufferedImage = resultBufferedImage;
  }

}
