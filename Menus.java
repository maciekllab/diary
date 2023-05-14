package com.example.lab6;

import javax.swing.*;

public class Menus {
    JMenu change;
    JMenuBar menuBar;
    private static Menus instance;
    private Menus(){
       // UIManager.put("MenuBar.background",new Color(38, 70, 83));
        change = new JMenu("Student's interface");

        menuBar= new JMenuBar();
        menuBar.add(change);
    }
    public static Menus getInstance(){
        if(instance==null)instance=new Menus();
        return instance;
    }
}
