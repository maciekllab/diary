package com.example.lab6;


import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class Buttons {
    JButton addExistingStudentButton;
    JButton addNewStudentButton;
    JButton removeStudentButton;
    JButton sortNameStudentButton;
    JButton sortPointsStudentButton;
    JButton editStudentButton;
    JButton addPointsStudentButton;
    JButton removePointsStudentButton;
    JButton confirmAddPointsButton;
    JButton confirmExistingStudentButton;
    JButton confirmRemovePointsButton;
    JButton searchStudentButton;
    JButton confirmEditButton;
    JButton changeConditionStudentButton;
    JButton confirmRemoveButton;
    JButton removeGroupsButton;
    JButton saveCSVButton;
    JButton addGroupButton;
    JButton updateConditionButton;
    JButton confirmUpdateButton;
    JButton rejectUpdateButton;

    public Buttons(){

        FontIcon confirmIcon = FontIcon.of(MaterialDesign.MDI_CHECK,30,Color.GREEN);
        FontIcon rejectIcon = FontIcon.of(MaterialDesign.MDI_CLOSE_CIRCLE,30,Color.RED);
        FontIcon sortAZIcon = FontIcon.of(BootstrapIcons.SORT_ALPHA_DOWN,30,Color.BLACK);
        FontIcon sortPointsIcon = FontIcon.of(BootstrapIcons.SORT_NUMERIC_DOWN_ALT,30,Color.BLACK);
        FontIcon searchStudentIcon = FontIcon.of(MaterialDesign.MDI_ACCOUNT_SEARCH,30,Color.BLACK);
        FontIcon addNewIcon = FontIcon.of(MaterialDesign.MDI_ACCOUNT_PLUS,30,Color.BLACK);
        FontIcon addExistingIcon = FontIcon.of(BootstrapIcons.PERSON_CIRCLE,30,Color.BLACK);
        FontIcon removeStudentIcon = FontIcon.of(MaterialDesign.MDI_ACCOUNT_REMOVE,30,Color.BLACK);
        FontIcon updateIcon = FontIcon.of(MaterialDesign.MDI_BELL_RING,30,Color.BLACK);
        FontIcon addGroupIcon = FontIcon.of(MaterialDesign.MDI_FOLDER_PLUS,30,Color.BLACK);
        FontIcon removeGroupIcon = FontIcon.of(MaterialDesign.MDI_FOLDER_REMOVE,30,Color.BLACK);
        FontIcon saveCSVIcon = FontIcon.of(MaterialDesign.MDI_FILE_EXPORT,30,Color.BLACK);
        FontIcon addPointsIcon = FontIcon.of(BootstrapIcons.JOURNAL_PLUS,30,Color.WHITE);
        FontIcon removePointsIcon = FontIcon.of(BootstrapIcons.JOURNAL_MINUS,30,Color.WHITE);
        FontIcon changeConditionIcon = FontIcon.of(BootstrapIcons.GEO_ALT_FILL,30,Color.WHITE);

            addExistingStudentButton = new JButton();
            addExistingStudentButton.setIcon(addExistingIcon);
            addExistingStudentButton.setFocusable(false);
            addExistingStudentButton.setToolTipText("Add student that already exists in base");
            addExistingStudentButton.setBackground(new Color(   29, 61, 255  ));
            addExistingStudentButton.setForeground(Color.WHITE);

            addNewStudentButton = new JButton();
            addNewStudentButton.setIcon(addNewIcon);
            addNewStudentButton.setFocusable(false);
            //addNewStudentButton.setPreferredSize(new Dimension(50,100));
            addNewStudentButton.setToolTipText("Add new student");
            addNewStudentButton.setBackground(new Color(   29, 61, 255  ));
            addNewStudentButton.setForeground(Color.WHITE);

            removeStudentButton = new JButton();
            removeStudentButton.setIcon(removeStudentIcon);
            removeStudentButton.setFocusable(false);
            removeStudentButton.setToolTipText("Remove students you want to select");
            removeStudentButton.setBackground(new Color(  29, 61, 255  ));
            removeStudentButton.setForeground(Color.WHITE);

            sortNameStudentButton = new JButton();
            sortNameStudentButton.setIcon(sortAZIcon);
            sortNameStudentButton.setFocusable(false);
            sortNameStudentButton.setToolTipText("Sort A - Z");
            sortNameStudentButton.setBackground(new Color(  29, 61, 255  ));
            sortNameStudentButton.setForeground(Color.WHITE);

            sortPointsStudentButton = new JButton();
            sortPointsStudentButton.setIcon(sortPointsIcon);
            sortPointsStudentButton.setFocusable(false);
            sortPointsStudentButton.setToolTipText("Sort by points");
            sortPointsStudentButton.setBackground(new Color(  29, 61, 255  ));
            sortPointsStudentButton.setForeground(Color.WHITE);

            addPointsStudentButton = new JButton();
            addPointsStudentButton.setIcon(addPointsIcon);
            addPointsStudentButton.setFocusable(false);
            addPointsStudentButton.setToolTipText("Add points");
            addPointsStudentButton.setBackground(new Color(  29, 61, 255 ));
            addPointsStudentButton.setPreferredSize(new Dimension(50,50));
            addPointsStudentButton.setForeground(Color.WHITE);

            removePointsStudentButton = new JButton();
            removePointsStudentButton.setIcon(removePointsIcon);
            removePointsStudentButton.setFocusable(false);
            removePointsStudentButton.setToolTipText("Delete selected points");
            removePointsStudentButton.setBackground(new Color(  29, 61, 255  ));
            removePointsStudentButton.setForeground(new Color(48, 49, 54));
            removePointsStudentButton.setPreferredSize(new Dimension(50,50));
            removePointsStudentButton.setForeground(Color.WHITE);

            FontIcon editStudentIcon = FontIcon.of(MaterialDesign.MDI_ACCOUNT_CARD_DETAILS,30,Color.WHITE);
            editStudentButton = new JButton();
            editStudentButton.setFocusable(false);
            editStudentButton.setIcon(editStudentIcon);
            editStudentButton.setToolTipText("Edit student's data");
            editStudentButton.setPreferredSize(new Dimension(50,50));
            editStudentButton.setBackground(new Color(  29, 61, 255 ));
            editStudentButton.setForeground(Color.WHITE);

            changeConditionStudentButton = new JButton();
            changeConditionStudentButton.setIcon(changeConditionIcon);
            changeConditionStudentButton.setFocusable(false);
            changeConditionStudentButton.setToolTipText("Change student's condition");
            changeConditionStudentButton.setPreferredSize(new Dimension(50,50));
            changeConditionStudentButton.setBackground(new Color(  29, 61, 255 ));
            changeConditionStudentButton.setForeground(Color.WHITE);

            searchStudentButton = new JButton();
            searchStudentButton.setIcon(searchStudentIcon);
            searchStudentButton.setFocusable(false);
            searchStudentButton.setToolTipText("Search surname pattern");
            searchStudentButton.setPreferredSize(new Dimension(35,35));
            searchStudentButton.setBackground(new Color(  29, 61, 255  ));
            searchStudentButton.setForeground(Color.WHITE);

            confirmEditButton = new JButton();
            confirmEditButton.setFocusable(false);
            confirmEditButton.setIcon(confirmIcon);
            confirmEditButton.setPreferredSize(new Dimension(120,30));
            confirmEditButton.setBackground(new Color(  29, 61, 255 ));
            confirmEditButton.setForeground(Color.WHITE);

            confirmExistingStudentButton = new JButton();
            confirmExistingStudentButton.setIcon(confirmIcon);
            confirmExistingStudentButton.setPreferredSize(new Dimension(60,40));
            confirmExistingStudentButton.setBackground(new Color(  29, 61, 255  ));
            confirmExistingStudentButton.setForeground(Color.WHITE);

            confirmAddPointsButton = new JButton();
            confirmAddPointsButton.setIcon(confirmIcon);
            confirmAddPointsButton.setPreferredSize(new Dimension(60,40));
            confirmAddPointsButton.setBackground(new Color(  29, 61, 255  ));
            confirmAddPointsButton.setForeground(Color.WHITE);

            confirmRemovePointsButton = new JButton();
            confirmRemovePointsButton.setIcon(confirmIcon);
            confirmRemovePointsButton.setPreferredSize(new Dimension(60,40));
            confirmRemovePointsButton.setBackground(new Color(  29, 61, 255  ));
            confirmRemovePointsButton.setForeground(Color.WHITE);


            confirmRemoveButton = new JButton();
            confirmRemoveButton.setIcon(confirmIcon);
            confirmRemoveButton.setPreferredSize(new Dimension(60,40));
            confirmRemoveButton.setBackground(new Color(  29, 61, 255 ));
            confirmRemoveButton.setForeground(Color.WHITE);

            removeGroupsButton = new JButton();
            removeGroupsButton.setIcon(removeGroupIcon);
            removeGroupsButton.setFocusable(false);
            removeGroupsButton.setToolTipText("Delete selected subjects");
            removeGroupsButton.setPreferredSize(new Dimension(100,30));
            removeGroupsButton.setBackground(new Color(  29, 61, 255  ));
            removeGroupsButton.setForeground(Color.WHITE);

        saveCSVButton = new JButton();
        saveCSVButton.setIcon(saveCSVIcon);
        saveCSVButton.setFocusable(false);
        saveCSVButton.setToolTipText("Save data to .csv");
        saveCSVButton.setPreferredSize(new Dimension(100,30));
        saveCSVButton.setBackground(new Color(  29, 61, 255  ));
        saveCSVButton.setForeground(Color.WHITE);

            addGroupButton = new JButton();
            addGroupButton.setIcon(addGroupIcon);
            addGroupButton.setFocusable(false);
            addGroupButton.setToolTipText("Add new subject");
            addGroupButton.setPreferredSize(new Dimension(100,30));
            addGroupButton.setBackground(new Color(  29, 61, 255  ));
            addGroupButton.setForeground(Color.WHITE);

            updateConditionButton = new JButton();
            updateConditionButton.setIcon(updateIcon);
            updateConditionButton.setFocusable(false);
            updateConditionButton.setToolTipText("Update changes in subjects");
            updateConditionButton.setBackground(new Color(  29, 61, 255  ));
            updateConditionButton.setForeground(Color.WHITE);

        confirmUpdateButton = new JButton();
        confirmUpdateButton.setIcon(confirmIcon);
        confirmUpdateButton.setPreferredSize(new Dimension(30,30));
        confirmUpdateButton.setBackground(new Color(  29, 61, 255  ));
        confirmUpdateButton.setForeground(Color.WHITE);


        rejectUpdateButton = new JButton();
        rejectUpdateButton.setIcon(rejectIcon);
        rejectUpdateButton.setPreferredSize(new Dimension(30,30));
        rejectUpdateButton.setBackground(new Color(  29, 61, 255  ));
        rejectUpdateButton.setForeground(Color.WHITE);
    }
}
