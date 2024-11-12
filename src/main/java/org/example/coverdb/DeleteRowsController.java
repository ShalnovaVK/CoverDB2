package org.example.coverdb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteRowsController {
    @FXML
    private TextField nameTable;
    @FXML
    private Label label3;
    @FXML
    private TextField textField;
    @FXML
    private Button b;
    @FXML
    private Button b2;
    @FXML
    private GridPane gridPane;
    @FXML
    private HBox hbox;

    private int columns;
    private String str;
    private List<String> dbCols ;
    private List<String> data = new ArrayList<String>();
    private Database database = new Database();

    public int parseTField(String textField){
        if (textField.matches("\\d+")) { return Integer.parseInt(textField); }
        else return -1;
    }
    public AnchorPane connection(){
        FXMLLoader centerTableLoader = new FXMLLoader(getClass().getResource("deleteRows.fxml"));
        AnchorPane centerTablePane = null;
        try {
            centerTablePane = (AnchorPane) centerTableLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return centerTablePane;
    }

    public void deleteRows() {
        data = new ArrayList<String>();
        str = ""; // Обнуляем str перед сбором новых данных
        ObservableList<Node> children = hbox.getChildren();
        ChoiceBox node = (ChoiceBox) children.get(1);
        if (node.getValue() ==  "по столбцу"){
            database.deleteRow(nameTable.getText(),((ChoiceBox<String>) children.get(2)).getValue(),
                    ((TextField) children.get(4)).getText());
        }
        else{
            String t = ((TextField) children.get(2)).getText();
            if (parseTField(t) != -1)

                database.deleteRowByNumber(nameTable.getText(),dbCols.get(0),((TextField) children.get(2)).getText());
        }
        //System.out.println(data);

        // database.insertRow(nameTable.getText(), dbCols, data);

    }
    public void workWithChoiceBox() throws SQLException {

        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        dbCols = new ArrayList<String>();

        ResultSet rs = database.readTable(nameTable.getText());

        if( rs != null) {
            ResultSetMetaData md = rs.getMetaData();
            int j = 1; // Для строк данных
            while (rs.next()) {
                for (int i = 0; i < md.getColumnCount(); i++) {
                    if(j == 1){
                        dbCols.add(md.getColumnName(i + 1));
                    }//Заголовок столбца для вставки
                    gridPane.add(new Label(md.getColumnName(i + 1)), i, 0); // Заголовок столбца
                    gridPane.add(new Label(rs.getString(i + 1)), i, j); // Получение данных по столбцам
                }
                j++;
            }
        }
        // string array

        String st[] = { "по счёту", "по столбцу" };
        // create a choiceBox
        ChoiceBox c = new ChoiceBox(FXCollections.observableArrayList(st));
        hbox.getChildren().clear();
        hbox.getChildren().add(label3);
        hbox.getChildren().add(c);
        c.setOnAction(actionEvent -> {
            if(c.getValue()== "по столбцу"){
                hbox.getChildren().clear();
                hbox.getChildren().add(label3);
                hbox.getChildren().add(c);
                ObservableList<Node> children = hbox.getChildren();
                hbox.getChildren().add(new ChoiceBox<>(FXCollections.observableArrayList(dbCols)));
                hbox.getChildren().add(new Label(" = "));
                hbox.getChildren().add(new TextField());
            }
            else{
                hbox.getChildren().clear();
                hbox.getChildren().add(label3);
                hbox.getChildren().add(c);
                hbox.getChildren().add(new TextField());
            }
        });



    }
}
