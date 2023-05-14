package com.example.lab6;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class StudentController implements Initializable {
    @FXML
    Label userMenuLabel;
    @FXML
    Label nameMenuLabel;
    @FXML
    Label surnameMenuLabel;
    @FXML
    Label birthMenuLabel;
    @FXML
    Label numberMenuLabel;
    @FXML
    Label pointsMenuLabel;
    @FXML
    CheckBox allCheckBox;
    @FXML
    TextField pointsInfoArea;
    @FXML
    TextField conditionInfoArea;
    @FXML
    private TextField searchArea;
    @FXML
    private TableColumn<TableItem, String> subjectColumn;
    @FXML
    private TableColumn<TableItem, Integer> pointsColumn;
    @FXML
    private TableColumn<TableItem, StudentCondition> conditionColumn;
    @FXML
    private TableColumn<TableItem,String> capacityColumn;
    @FXML
    private TableView<TableItem> subjectsTable;


    public void switchToLoginPanel(javafx.event.ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.setMinHeight(300);
        stage.setMinWidth(300);
    }

    public void initData(){
        userMenuLabel.setText(Data.getInstance().client.name);
        nameMenuLabel.setText(Data.getInstance().client.name);
        surnameMenuLabel.setText(Data.getInstance().client.surname);
        birthMenuLabel.setText(Integer.toString(Data.getInstance().client.birthYear));
        numberMenuLabel.setText(Integer.toString(Data.getInstance().client.diaryNumber));
        pointsMenuLabel.setText(Data.getInstance().client.averagePoints +"%");
        if(Data.getInstance().client.averagePoints<50)pointsMenuLabel.setTextFill(Color.RED);
        else if(Data.getInstance().client.averagePoints<80)pointsMenuLabel.setTextFill(Color.GOLD);
        else pointsMenuLabel.setTextFill(Color.LIMEGREEN);
    }
    public void changeInterface(javafx.event.ActionEvent e){
        Stage temp =(Stage)((Node)e.getSource()).getScene().getWindow();
        temp.close();
        Frames.getInstance().frame.setVisible(true);
    }
    public void showSubjectsTable(ObservableList<TableItem> tempList){
        subjectsTable.setItems(tempList);
    }
    public ObservableList<TableItem> getTableItemsList(boolean extended){
        ObservableList<TableItem> temp = FXCollections.observableArrayList();
        if(extended)for (Map.Entry<String,Class> entry : ClassContainer.getInstance().groups.entrySet()) {
            if(Data.getInstance().client.conditionsMap.containsKey(entry.getKey()))temp.add(new TableItem(entry.getValue(), entry.getKey(),Data.getInstance().client.pointsMap.get(entry.getKey()).average,Data.getInstance().client.conditionsMap.get(entry.getKey())));
            else temp.add(new TableItem(entry.getValue(),entry.getKey(),0,StudentCondition.INACTIVE));
        }
        else for (Map.Entry<String,PointsList> entry : Data.getInstance().client.pointsMap.entrySet()) {
                temp.add(new TableItem(Data.getInstance().container.groups.get(entry.getKey()),entry.getKey(),entry.getValue().average,Data.getInstance().client.conditionsMap.get(entry.getKey())));
        }
        return temp;
    }
    public void allCheckBoxOnClick(){
        showSubjectsTable(getTableItemsList(!allCheckBox.isSelected()));
    }
    public void searchOnClick(){
        if(searchArea.getText().equals(""))return;
        ObservableList<TableItem> tempList = FXCollections.observableArrayList();
        for (Map.Entry<String,Class> entry : ClassContainer.getInstance().groups.entrySet()){
            if(!entry.getValue().groupName.contains(searchArea.getText()))continue;
            if(Data.getInstance().client.conditionsMap.containsKey(entry.getKey()))tempList.add(new TableItem(entry.getValue(), entry.getKey(),Data.getInstance().client.pointsMap.get(entry.getKey()).average,Data.getInstance().client.conditionsMap.get(entry.getKey())));
            else tempList.add(new TableItem(entry.getValue(),entry.getKey(),0,StudentCondition.INACTIVE));
        }

        showSubjectsTable(tempList);
    }
    public void signUpOnClick(){
        if(subjectsTable.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tip");
            alert.setHeaderText("Please select subject");
            alert.showAndWait();
            return;
        }
        if(Data.getInstance().client.conditionsMap.containsKey(subjectsTable.getSelectionModel().getSelectedItem().ItemClass.groupName))return;
        if(subjectsTable.getSelectionModel().getSelectedItem().ItemClass.addExistingStudent(Data.getInstance().client));
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Group is full");
            alert.showAndWait();
            return;
        }
        Data.getInstance().client.conditionsMap.put(subjectsTable.getSelectionModel().getSelectedItem().ItemClass.groupName,StudentCondition.WAITING);
        showSubjectsTable(getTableItemsList(!allCheckBox.isSelected()));
    }
    public void ResignOnClick(){
        if(subjectsTable.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tip");
            alert.setHeaderText("Please select subject");
            alert.showAndWait();
            return;
        }
        if(!Data.getInstance().client.conditionsMap.containsKey(subjectsTable.getSelectionModel().getSelectedItem().ItemClass.groupName))return;
        Data.getInstance().client.conditionsMap.remove(subjectsTable.getSelectionModel().getSelectedItem().ItemClass.groupName);
        Data.getInstance().client.conditionsMap.put(subjectsTable.getSelectionModel().getSelectedItem().ItemClass.groupName,StudentCondition.RESIGNED);
        showSubjectsTable(getTableItemsList(!allCheckBox.isSelected()));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TableColumn> columnList = new ArrayList<>(Arrays.asList(subjectColumn,pointsColumn,conditionColumn,capacityColumn));
        List<Field> fieldsList = new ArrayList<>(Arrays.asList(TableItem.class.getDeclaredFields()));
        fieldsList.removeIf(fld -> !fld.isAnnotationPresent(Exportable.class));
        for(int i =0;i<columnList.size();i++)columnList.get(i).setText(fieldsList.get(i).getName());
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
       capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
       subjectsTable.getSelectionModel().selectedItemProperty().addListener(e->{
            if(subjectsTable.getSelectionModel().getSelectedItem()!=null && Data.getInstance().client.conditionsMap.containsKey(subjectsTable.getSelectionModel().getSelectedItem().ItemClass.groupName)){
                Class temp = subjectsTable.getSelectionModel().getSelectedItem().ItemClass;
                conditionInfoArea.setText(Data.getInstance().client.conditionsMap.get(temp.groupName).toString());
                StringBuilder text= new StringBuilder();
                for (int a:Data.getInstance().client.pointsMap.get(temp.groupName).points) {
                    text.append(a).append(",");
                }
                pointsInfoArea.setText(text.toString());
            }
            else{
                pointsInfoArea.setText("");
                conditionInfoArea.setText("");
            }
       });
    }
}
