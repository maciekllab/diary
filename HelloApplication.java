package com.example.lab6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        stage.setTitle("Teacher's diary");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest( event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save data");
            alert.setHeaderText("Do you want to save data to .ser .cvs files?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                try {
                    Data.getInstance().writeToCSVFiles();
                    Data.getInstance().serializeData();
                } catch (Exception e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setHeaderText("File doesn't exist or is being edited");
                    alert2.showAndWait();
                    event.consume();
                }
            }
        } );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Diary();
                Frames.getInstance().frame.setVisible(false);
            }
        });
        launch(args);
    }
}