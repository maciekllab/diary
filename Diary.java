package com.example.lab6;

import javafx.application.Platform;

import javafx.scene.Scene;


import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Diary extends JFrame {
    JProgressBar groupsProgressBar;
    Data data;
    Buttons buttons;
    Labels labels;
    Panels panels;
    TextFields textFields;
    Tables tables;
    Frames frames;
    Menus menus;

    public Diary(){
        data = Data.getInstance();
        tables = new Tables();
        labels = new Labels();
        buttons = new Buttons();
        panels = Panels.getInstance();
        menus = Menus.getInstance();
        textFields= new TextFields();
        showGroupsTable();

        buttons.addExistingStudentButton.addActionListener(e->{
            if(!isAnyGroupSelected())return;
            if(getSelectedList(data.groupsSelectedRow).size()==getSelectedClass(data.groupsSelectedRow).maxStudents){
                JOptionPane.showMessageDialog(frames.frame,"Group is full");
                return;
            }
            data.studentsSelectedRow=-1;
            panels.infoPanelLayout.show(panels.infoPanel,"existingStudent");
            data.container.allStudents.sortByName();
            textFields.existingStudentArea.removeAllItems();
            for (Student s: data.container.allStudents.studentsList) {
                textFields.existingStudentArea.addItem("" + s.name+"  "+s.surname+"  "+ s.diaryNumber);
            }

        });
        buttons.addNewStudentButton.addActionListener(e->{
            if(!isAnyGroupSelected())return;
            if(getSelectedList(data.groupsSelectedRow).size()==getSelectedClass(data.groupsSelectedRow).maxStudents){
                JOptionPane.showMessageDialog(frames.frame,"Group is full");
                return;
            }
            data.studentsSelectedRow=-1;
            panels.infoPanelLayout.show(panels.infoPanel,"studentInfo");
            setAllFieldsEdit(true);
            clearInfoPanel();
            textFields.studentPointsArea.setValue(0);
            textFields.studentPointsArea.setEditable(false);
            textFields.studentConditionArea.setEnabled(false);
            textFields.studentConditionArea.setSelectedItem(StudentCondition.NEW);
            buttons.editStudentButton.setVisible(false);
            buttons.addPointsStudentButton.setVisible(false);
            buttons.changeConditionStudentButton.setVisible(false);
            buttons.removePointsStudentButton.setVisible(false);
        });
        buttons.removeStudentButton.addActionListener(e->{
            if(!isAnyGroupSelected())return;
            labels.removeLabel.setText(tables.countSelectedElementsInTable(tables.studentTableModel) + " students will be removed from "+tables.groupsTableModel.getValueAt(data.groupsSelectedRow,1));
            panels.infoPanelLayout.show(panels.infoPanel,"remove");
        });
        buttons.sortNameStudentButton.addActionListener(e ->
        {
            if(!isAnyGroupSelected())return;
            Object group = tables.groupsTable.getValueAt(data.groupsSelectedRow,1);
            Class tempClass = data.container.groups.get(group.toString());
            tempClass.sortByName();
            showStudentsTable(data.groupsSelectedRow);
            panels.infoPanelLayout.show(panels.infoPanel,"blank");
        });
        buttons.sortPointsStudentButton.addActionListener(e ->
        {
            if(!isAnyGroupSelected())return;
            Object group = tables.groupsTable.getValueAt(data.groupsSelectedRow,1);
            Class tempClass = data.container.groups.get(group.toString());
            tempClass.sortByPoints();
            showStudentsTable(data.groupsSelectedRow);
            panels.infoPanelLayout.show(panels.infoPanel,"blank");
        });
        buttons.addPointsStudentButton.addActionListener(e -> {
            textFields.pointsOperationArea.setValue(0);
            textFields.pointsOperationArea.setEditable(true);
            buttons.confirmAddPointsButton.setVisible(true);
            panels.infoPanelLayout.show(panels.infoPanel,"addPoints");
        });
        buttons.removePointsStudentButton.addActionListener(e -> {
            textFields.removePointsArea.removeAllItems();
            for (Integer a :getSelectedList(data.groupsSelectedRow).get(data.studentsSelectedRow).pointsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).points) {
                textFields.removePointsArea.addItem(a);
            }
            panels.infoPanelLayout.show(panels.infoPanel,"removePoints");
        });
        buttons.editStudentButton.addActionListener(e ->
        {
            setAllFieldsEdit(true);
            textFields.studentPointsArea.setEditable(false);
        });
        buttons.changeConditionStudentButton.addActionListener(e ->
        {
            setAllFieldsEdit(false);
            textFields.studentConditionArea.setEnabled(true);
            buttons.confirmEditButton.setVisible(true);
        });
        buttons.searchStudentButton.addActionListener(e->{
            if(!isAnyGroupSelected())return;
            searchStudentPattern(textFields.searchStudentArea.getText());
            textFields.searchStudentArea.setText("");
            panels.infoPanelLayout.show(panels.infoPanel,"blank");
        });
        buttons.confirmEditButton.addActionListener(e->{
            if(data.studentsSelectedRow==-1){
                if(!isStudentAddInputCorrect())return;
                if(!addNewStudentToClass()){
                    JOptionPane.showMessageDialog(frames.frame,"Change student's diary number");
                    return;
                }
                panels.infoPanelLayout.show(panels.infoPanel,"blank");
                showProgressbar();
            }
            else{
                if(!isStudentAddInputCorrect())return;
                editStudent();
                panels.infoPanelLayout.show(panels.infoPanel,"blank");
            }
        });
        buttons.confirmExistingStudentButton.addActionListener(e->{
            if(!addExistingStudentToClass()){
                JOptionPane.showMessageDialog(frames.frame,"Student already exists in this group");
                return;
            }
            panels.infoPanelLayout.show(panels.infoPanel,"blank");
            showProgressbar();
        });
        buttons.confirmAddPointsButton.addActionListener(e->{
            getSelectedClass(data.groupsSelectedRow).addPoints(getSelectedList(data.groupsSelectedRow).get(data.studentsSelectedRow),(int)textFields.pointsOperationArea.getValue());
            getSelectedList(data.groupsSelectedRow).get(data.studentsSelectedRow).averagePoints=getSelectedList(data.groupsSelectedRow).get(data.studentsSelectedRow).calculateAverage();
            panels.infoPanelLayout.show(panels.infoPanel,"studentInfo");
            clearInfoPanel();
            showStudentInfoPanel(false);
            showStudentsTable(data.groupsSelectedRow);
        });
        buttons.confirmRemovePointsButton.addActionListener(e->{
            if(textFields.removePointsArea.getSelectedIndex()!=-1)getSelectedClass(data.groupsSelectedRow).removePoints(getSelectedList(data.groupsSelectedRow).get(data.studentsSelectedRow),(int)textFields.removePointsArea.getSelectedItem());
            getSelectedList(data.groupsSelectedRow).get(data.studentsSelectedRow).averagePoints=getSelectedList(data.groupsSelectedRow).get(data.studentsSelectedRow).calculateAverage();
            panels.infoPanelLayout.show(panels.infoPanel,"studentInfo");
            clearInfoPanel();
            showStudentInfoPanel(false);
            showStudentsTable(data.groupsSelectedRow);
        });
        buttons.confirmRemoveButton.addActionListener(e->{
            removeSelectedStudents();
            panels.infoPanelLayout.show(panels.infoPanel,"blank");
            showProgressbar();
        });
        buttons.updateConditionButton.addActionListener(e->{
            if(!isAnyGroupSelected())return;
            panels.infoPanelLayout.show(panels.infoPanel,"update");
            panels.updateChangesInfoPanel.setVisible(false);
            data.updateSelectedRow=-1;
            showUpdateList();
        });
        buttons.confirmUpdateButton.addActionListener(e->{
            panels.updateChangesInfoPanel.setVisible(false);
            List<Student>list = getUpdateList();
            Student s = list.get(data.updateSelectedRow);
            list.remove(s);
            if(s.conditionsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).equals(StudentCondition.WAITING)){
                s.conditionsMap.replace(getSelectedClass(data.groupsSelectedRow).groupName,StudentCondition.NEW);
            }
            else if(s.conditionsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).equals(StudentCondition.RESIGNED)){
                s.conditionsMap.remove(getSelectedClass(data.groupsSelectedRow).groupName);
                s.pointsMap.remove(getSelectedClass(data.groupsSelectedRow).groupName);
                s.averagePoints=s.calculateAverage();
                getSelectedClass(data.groupsSelectedRow).studentsList.remove(s);
                showProgressbar();
                showStudentsTable(data.groupsSelectedRow);
            }
            showUpdateList();
        });
        buttons.rejectUpdateButton.addActionListener(e->{
            panels.updateChangesInfoPanel.setVisible(false);
            List<Student>list = getUpdateList();
            Student s = list.get(data.updateSelectedRow);
            list.remove(s);
            if(s.conditionsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).equals(StudentCondition.WAITING)){
                s.conditionsMap.remove(getSelectedClass(data.groupsSelectedRow).groupName);
                s.pointsMap.remove(getSelectedClass(data.groupsSelectedRow).groupName);
                s.averagePoints=s.calculateAverage();
                getSelectedClass(data.groupsSelectedRow).studentsList.remove(s);
                showProgressbar();
                showStudentsTable(data.groupsSelectedRow);
            }
            else if(s.conditionsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).equals(StudentCondition.RESIGNED)){
                s.conditionsMap.replace(getSelectedClass(data.groupsSelectedRow).groupName,StudentCondition.PRESENT);
            }
            showUpdateList();
        });
        buttons.removeGroupsButton.addActionListener(e->{
            int count = tables.countSelectedElementsInTable(tables.groupsTableModel);
            if(count==0){
                JOptionPane.showMessageDialog(frames.frame,"Please select at least one group","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int result = JOptionPane.showConfirmDialog(frames.frame, count+" groups will be removed","Confirm",JOptionPane.OK_CANCEL_OPTION);
            if(result!= JOptionPane.OK_OPTION)return;
            removeSelectedGroups();
            tables.studentTableModel.setRowCount(0);
            showGroupsTable();
        });
        buttons.saveCSVButton.addActionListener(e->{
            try {
                Data.getInstance().writeToCSVFiles();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frames.frame,"File is being edited","Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttons.addGroupButton.addActionListener(e->{
            int result = JOptionPane.showConfirmDialog(frames.frame, panels.addGroupPanel, "Please enter values", JOptionPane.OK_CANCEL_OPTION);
            if(result!= JOptionPane.OK_OPTION)return;
            if( textFields.groupNameArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(frames.frame,"Please, choose group name","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if( data.container.groups.containsKey(textFields.groupNameArea.getText())){
                JOptionPane.showMessageDialog(frames.frame,"This group already exists","Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            data.container.addClass(textFields.groupNameArea.getText(),(int)textFields.groupCapacityArea.getValue());
            showGroupsTable();
        });
        tables.studentsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    JTable target = (JTable)me.getSource();
                    data.studentsSelectedRow= target.getSelectedRow();
                    panels.infoPanelLayout.show(panels.infoPanel,"studentInfo");
                    showStudentInfoPanel(false);
                    buttons.editStudentButton.setVisible((true));
                    buttons.addPointsStudentButton.setVisible(true);
                    buttons.removePointsStudentButton.setVisible(true);
                    buttons.changeConditionStudentButton.setVisible((true));
                }
            }
        });

        groupsProgressBar= new JProgressBar();
        groupsProgressBar.setStringPainted(true);
        groupsProgressBar.setForeground(new Color( 29, 61, 255));
        groupsProgressBar.setBackground(Color.lightGray);
        groupsProgressBar.setVisible(false);

        panels.groupStatisticsPanel.add(labels.statisticsLabel,BorderLayout.PAGE_START);
        panels.groupStatisticsPanel.add(groupsProgressBar,BorderLayout.PAGE_END);

        panels.studentsListPanel.add(labels.tableLabel,BorderLayout.NORTH);
        panels.studentsListPanel.add(panels.studentsListScrollPanel,BorderLayout.CENTER);
        panels.studentsListPanel.add(panels.groupStatisticsPanel,BorderLayout.SOUTH);
        panels.studentsListScrollPanel.setViewportView(tables.studentsTable);

        panels.studentInfoPanel.add(labels.studentNameLabel);
        panels.studentInfoPanel.add(textFields.studentNameArea);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,labels.studentNameLabel,5,SpringLayout.NORTH,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,labels.studentNameLabel,10,SpringLayout.WEST,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,textFields.studentNameArea,100,SpringLayout.WEST,labels.studentNameLabel);
        panels. studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,textFields.studentNameArea,0,SpringLayout.NORTH,labels.studentNameLabel);
        panels.studentInfoPanel.add(labels.studentSurnameLabel);
        panels.studentInfoPanel.add(textFields.studentSurnameArea);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,labels.studentSurnameLabel,30,SpringLayout.NORTH,labels.studentNameLabel);
        panels. studentInfoPanelLayout.putConstraint(SpringLayout.WEST,labels.studentSurnameLabel,10,SpringLayout.WEST,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,textFields.studentSurnameArea,100,SpringLayout.WEST,labels.studentSurnameLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,textFields.studentSurnameArea,30,SpringLayout.NORTH,textFields.studentNameArea);
        panels.studentInfoPanel.add(labels.studentConditionLabel);
        panels.studentInfoPanel.add(textFields.studentConditionArea);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,labels.studentConditionLabel,30,SpringLayout.NORTH,labels.studentSurnameLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,labels.studentConditionLabel,10,SpringLayout.WEST,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,textFields.studentConditionArea,100,SpringLayout.WEST,labels.studentConditionLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,textFields.studentConditionArea,30,SpringLayout.NORTH,textFields.studentSurnameArea);
        panels.studentInfoPanel.add(labels.studentNumberLabel);
        panels.studentInfoPanel.add(textFields.studentNumberArea);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,labels.studentNumberLabel,30,SpringLayout.NORTH,labels.studentConditionLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,labels.studentNumberLabel,10,SpringLayout.WEST,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,textFields.studentNumberArea,100,SpringLayout.WEST,labels.studentNumberLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,textFields.studentNumberArea,30,SpringLayout.NORTH,textFields.studentConditionArea);
        panels.studentInfoPanel.add(labels.studentPointsLabel);
        panels.studentInfoPanel.add(textFields.studentPointsArea);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,labels.studentPointsLabel,30,SpringLayout.NORTH,labels.studentNumberLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,labels.studentPointsLabel,10,SpringLayout.WEST,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,textFields.studentPointsArea,100,SpringLayout.WEST,labels.studentPointsLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,textFields.studentPointsArea,30,SpringLayout.NORTH,textFields.studentNumberArea);
        panels.studentInfoPanel.add(labels.studentBirthLabel);
        panels.studentInfoPanel.add(textFields.studentBirthArea);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,labels.studentBirthLabel,30,SpringLayout.NORTH,labels.studentPointsLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,labels.studentBirthLabel,10,SpringLayout.WEST,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,textFields.studentBirthArea,100,SpringLayout.WEST,labels.studentBirthLabel);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,textFields.studentBirthArea,30,SpringLayout.NORTH,textFields.studentPointsArea);
        panels. studentInfoPanel.add(buttons.editStudentButton);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,buttons.editStudentButton,5,SpringLayout.NORTH,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,buttons.editStudentButton,435,SpringLayout.WEST,this);
        panels.studentInfoPanel.add(buttons.addPointsStudentButton);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,buttons.addPointsStudentButton,65,SpringLayout.NORTH,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,buttons.addPointsStudentButton,495,SpringLayout.WEST,this);
        panels.studentInfoPanel.add(buttons.removePointsStudentButton);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,buttons.removePointsStudentButton,65,SpringLayout.NORTH,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,buttons.removePointsStudentButton,435,SpringLayout.WEST,this);
        panels.studentInfoPanel.add(buttons.changeConditionStudentButton);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,buttons.changeConditionStudentButton,5,SpringLayout.NORTH,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,buttons.changeConditionStudentButton,495,SpringLayout.WEST,this);
        panels.studentInfoPanel.add(buttons.confirmEditButton);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.NORTH,buttons.confirmEditButton,145,SpringLayout.NORTH,this);
        panels.studentInfoPanelLayout.putConstraint(SpringLayout.WEST,buttons.confirmEditButton,430,SpringLayout.WEST,this);

        panels.removePointsPanel.add(labels.removePointsLabel);
        panels.removePointsPanel.add(textFields.removePointsArea);
        panels.removePointsPanel.add(buttons.confirmRemovePointsButton);

        panels.addPointsPanel.add(labels.addPointsLabel);
        panels.addPointsPanel.add(textFields.pointsOperationArea);
        panels.addPointsPanel.add(buttons.confirmAddPointsButton);

        panels.existingStudentPanel.add(labels.existingStudentLabel);
        panels.existingStudentPanel.add(textFields.existingStudentArea);
        panels.existingStudentPanel.add(buttons.confirmExistingStudentButton);

        panels.removeElementsPanel.add(labels.removeLabel);
        panels.removeElementsPanel.add(buttons.confirmRemoveButton);

        panels.updateChangesListPanel.add(labels.updateListTitle);
        panels.updateChangesListPanel.add(textFields.updateList);
        textFields.updateList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList source = (JList) e.getSource();
                if(source.getModel().getSize()==0)return;
                if(e.getClickCount()==2){
                    panels.updateChangesInfoPanel.setVisible(true);
                    data.updateSelectedRow = source.getSelectedIndex();
                    source.setSelectedIndex(data.updateSelectedRow);
                    List<Student>list = getUpdateList();
                    Student s = list.get(data.updateSelectedRow);
                    showUpdateList();
                    labels.updateStudentLabel.setText("" + s.name+ "  "+ s.surname+"  "+s.diaryNumber);
                    if(s.conditionsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).equals(StudentCondition.RESIGNED))labels.updateActionLabel.setText("Requests to be removed from " + getSelectedClass(data.groupsSelectedRow).groupName);
                    if(s.conditionsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).equals(StudentCondition.WAITING))labels.updateActionLabel.setText("Requests to join "+getSelectedClass(data.groupsSelectedRow).groupName);
                }
            }
        });

        panels.updateChangesInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panels.updateChangesInfoPanel.add(labels.updateStudentLabel);
        panels.updateChangesInfoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        panels.updateChangesInfoPanel.add(labels.updateActionLabel);
        panels.updateChangesInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panels.updateChangesInfoPanel.add(buttons.confirmUpdateButton);
        panels.updateChangesInfoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        panels.updateChangesInfoPanel.add(buttons.rejectUpdateButton);

        panels.updateChangesPanel.add(panels.updateChangesListPanel);
        panels.updateChangesPanel.add(panels.updateChangesInfoPanel);

        panels.infoPanel.add(panels.blankPanel,"blank");
        panels.infoPanel.add(panels.studentInfoPanel,"studentInfo");
        panels.infoPanel.add(panels.addPointsPanel,"addPoints");
        panels.infoPanel.add(panels.removePointsPanel,"removePoints");
        panels.infoPanel.add(panels.existingStudentPanel,"existingStudent");
        panels.infoPanel.add(panels.removeElementsPanel,"remove");
        panels.infoPanel.add(panels.updateChangesPanel,"update");

        panels.infoPanelLayout.show(panels.infoPanel,"blank");

        panels.studentsPanel.add(panels.studentsListPanel);
        panels.studentsPanel.add(panels.infoPanel);


        panels.menuButtonsPanel.add(buttons.updateConditionButton);
        panels.menuButtonsPanel.add(buttons.removeStudentButton);
        panels.menuButtonsPanel.add(buttons.addNewStudentButton);
        panels.menuButtonsPanel.add(buttons.addExistingStudentButton);
        panels.menuButtonsPanel.add(buttons.sortPointsStudentButton);
        panels.menuButtonsPanel.add(buttons.sortNameStudentButton);

        panels.menuPanel.add(buttons.searchStudentButton);
        panels.menuPanel.add(textFields.searchStudentArea);

        tables.groupsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                JTable target = (JTable)me.getSource();
                if (me.getClickCount() == 2) {
                    data.groupsSelectedRow = target.getSelectedRow();
                    data.studentsSelectedRow=-1;
                    panels.infoPanelLayout.show( panels.infoPanel,"blank");
                    showStudentsTable(data.groupsSelectedRow);
                    labels.tableLabel.setText("Selected subject : "+getSelectedClass(data.groupsSelectedRow).groupName);
                }
            }
        });

        panels.addGroupPanel.add(new JLabel("Group name:"));
        panels.addGroupPanel.add(textFields.groupNameArea);
        panels.addGroupPanel.add(new JLabel("Max students:"));
        panels.addGroupPanel.add(textFields.groupCapacityArea);

        panels.groupsPanel.add(labels.groupsLabel);
        panels.groupsPanel.add(tables.groupsTable);
        panels.groupsPanel.add(buttons.addGroupButton);
        panels.groupsPanel.add(buttons.removeGroupsButton);
        panels.groupsPanel.add(buttons.saveCSVButton);

        panels.windowPanelSwing.add(panels.studentsPanel,BorderLayout.CENTER);
        panels.windowPanelSwing.add(panels.menuPanel,BorderLayout.WEST);
        panels.windowPanelSwing.add(panels.groupsPanel,BorderLayout.EAST);

        menus.change.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                frames.frame.setVisible(false);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HelloApplication g = new HelloApplication();
                        try {
                            g.start(null);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
            @Override public void menuDeselected(MenuEvent e) {}
            @Override public void menuCanceled(MenuEvent e) {}
        });

        frames= Frames.getInstance();
        frames.frame.setJMenuBar(menus.menuBar);
        frames.frame.add(panels.windowPanelSwing);
        frames.frame.pack();
        frames.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Do you want to save .csv .ser files?", "Exit Program Message Box",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    try {
                        data.writeToCSVFiles();
                        data.serializeData();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frames.frame,"File is being edited","Error", JOptionPane.ERROR_MESSAGE);
                        windowClosing(e);
                    }
                }
            }
        });
    }
    public void showStudentInfoPanel(boolean editable){
        List <Student>tempList =getSelectedList(data.groupsSelectedRow);
        textFields.studentNameArea.setText(tempList.get(data.studentsSelectedRow).name);
        textFields.studentSurnameArea.setText(tempList.get(data.studentsSelectedRow).surname);
        textFields.studentNumberArea.setValue( tempList.get(data.studentsSelectedRow).diaryNumber);
        StringBuilder points = new StringBuilder();
        for( int a : tempList.get(data.studentsSelectedRow).pointsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).points){
            points.append(a).append(", ");
        }
        textFields.studentPointsArea.setText(points.toString());
        textFields.studentBirthArea.setValue(tempList.get(data.studentsSelectedRow).birthYear);
        textFields.studentConditionArea.setSelectedItem(tempList.get(data.studentsSelectedRow).conditionsMap.get(getSelectedClass(data.groupsSelectedRow).groupName));
        setAllFieldsEdit(editable);
    }
    public void setAllFieldsEdit(boolean editable){
        textFields.studentNameArea.setEditable(editable);
        textFields.studentSurnameArea.setEditable(editable);
        textFields.studentConditionArea.setEnabled(editable);
        textFields.studentPointsArea.setEditable(editable);
        textFields.studentNumberArea.setEditable(editable);
        textFields.studentBirthArea.setEditable(editable);
        buttons.confirmEditButton.setVisible(editable);
    }
    public void clearInfoPanel(){
        textFields.studentNameArea.setText(null);
        textFields.studentSurnameArea.setText(null);
        textFields.studentNumberArea.setValue(getSelectedList(data.groupsSelectedRow).size()+1);
        textFields.studentPointsArea.setValue(0);
        textFields.studentBirthArea.setText(null);
        textFields.studentConditionArea.setSelectedIndex(-1);
    }
    public boolean isAnyGroupSelected(){
        if(data.groupsSelectedRow==-1){
            JOptionPane.showMessageDialog(frames.frame,"No group selected");
            return false;
        }
        return true;
    }
    public boolean isBirthInputCorrect(){
        if( textFields.studentBirthArea.getValue()==null)return false;
        int input = (int) textFields.studentBirthArea.getValue();
        return (input >= 1900 && input <= 2030);
    }
    public boolean isConditionInputCorrect(){
        return (textFields.studentConditionArea.getSelectedItem() != null);
    }
    public boolean isNameInputCorrect(){
        if( textFields.studentNameArea.getText().equals("") ||  textFields.studentNameArea.getText().charAt(0)==' ')return false;
        String name =  textFields.studentNameArea.getText();
        textFields.studentNameArea.setText(name.substring(0,1).toUpperCase() + name.substring(1));
        return true;
    }
    public boolean isSurnameInputCorrect(){
        if( textFields.studentSurnameArea.getText().equals(""))return false;
        if( textFields.studentSurnameArea.getText().equals("") ||  textFields.studentSurnameArea.getText().charAt(0)==' ')return false;
        String name =  textFields.studentSurnameArea.getText();
        textFields.studentSurnameArea.setText(name.substring(0,1).toUpperCase() + name.substring(1));
        return true;
    }
    public boolean isStudentAddInputCorrect(){
        if(!isNameInputCorrect()){
            JOptionPane.showMessageDialog(frames.frame,"Wrong name input");
            return false;
        }
        if(!isSurnameInputCorrect()){
            JOptionPane.showMessageDialog(frames.frame,"Wrong surnname input");
            return false;
        }
        if(!isConditionInputCorrect()){
            JOptionPane.showMessageDialog(frames.frame,"You have to choose student condition");
            return false;
        }
        if(!isBirthInputCorrect()){
            JOptionPane.showMessageDialog(frames.frame,"Incorrect birth year");
            return false;
        }
        return true;
    }
    public void showProgressbar(){
        Class temp = getSelectedClass(data.groupsSelectedRow);
        labels.statisticsLabel.setText(temp.studentsList.size() +"/"+ temp.maxStudents + "students in group");
        groupsProgressBar.setValue(100*temp.studentsList.size()/temp.maxStudents);
        groupsProgressBar.setVisible(true);
    }
    public boolean addNewStudentToClass(){
       if(getSelectedClass(data.groupsSelectedRow).addNewStudent(new Student(textFields.studentNameArea.getText(),textFields.studentSurnameArea.getText(),(int)textFields.studentBirthArea.getValue(),(int)textFields.studentNumberArea.getValue()),new ArrayList<>(),(StudentCondition)textFields.studentConditionArea.getSelectedItem())) {
           showStudentsTable(data.groupsSelectedRow);
           return true;
       }
       return false;
    }
    public boolean addExistingStudentToClass(){
        if(getSelectedClass(data.groupsSelectedRow).addExistingStudent(ClassContainer.getInstance().allStudents.studentsList.get(textFields.existingStudentArea.getSelectedIndex()))) {
            showStudentsTable(data.groupsSelectedRow);
            return true;
        }
        return false;
    }
    public void editStudent(){
        List<Student> currentList = getSelectedList(data.groupsSelectedRow);
        Student currentStudent = currentList.get(data.studentsSelectedRow);
        currentStudent.name= textFields.studentNameArea.getText();
        currentStudent.surname=textFields.studentSurnameArea.getText();
        currentStudent.conditionsMap.replace(getSelectedClass(data.groupsSelectedRow).groupName,(StudentCondition)textFields.studentConditionArea.getSelectedItem());
        currentStudent.birthYear = (int)textFields.studentBirthArea.getValue();
        currentStudent.diaryNumber=(int)textFields.studentNumberArea.getValue();
        showStudentsTable(data.groupsSelectedRow);
    }
    public void removeSelectedStudents(){
        for (int i=0; i<tables.studentTableModel.getRowCount();i++){
            if( tables.studentTableModel.getValueAt(i,0).equals(Boolean.TRUE)){
                getSelectedClass(data.groupsSelectedRow).studentsList.get(i).conditionsMap.remove(getSelectedClass(data.groupsSelectedRow).groupName);
                getSelectedClass(data.groupsSelectedRow).studentsList.get(i).pointsMap.remove(getSelectedClass(data.groupsSelectedRow).groupName);
                getSelectedClass(data.groupsSelectedRow).studentsList.get(i).averagePoints=getSelectedClass(data.groupsSelectedRow).studentsList.get(i).calculateAverage();
                getSelectedClass(data.groupsSelectedRow).studentsList.remove(i);
                tables.studentTableModel.removeRow(i);
                i--;
            }
        }
    }
    public void removeSelectedGroups(){
        for (int i=0; i<tables.groupsTableModel.getRowCount();i++){
            if(tables.groupsTableModel.getValueAt(i,0).equals(Boolean.TRUE)){
                data.container.removeClass((String)tables.groupsTableModel.getValueAt(i,1));
            }
        }
        if(data.container.groups.isEmpty())data.groupsSelectedRow=-1;
    }
    public void searchStudentPattern(String pattern){
        List<Student>tempList = getSelectedClass(data.groupsSelectedRow).searchPartial(pattern);
        if(tempList.size()==0){
            JOptionPane.showMessageDialog(frames.frame,"No student matches the pattern");
            return;
        }
        tables.studentTableModel.setRowCount(0);
        for (int i=0;i<tempList.size();i++) {
            tables.studentTableModel.addRow(new Object[0]);
            tables.studentTableModel.setValueAt(false,i,0);
            tables.studentTableModel.setValueAt(tempList.get(i).name,i,1);
            tables.studentTableModel.setValueAt(tempList.get(i).surname,i,2);
            tables.studentTableModel.setValueAt(tempList.get(i).pointsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).average,i,3);
        }
    }
    public void showStudentsTable(int row){
        tables.studentTableModel.setRowCount(0);
        List<Student> tempList = getSelectedList(row);
        for (int i=0;i<tempList.size();i++) {
            tables.studentTableModel.addRow(new Object[0]);
            tables.studentTableModel.setValueAt(false,i,0);
            tables.studentTableModel.setValueAt(tempList.get(i).name,i,1);
            tables.studentTableModel.setValueAt(tempList.get(i).surname,i,2);
            tables.studentTableModel.setValueAt(tempList.get(i).pointsMap.get(getSelectedClass(data.groupsSelectedRow).groupName).average,i,3);
        }
        showProgressbar();
    }
    public void showGroupsTable() {
        tables.groupsTableModel.setRowCount(0);
        int iterator1=0;
        for (HashMap.Entry<String, Class> set : data.container.groups.entrySet()) {
            tables.groupsTableModel.addRow(new Object[0]);
            tables.groupsTableModel.setValueAt(false,iterator1,0);
            tables.groupsTableModel.setValueAt(set.getValue().groupName,iterator1,1);
            iterator1++;
        }
        tables.groupsTable.setPreferredSize(new Dimension(100,tables.groupsTableModel.getRowCount()*tables.groupsTable.getRowHeight()));
    }
    public List<Student> getUpdateList(){
        List<Student> temp = new ArrayList<>();
        Class current = getSelectedClass(data.groupsSelectedRow);
        for (Student s : current.studentsList)if(s.conditionsMap.get(current.groupName).equals(StudentCondition.WAITING) || s.conditionsMap.get(current.groupName).equals(StudentCondition.RESIGNED))temp.add(s);
        return temp;
    }
    public void showUpdateList(){
        List<Student> list = getUpdateList();
        textFields.listModel.removeAllElements();
        for (Student s: list) {
            textFields.listModel.addElement("" + s.name+"  "+s.surname+"  "+ s.diaryNumber);
        }
    }
    public List<Student> getSelectedList(int row){
        Object group = tables.groupsTable.getValueAt(row,1);
        Class tempClass = data.container.groups.get(group.toString());
        return tempClass.studentsList;
    }
    public Class getSelectedClass(int row){
        Object group = tables.groupsTable.getValueAt(row,1);
        return data.container.groups.get(group.toString());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Diary();
            }
        });
    }
}

