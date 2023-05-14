package com.example.lab6;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchToStudentPanel(javafx.event.ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student.fxml"));
        root = loader.load();
        StudentController s = loader.getController();
        try {s.initData();}catch(Exception exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("File doesn't exist or is being edited");
            alert.showAndWait();
            return;
        }
        s.showSubjectsTable(s.getTableItemsList(false));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
        stage.setMinHeight(600);
        stage.setMinWidth(900);
    }
}