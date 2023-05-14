package com.example.lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextFields {
    JFormattedTextField pointsOperationArea;
    JFormattedTextField studentNameArea;
    JFormattedTextField  studentSurnameArea;
    JFormattedTextField  studentPointsArea;
    JFormattedTextField  studentBirthArea;
    JFormattedTextField  studentNumberArea;
    JFormattedTextField groupNameArea;
    JFormattedTextField groupCapacityArea;
    JFormattedTextField searchStudentArea;
    JComboBox studentConditionArea;
    JComboBox removePointsArea;
    JComboBox existingStudentArea;
    DefaultListModel listModel;
    JList updateList;

    public TextFields(){
        studentNameArea= new  JFormattedTextField ();
        studentNameArea.setBackground(new Color(30, 30, 30));
        studentNameArea.setForeground(Color.lightGray);
        studentNameArea.setPreferredSize(new Dimension(200,20));

        studentSurnameArea= new  JFormattedTextField ();
        studentSurnameArea.setBackground(new Color(30, 30, 30));
        studentSurnameArea.setForeground(Color.lightGray);
        studentSurnameArea.setPreferredSize(new Dimension(200,20));


        Formatters formatters = new Formatters();
        studentNumberArea= new JFormattedTextField (formatters.pointsFormatter);
        studentNumberArea.setBackground(new Color(30, 30, 30));
        studentNumberArea.setForeground(Color.lightGray);
        studentNumberArea.setPreferredSize(new Dimension(200,20));

        studentPointsArea= new JFormattedTextField ();
        studentPointsArea.setBackground(new Color(30, 30, 30));
        studentPointsArea.setForeground(Color.lightGray);
        studentPointsArea.setPreferredSize(new Dimension(200,20));

        studentConditionArea= new JComboBox ();
        studentConditionArea.setBackground(new Color(30, 30, 30));
        studentConditionArea.setForeground(Color.gray);
        studentConditionArea.setEditable(false);
        studentConditionArea.addItem(StudentCondition.PRESENT);
        studentConditionArea.addItem(StudentCondition.ABSENT);
        studentConditionArea.addItem(StudentCondition.SICK);
        studentConditionArea.addItem(StudentCondition.CATCHINGUP);
        studentConditionArea.addItem(StudentCondition.NEW);
        studentConditionArea.addItem(StudentCondition.RESIGNED);
        studentConditionArea.addItem(StudentCondition.WAITING);
        studentConditionArea.addItem(StudentCondition.INACTIVE);
        studentConditionArea.setPreferredSize(new Dimension(200,20));

        removePointsArea= new JComboBox ();
        removePointsArea.setBackground(new Color(30, 30, 30));
        removePointsArea.setForeground(Color.gray);
        removePointsArea.setPreferredSize(new Dimension(100,20));

        existingStudentArea= new JComboBox ();
        existingStudentArea.setBackground(new Color(30, 30, 30));
        existingStudentArea.setForeground(Color.gray);
        existingStudentArea.setPreferredSize(new Dimension(400,20));

        studentBirthArea= new JFormattedTextField (formatters.yearFormatter);
        studentBirthArea.setBackground(new Color(30, 30, 30));
        studentBirthArea.setForeground(Color.lightGray);
        studentBirthArea.setPreferredSize(new Dimension(200,20));

        pointsOperationArea = new JFormattedTextField(formatters.pointsFormatter);
        pointsOperationArea.setPreferredSize(new Dimension(30,30));

        searchStudentArea= new  JFormattedTextField ();
        searchStudentArea.setBackground(new Color(48, 49, 54));
        searchStudentArea.setForeground(Color.WHITE);
        searchStudentArea.setPreferredSize(new Dimension(125,20));

        groupNameArea = new JFormattedTextField();
        groupNameArea.setPreferredSize(new Dimension(50,20));
        groupCapacityArea = new JFormattedTextField(formatters.pointsFormatter);
        groupCapacityArea.setValue(30);
        groupCapacityArea.setPreferredSize(new Dimension(50,20));

        listModel= new DefaultListModel<>();
        updateList = new JList(listModel);
        updateList.setPreferredSize(new Dimension(250,190));
        updateList.setBackground(new Color(38, 38, 38));
        updateList.setForeground(Color.WHITE);
        updateList.setSelectionBackground(new Color(38, 38, 38));
        updateList.setSelectionForeground(Color.WHITE);

        studentNameArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if( e.getKeyChar()=='\b'||e.getKeyChar()==32 || e.getKeyChar()>='a' && e.getKeyChar()<='z' ||  e.getKeyChar()>='A' && e.getKeyChar()<='Z')studentNameArea.setEditable(true);
                else studentNameArea.setEditable(false);
            }
        });
        studentSurnameArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if( e.getKeyChar()=='\b'||e.getKeyChar()==32 || e.getKeyChar()>='a' && e.getKeyChar()<='z' ||  e.getKeyChar()>='A' && e.getKeyChar()<='Z')studentSurnameArea.setEditable(true);
                else studentSurnameArea.setEditable(false);
            }
        });
        studentBirthArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if( e.getKeyChar()=='\b' || e.getKeyChar()>='0' && e.getKeyChar()<='9')studentBirthArea.setEditable(true);
                else studentBirthArea.setEditable(false);
            }
        });
    }
}
