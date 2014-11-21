package com.romario.misoilab3.gui;

import com.romario.misoilab3.gui.gbc.GBC;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by romario on 11/21/14.
 */
public class MyControlPanel extends JPanel {

  private static final int CELL_INSETS = 4;

  private final JButton openFileButton = new JButton("Choose File");

  private final JFileChooser fileChooser = new JFileChooser(new File(this.getClass()
      .getClassLoader().getResource("").getPath()));

  private final MyFrame frame;

  public MyControlPanel(MyFrame frame) {

    this.frame = frame;

    setBackground(Color.LIGHT_GRAY);
    setLayout(new GridBagLayout());

    initializeGUI();
    setListeners();
  }

  private void setListeners() {

    openFileButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        fileChooser.setFileFilter(new FileFilter() {
          @Override
          public boolean accept(File f) {
            if (f.isDirectory()) {
              return true;
            }
            final String name = f.getName();
            return name.endsWith(".jpg") || name.endsWith(".png");

          }

          @Override
          public String getDescription() {
            return "*.png, *.jpg";
          }
        });
        if (e.getSource() == openFileButton) {
          int returnVal = fileChooser.showOpenDialog(frame);
          if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println(file);

            BufferedImage img = openImage(file);
            frame.getForm().setSourceBufferedImage(img);
            frame.getForm().setResultBufferedImage(img);
            /* frame.getViewPanel().distributePanels(); */

            frame.getViewPanel().repaint();
          }
        }
      }
    });

  }

  /*
   * private int[][] viewImage(BufferedImage image) { int[][] tp = new int[651][400];
   * 
   * for(int i = 0; i < image.getWidth(); i++) { for(int j = 0; j < image.getHeight(); j++) {
   * tp[i][j] = image.getRGB(i, j) & 0XFF; } }
   * 
   * return tp; }
   */

  private void viewArray(int[][] array, int xLength, int yLength) {
    for (int i = 0; i < xLength; i++) {
      for (int j = 0; j < yLength; j++) {
        System.out.print(array[i][j]);
      }
      System.out.println();
    }
  }

  private void initializeGUI() {

    add(openFileButton, new GBC(0, 0).setInsets(CELL_INSETS).setAnchor(GridBagConstraints.WEST));

  }

  private BufferedImage openImage(final File file) {
    BufferedImage out = null;
    try {
      out = ImageIO.read(file);
      System.out.println(out);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out;
  }



}
