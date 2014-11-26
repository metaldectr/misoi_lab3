package com.romario.misoilab3.gui;

import com.romario.misoilab3.form.Form;

import javax.swing.*;
import java.awt.*;

/**
 * Created by romario on 11/21/14.
 */
public class MyViewPanel extends JPanel {

	private MyFrame frame;
	private Form form;
	private Graphics2D graphics2D;

	public MyViewPanel(MyFrame frame) {
		this.frame = frame;
		this.form = frame.getForm();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		graphics2D = (Graphics2D) g;

		graphics2D.drawImage(form.getResultBufferedImage(), 0, 0, 600, 400, this);
	}

}
