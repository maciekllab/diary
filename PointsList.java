package com.example.lab6;

import java.io.Serializable;
import java.util.ArrayList;

public class PointsList implements Serializable {
    int average;
    @Exportable
    ArrayList<Integer> points;

    public PointsList(ArrayList<Integer> list){
        this.points = list;
        updateAverage();
    }
    public void updateAverage(){
        if (this.points.size()==0){
            this.average=0;
            return;
        }
        int sum=0;
        for (int a : this.points){
            sum+=a;
        }
        this.average=sum/this.points.size();
    }
}
