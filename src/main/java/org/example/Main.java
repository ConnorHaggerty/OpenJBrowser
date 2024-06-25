package org.example;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;




public class Main {
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }

        //Create Frame
        JFrame frame= new JFrame("Browser");

        //Set close operation on frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Make a Browser Pane
        mainPanel mainwindow= new mainPanel(frame);
        frame.setContentPane(mainwindow.getPanel());

        //Determine the size of the frame based off of the contents
        frame.pack();



        //More Properties
        frame.setResizable(true); //enables resize
        frame.setVisible(true); //allows it to show
        frame.setLocationRelativeTo(null); //sends it to center of screen


    }
}