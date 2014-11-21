package com.romario.misoilab3.main;

import com.romario.misoilab3.gui.MyFrame;

import javax.swing.*;

/**
 * Created by romario on 11/21/14.
 */
public class Main {
  public static void main(String[] args) {
    System.out.println("start");

    MyFrame frame = new MyFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    System.out.println("finish");
  }
}
