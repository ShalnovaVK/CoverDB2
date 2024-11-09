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
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static sun.tools.jconsole.LabeledComponent.layout;


public class InsertTableController {
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
    private GridPane gridPane2;

    private int columns;
    private String str;
    private List<String> dbCols;
    private List<String> data;
    private Database database = new Database();

//    public void parseTField(){
//        label3.setText("");
//        if (textField.getText().matches("\\d+")) { columns = Integer.parseInt(textField.getText()); }
//        else label3.setText("Это не число!!!");
//    }
    public AnchorPane connection(){
        FXMLLoader centerTableLoader = new FXMLLoader(getClass().getResource("insertTable.fxml"));
        AnchorPane centerTablePane = null;
        try {
            centerTablePane = (AnchorPane) centerTableLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return centerTablePane;
    }

    public void insertTable() {
            data = new ArrayList<String>();
            str = ""; // Обнуляем str перед сбором новых данных
            ObservableList<Node> children = gridPane2.getChildren();
            for (int i = 1; i < children.size(); i +=2) {
                Node node = children.get(i);
                TextField t  = (TextField)node ;
                str = t.getText();
                data.add(t.getText());
            }
            System.out.println(data);

       database.insertRow(nameTable.getText(), dbCols, data);

    }
    public void workWithScrollBox() throws SQLException {
        // string array

        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();


        gridPane2.getColumnConstraints().clear();
        gridPane2.getRowConstraints().clear();
        gridPane2.getChildren().clear();


        ResultSet rs = database.readTable(nameTable.getText());
        dbCols  = new ArrayList<String>();

        if( rs != null) {
            ResultSetMetaData md = rs.getMetaData();
            int j = 1; // Для строк данных
            while (rs.next()) {
                for (int i = 0; i < md.getColumnCount(); i++) {
                    if(j == 1){
                        gridPane2.add(new Label(md.getColumnName(i + 1)), i, 0);
                        dbCols.add(md.getColumnName(i + 1));
                        gridPane2.add(new TextField(), i, 1);
                    }//Заголовок столбца для вставки
                    gridPane.add(new Label(md.getColumnName(i + 1)), i, 0); // Заголовок столбца
                    gridPane.add(new Label(rs.getString(i + 1)), i, j); // Получение данных по столбцам
                }
                j++;
            }

        }
        gridPane.setGridLinesVisible(true);
        gridPane2.setGridLinesVisible(true);
        gridPane.setVisible(true);
        gridPane2.setVisible(true);
        gridPane.layout();
        gridPane.requestLayout();

    }
}
