package com.romario.misoilab3.gui;

import com.romario.misoilab3.common.BlobDetector;
import com.romario.misoilab3.filter.BynaryFilter;
import com.romario.misoilab3.filter.Gaussian;
import com.romario.misoilab3.filter.ImageConverter;
import com.romario.misoilab3.form.Form;
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

/**
 * Created by romario on 11/21/14.
 */
public class MyControlPanel extends JPanel {

  private static final int CELL_INSETS = 4;

  private final JButton openFileButton = new JButton("Choose File");
	private final JButton smoothingGaussianButton = new JButton("Get Blobs");

  private final JFileChooser fileChooser = new JFileChooser(new File(this.getClass()
      .getClassLoader().getResource("").getPath()));

  private final MyFrame frame;
	private Form form;

  public MyControlPanel(MyFrame frame) {

    this.frame = frame;
		this.form = frame.getForm();

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
            form.setSourceBufferedImage(img);
            form.setResultBufferedImage(img);
            /* frame.getViewPanel().distributePanels(); */

            frame.getViewPanel().repaint();
          }
        }
      }
    });

	  smoothingGaussianButton.addActionListener(new ActionListener() {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			  /*Gaussian gaussian = new Gaussian();*/
			  //form.setResultBufferedImage(BynaryFilter.bynaryImage(form.getSourceBufferedImage()));
			  /*form.setResultBufferedImage(
					  ImageConverter.convertRGBImageToBWImage(form.getSourceBufferedImage()));
			  form.setResultBufferedImage(gaussian.convertImage(form.getSourceBufferedImage(), Math.exp(1.3)));*/

			  BlobDetector blobDetector = new BlobDetector(form.getSourceBufferedImage());
			  form.setResultBufferedImage(blobDetector.detectBlobs());


			  frame.getViewPanel().repaint();
			  System.out.println("end smoothing gaussian");
		  }
	  });

  }

  private void initializeGUI() {

    add(openFileButton, new GBC(0, 0).setInsets(CELL_INSETS).setAnchor(GridBagConstraints.WEST));
	  add(smoothingGaussianButton, new GBC(1, 0).setInsets(CELL_INSETS).setAnchor(GridBagConstraints.WEST));

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
