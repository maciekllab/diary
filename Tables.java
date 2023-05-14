package com.example.lab6;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Tables {
    JTable studentsTable;
    JTable groupsTable;
    DefaultTableModel studentTableModel;
    DefaultTableModel groupsTableModel;


    public Tables(){
        UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
        studentsTable = new JTable();
        studentsTable.setFocusable(false);
        studentTableModel = new DefaultTableModel(){
            @Override
            public java.lang.Class<?> getColumnClass(int column){
                return switch (column) {
                    case 0 -> Boolean.class;
                    case 1 -> String.class;
                    case 2 -> String.class;
                    case 3 -> String.class;
                    default -> String.class;
                };
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        MatteBorder tableborder= new MatteBorder(0,0,0,0,new Color(38, 38, 38));
        studentsTable.setModel(studentTableModel);
        studentsTable.setBorder(tableborder);
        studentsTable.getTableHeader().setBackground(new Color(38, 38, 38));
        studentsTable.getTableHeader().setForeground(Color.WHITE);
        studentsTable.setBackground(new Color(48, 49, 54));
        studentsTable.setForeground(Color.lightGray);
        studentsTable.setSelectionBackground(new Color( 29, 61, 255 ));
        studentsTable.setSelectionForeground(Color.WHITE);
        studentsTable.setShowVerticalLines(false);
        studentTableModel.addColumn("");
        studentTableModel.addColumn("Name");
        studentTableModel.addColumn("Surname");
        studentTableModel.addColumn("Average points %");
        studentsTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
        studentsTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
        studentsTable.getColumnModel().getColumn(3).setCellRenderer(renderer);

        groupsTable = new JTable();
        groupsTable.setBackground(new Color(48, 49, 54));
        groupsTable.setForeground(Color.lightGray);
        groupsTable.setSelectionBackground(new Color( 29, 61, 255 ));
        groupsTable.setSelectionForeground(Color.WHITE);
        groupsTable.setShowVerticalLines(false);
        groupsTableModel = new DefaultTableModel(){
            @Override
            public java.lang.Class<?> getColumnClass(int column){
                return switch (column) {
                    case 0 -> Boolean.class;
                    case 1 -> String.class;
                    default -> String.class;
                };
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
        groupsTable.setModel(groupsTableModel);
        groupsTableModel.addColumn("selected");
        groupsTableModel.addColumn("group");
        groupsTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
    }
    public int countSelectedElementsInTable(DefaultTableModel model){
        int count=0;
        for (int i=0; i<model.getRowCount();i++){
            if(model.getValueAt(i,0).equals(Boolean.TRUE))count++;
        }
        return count;
    }
}
