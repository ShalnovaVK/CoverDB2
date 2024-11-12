package org.example.coverdb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FinderRowsController {
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
        FXMLLoader centerTableLoader = new FXMLLoader(getClass().getResource("FindRows.fxml"));
        AnchorPane centerTablePane = null;
        try {
            centerTablePane = (AnchorPane) centerTableLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return centerTablePane;
    }
}
