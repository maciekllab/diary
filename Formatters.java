package com.example.lab6;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class Formatters {
    NumberFormat pointsFormat;
    NumberFormat yearFormat;
    NumberFormatter pointsFormatter;
    NumberFormatter numberformatter;
    NumberFormatter yearFormatter;
    public Formatters(){
        pointsFormat = NumberFormat.getInstance();
        yearFormat = NumberFormat.getInstance();
        pointsFormatter = new NumberFormatter(pointsFormat);
        pointsFormatter.setValueClass(Integer.class);
        pointsFormatter.setMinimum(0);
        pointsFormatter.setAllowsInvalid(false);
        numberformatter = new NumberFormatter(pointsFormat);
        numberformatter.setValueClass(Integer.class);
        numberformatter.setAllowsInvalid(false);
        yearFormatter = new NumberFormatter(yearFormat);
        yearFormatter.setValueClass(Integer.class);
    }
}
