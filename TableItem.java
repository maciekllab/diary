package com.example.lab6;

import javafx.scene.Node;

public class TableItem extends Node {
    Class ItemClass;
    @Exportable
    String name;
    @Exportable
    int points;
    @Exportable
    StudentCondition condition;
    @Exportable
    String capacity;

    public TableItem(Class itemClass, String itemName, int itemPoints, StudentCondition itemCondition) {
        this.ItemClass = itemClass;
        this.name = itemName;
        this.points = itemPoints;
        this.condition = itemCondition;
        this.capacity = ""+itemClass.studentsList.size()+"/"+itemClass.maxStudents;
    }

    public Class getItemClass() {
        return ItemClass;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public StudentCondition getCondition() {
        return condition;
    }

    public String getCapacity() {
        return capacity;
    }
}
