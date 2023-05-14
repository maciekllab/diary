package com.example.lab6;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class Frames {
    JFrame frame;
    private static Frames instance;

    private Frames() {
        UIManager.put("OptionPane.background",new ColorUIResource(30, 30, 30));
        UIManager.put("Panel.background",new Color(30, 30, 30));
        UIManager.put("OptionPane.messageForeground", Color.lightGray);
        frame = new JFrame();
        frame.setTitle("Teacher's diary");
        frame.setSize(900,600);
        frame.setMinimumSize(new Dimension(900,600));
        frame.setLayout( new BorderLayout());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static Frames getInstance(){
        if(instance==null)instance=new Frames();
        return instance;
    }

}
