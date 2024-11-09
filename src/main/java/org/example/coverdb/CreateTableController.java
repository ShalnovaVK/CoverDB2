package org.example.coverdb;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateTableController {
    @FXML
    private TextField textField2;
    @FXML
    private Label label3;
    @FXML
    private TextField textField;
    @FXML
    private Button b;
    @FXML
    private Button b2;
    @FXML
    private GridPane root;

    private int columns;
    private String str;
    private List<String> dbCols = new ArrayList<String>();
    private List<String> typeData = new ArrayList<String>();
    private Database database = new Database();

    public void parseTField(){
        label3.setText("");
        if (textField.getText().matches("-?\\d+")) { columns = Integer.parseInt(textField.getText()); }
        else label3.setText("Это не число!!!");
    }
    public AnchorPane connection(){
        FXMLLoader centerTableLoader = new FXMLLoader(getClass().getResource("createTable.fxml"));
        AnchorPane centerTablePane = null;
        try {
            centerTablePane = (AnchorPane) centerTableLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return centerTablePane;
    }
    public void createTable() {

            str = ""; // Обнуляем str перед сбором новых данных
            ObservableList<Node> children = root.getChildren();
            for (int i = 3; i < children.size(); i++) {
                Node node = children.get(i);
                if (GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) != 0) {
                    TextField t = (TextField) node;
                    dbCols.add(t.getText());
                    // break;
                }
            }
        database.createTable(textField2.getText(), dbCols, typeData);

    }
    public void workWithChoiceBox() {
        // string array

        while(root.getRowConstraints().size() > 1){
            root.getRowConstraints().remove(1);
        }

        while(root.getColumnConstraints().size() > 1){
            root.getColumnConstraints().remove(1);
        }

        str = "";
        String st[] = { "int", "float", "varchar(30)",
                "varchar(70)", "varchar(255)", "boolean", "serial" };
        // create a choiceBox
        for (int i = 1; i <= columns; i++){
            TextField t = new TextField();
            ChoiceBox c = new ChoiceBox(FXCollections.observableArrayList(st));
            // add a listener
            c.setOnAction(actionEvent -> {
                typeData.add((String) c.getValue());
        });
            if(i != columns) str += ",";
            GridPane.setColumnIndex(c, i);
            GridPane.setRowIndex(c, 1);
            GridPane.setColumnIndex(t, i);
            GridPane.setRowIndex(c, 0);
            root.add(c, 1,i);
            root.add(t, 0,i);
        }
        System.out.println(str);
        // Add one RowConstraint for each row. The problem here is that you
        // have to know how many rows you have in you GridPane to set
        // RowConstraints for all of them.
        for (int i = 1; i <= columns; i++) {

            RowConstraints con = new RowConstraints();
            // Here we set the pref height of the row, but you could also use .setPercentHeight(double)
            // if you don't know much space you will need for each label.
            con.setPrefHeight(30);
            root.getRowConstraints().add(con);


        }
    }
}
