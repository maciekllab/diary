package com.example.lab6;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Labels {
    JLabel studentNameLabel;
    JLabel studentSurnameLabel;
    JLabel studentPointsLabel;
    JLabel studentConditionLabel;
    JLabel studentBirthLabel;
    JLabel studentNumberLabel;
    JLabel addPointsLabel;
    JLabel removePointsLabel;
    JLabel existingStudentLabel;
    JLabel tableLabel;
    JLabel statisticsLabel;
    JLabel removeLabel;
    JLabel groupsLabel;
    JLabel  updateStudentLabel;
    JLabel updateActionLabel;
    JLabel updateListTitle;

    public Labels(){
        tableLabel= new JLabel("No subject selected",SwingConstants.CENTER);
        tableLabel.setPreferredSize(new Dimension(400,20));
        tableLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        tableLabel.setForeground(Color.WHITE);
        tableLabel.setBorder(new EmptyBorder(5,0,5,0));

        statisticsLabel= new JLabel("",SwingConstants.CENTER);
        statisticsLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        statisticsLabel.setForeground(Color.WHITE);
        statisticsLabel.setBorder(new EmptyBorder(5,0,5,0));

        studentNameLabel= new JLabel("Name:");
        studentNameLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        studentNameLabel.setForeground(Color.WHITE);

        studentSurnameLabel= new JLabel("Surname:");
        studentSurnameLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        studentSurnameLabel.setForeground(Color.WHITE);

        studentNumberLabel= new JLabel("Number:");
        studentNumberLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        studentNumberLabel.setForeground(Color.WHITE);

        studentConditionLabel= new JLabel("Condition:");
        studentConditionLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        studentConditionLabel.setForeground(Color.WHITE);

        studentBirthLabel= new JLabel("Year of birth:");
        studentBirthLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        studentBirthLabel.setForeground(Color.WHITE);

        studentPointsLabel= new JLabel("Points:");
        studentPointsLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        studentPointsLabel.setForeground(Color.WHITE);

        addPointsLabel = new JLabel("Enter points to bo added");
        addPointsLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        addPointsLabel.setForeground(Color.WHITE);

        removePointsLabel = new JLabel("Choose points to bo removed");
        removePointsLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        removePointsLabel.setForeground(Color.WHITE);

        existingStudentLabel = new JLabel("Choose existing student");
        existingStudentLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        existingStudentLabel.setForeground(Color.WHITE);

        removeLabel=new JLabel();
        removeLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        removeLabel.setForeground(Color.WHITE);

        groupsLabel = new JLabel("List of subjects:");
        groupsLabel.setBorder(new EmptyBorder(20,0,0,0));
        groupsLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        groupsLabel.setForeground(Color.WHITE);

        updateActionLabel=new JLabel();
        updateActionLabel.setFont(new Font("Helvetica",Font.BOLD,12));
        updateActionLabel.setForeground(Color.LIGHT_GRAY);

        updateStudentLabel=new JLabel();
        updateStudentLabel.setFont(new Font("Helvetica",Font.BOLD,14));
        updateStudentLabel.setForeground(Color.WHITE);

        updateListTitle =new JLabel("List of changes");
        updateListTitle.setFont(new Font("Helvetica",Font.BOLD,12));
        updateListTitle.setBackground(new Color(30, 30, 30));
        updateListTitle.setForeground(Color.WHITE);
    }
}
