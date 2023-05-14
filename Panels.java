package com.example.lab6;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class Panels {
    private static Panels instance;
    JFXPanel windowPanelFX;
    JPanel windowPanelSwing;
    JPanel addGroupPanel;
    JPanel menuPanel;
    JPanel menuButtonsPanel;
    JPanel studentsPanel;
    JPanel blankPanel;
    JPanel removeElementsPanel;
    JPanel groupsPanel;
    JPanel studentsListPanel;
    JScrollPane studentsListScrollPanel;
    JPanel infoPanel;
    JPanel studentInfoPanel;
    JPanel addPointsPanel;
    JPanel removePointsPanel;
    JPanel existingStudentPanel;
    JPanel updateChangesPanel;
    JPanel updateChangesListPanel;
    JPanel updateChangesInfoPanel;
    JPanel groupStatisticsPanel;
    SpringLayout studentInfoPanelLayout;
    CardLayout infoPanelLayout;
    private Panels(){
        studentsListScrollPanel = new JScrollPane();
        studentsListScrollPanel.setPreferredSize(new Dimension(400,400));
        studentsListScrollPanel.setBackground(new Color(38, 38, 38));
        studentsListScrollPanel.getVerticalScrollBar().setBackground(new Color(38, 38, 38));
        studentsListScrollPanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.lightGray;
            }
        });

        groupStatisticsPanel = new JPanel();
        groupStatisticsPanel.setLayout(new BorderLayout());
        groupStatisticsPanel.setBackground(new Color(38, 38, 38));

        studentsListPanel = new JPanel();
        studentsListPanel.setLayout(new BorderLayout());
        studentsListPanel.setBackground(new Color(38, 38, 38));
        studentsListPanel.setForeground(new Color(38, 38, 38));
        studentsListPanel.setPreferredSize(new Dimension(560,400));

        blankPanel = new JPanel();
        blankPanel.setBackground(new Color(48, 49, 54));
        blankPanel.setPreferredSize(new Dimension(560,200));

        studentInfoPanel= new JPanel();
        studentInfoPanel.setBackground(new Color(48, 49, 54));
        studentInfoPanel.setPreferredSize(new Dimension(560,200));
        studentInfoPanel.setBorder(new EmptyBorder(5,0,0,0));
        studentInfoPanelLayout  = new SpringLayout();
        studentInfoPanel.setLayout(studentInfoPanelLayout);

        addPointsPanel = new JPanel();
        addPointsPanel.setBackground(new Color(48, 49, 54));
        addPointsPanel.setPreferredSize(new Dimension(560,200));

        updateChangesPanel = new JPanel();
        updateChangesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        updateChangesPanel.setBackground(new Color(48, 49, 54));
        updateChangesPanel.setPreferredSize(new Dimension(560,200));

        updateChangesListPanel = new JPanel();
        updateChangesListPanel.setBackground(new Color(48, 49, 54));
        updateChangesListPanel.setPreferredSize(new Dimension(250,190));

        updateChangesInfoPanel = new JPanel();
        updateChangesInfoPanel.setLayout(new BoxLayout(updateChangesInfoPanel,BoxLayout.Y_AXIS));
        updateChangesInfoPanel.setBackground(new Color(48, 49, 54));
        updateChangesInfoPanel.setPreferredSize(new Dimension(290,190));

        removePointsPanel = new JPanel();
        removePointsPanel.setBackground(new Color(48, 49, 54));
        removePointsPanel.setPreferredSize(new Dimension(560,200));

        existingStudentPanel = new JPanel();
        existingStudentPanel.setBackground(new Color(48, 49, 54));
        existingStudentPanel.setPreferredSize(new Dimension(560,200));

        removeElementsPanel = new JPanel();
        removeElementsPanel.setBackground(new Color(48, 49, 54));
        removeElementsPanel.setPreferredSize(new Dimension(560,200));

        infoPanel = new JPanel();
        infoPanel.setBackground(new Color(48, 49, 54));
        infoPanelLayout = new CardLayout();
        infoPanel.setLayout(infoPanelLayout);
        infoPanel.setPreferredSize(new Dimension(560,200));
        infoPanel.setVisible(true);

        studentsPanel = new JPanel();
        studentsPanel.setBackground(Color.darkGray);
        studentsPanel.setLayout( new BoxLayout(studentsPanel,BoxLayout.Y_AXIS));
        studentsPanel.setPreferredSize(new Dimension(560,360));

        GridLayout menuLayout = new GridLayout(3,2,10,10);
        menuButtonsPanel = new JPanel();
        menuButtonsPanel.setBorder(new EmptyBorder(20,30,20,30));
        menuButtonsPanel.setBackground(new Color(30, 30, 30));
        menuButtonsPanel.setPreferredSize(new Dimension(190,210));
        menuButtonsPanel.setLayout(menuLayout);

        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(30, 30, 30));
        menuPanel.setPreferredSize(new Dimension(190,600));
        menuPanel.add(menuButtonsPanel);

        addGroupPanel = new JPanel();

        groupsPanel = new JPanel();
        groupsPanel.setBackground(new Color(30, 30, 30));
        groupsPanel.setPreferredSize(new Dimension(150,600));

        windowPanelSwing = new JPanel();
        windowPanelSwing.setLayout(new BorderLayout());
        windowPanelSwing.setPreferredSize(new Dimension(900,600));

        windowPanelFX = new JFXPanel();
    }
    public static Panels getInstance(){
        if(instance==null)instance= new Panels();
        return instance;
    }
}
