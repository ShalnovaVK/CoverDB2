package org.example.coverdb;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HelloApplication extends Application {


    public static void main(String[] args) {
        launch();
        //Database db = new Database();
        //List<String> columns = Arrays.asList("buyer_id", "full_name", "login", "password", "contacts", "phone_number");
        //List<String> values = Arrays.asList("5", "'ivanov ivan'", "'ivanov'", "'secret'", "'ivan@example.com'", "'+7-499-777-88-90'");
        //db.insertRow("client", columns, values);
        //db.readTable("client");
    }
    @Override
    public void start(Stage myStage) {
        //Give the stage a title.
        myStage.setTitle("Demonstrate Menus");

        //Use a BorderPane for the root node.
        BorderPane rootNode = new BorderPane();
        //Create a scene.
        Scene myScene = new Scene(rootNode, 300, 300);
        //Set the scene on the stage.
        myStage.setScene(myScene);
        //Create a label that will report the selection.

        Label response = new Label("Menu Demo");
        //Create the menu bar.
        MenuBar mb = new MenuBar();

        // Create the File menu.

        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem close = new MenuItem("Close");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(open, close, save, new SeparatorMenuItem(), exit);

        //Add File menu to the menu bar.

        mb.getMenus().add(fileMenu);
        //Create the Options menu.
        Menu optionsMenu = new Menu("Options");
        // Create the Colors submenu.
        Menu colorsMenu = new Menu("Colors");
        MenuItem red = new MenuItem("Red");
        MenuItem green = new MenuItem("Green");
        MenuItem blue = new MenuItem("Blue");
        colorsMenu.getItems().addAll(red, green, blue);
        optionsMenu.getItems().add(colorsMenu);
        // Create the Priority submenu.
        Menu priorityMenu = new Menu("Priority");
        MenuItem high = new MenuItem("High");
        MenuItem low = new MenuItem("Low");
        priorityMenu.getItems().addAll(high, low);
        optionsMenu.getItems().add(priorityMenu);
        // Add a separator.

        optionsMenu.getItems().add(new SeparatorMenuItem());

        //Create the Reset menu item.

        MenuItem reset = new MenuItem("Reset");

        optionsMenu.getItems().add(reset);

        //Add Options menu to the menu bar.

        mb.getMenus().add(optionsMenu);

        //Create the Help menu.

        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About"); helpMenu.getItems().add(about);

        //Add Help menu to the menu bar.

        mb.getMenus().add(helpMenu);

        //Create one event handler that will handle menu action events.
        EventHandler<ActionEvent> MEHandler =
                new EventHandler<ActionEvent>() { public void handle(ActionEvent ae) {
                    String name = ((MenuItem)ae.getTarget()).getText();
                    // If Exit is chosen, the program is terminated.

                    if(name.equals("Exit")) Platform.exit();
                    response.setText( name + " selected");
                }
        };
        // Set action event handlers for the menu items.
        open.setOnAction(MEHandler); close.setOnAction(MEHandler);
        save.setOnAction(MEHandler); exit.setOnAction(MEHandler);
        red.setOnAction(MEHandler); green.setOnAction(MEHandler);
        blue.setOnAction(MEHandler); high.setOnAction(MEHandler);
        low.setOnAction(MEHandler); reset.setOnAction(MEHandler);
        about.setOnAction(MEHandler);
        // Add keyboard accelerators for the File menu.

        open.setAccelerator(KeyCombination.keyCombination("shortcut+O"));
        close.setAccelerator(KeyCombination.keyCombination("shortcut+C"));
        save.setAccelerator(KeyCombination.keyCombination("shortcut+S"));
        exit.setAccelerator(KeyCombination.keyCombination("shortcut+E"));
        //Add the menu bar to the top of the border pane and
        //the response label to the center position.
        rootNode.setTop(mb); rootNode.setCenter(response);
        //Show the stage and its scene.
        myStage.show();

    }
}

