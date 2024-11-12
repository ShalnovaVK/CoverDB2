package org.example.coverdb;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

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
    public void start(Stage myStage) throws IOException {
        //----------------------------------МЕНЮ----------------------------------------
        //Give the stage a title.
        myStage.setTitle("Мои Корзинки");

        //Use a BorderPane for the root node.
        BorderPane rootNode = new BorderPane();
        //Create a scene.
        Scene myScene = new Scene(rootNode, 500, 500);
        //Set the scene on the stage.
        myStage.setScene(myScene);
        //Create a label that will report the selection.

        Label response = new Label("Menu Demo");
        //Create the menu bar.
        MenuBar mb = new MenuBar();

        // Create the File menu.

        Menu fileMenu = new Menu("Файл");

        MenuItem exit = new MenuItem("Выход");
        fileMenu.getItems().addAll(exit);

        //Add File menu to the menu bar.

        mb.getMenus().add(fileMenu);
        //Create the Options menu.
        Menu tablesMenu = new Menu("Таблицы");
        // Create the submenu.
        Menu regimMenu = new Menu("Режим");
        MenuItem insert = new MenuItem("Заполнение");
        MenuItem create = new MenuItem("Создание");
        regimMenu.getItems().addAll(insert, create);
        tablesMenu.getItems().add(regimMenu);
        // Create the Priority submenu.
        Menu ListMenu = new Menu("Работа с записями");
        MenuItem update = new MenuItem("Изменение");
        MenuItem delete = new MenuItem("Удаление");
        MenuItem find = new MenuItem("Поиск");

        ListMenu.getItems().addAll(update, delete, find);
        tablesMenu.getItems().add(ListMenu);
        // Add a separator.

        tablesMenu.getItems().add(new SeparatorMenuItem());

        //Create the Reset menu item.

        MenuItem reset = new MenuItem("На главную");

        tablesMenu.getItems().add(reset);

        //Add Options menu to the menu bar.

        mb.getMenus().add(tablesMenu);

        //Create the Help menu.

        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About"); helpMenu.getItems().add(about);

        //Add Help menu to the menu bar.

        mb.getMenus().add(helpMenu);

        //Create one event handler that will handle menu action events.
        EventHandler<ActionEvent> MEHandler =
                new EventHandler<ActionEvent>() { public void handle(ActionEvent ae) {
                    String name = ((MenuItem)ae.getTarget()).getText();
                    if(name.equals("Выход")) Platform.exit();
                    //response.setText( name + " selected");
                }
        };
        EventHandler<ActionEvent> MEHandler2 =
                new EventHandler<ActionEvent>() { public void handle(ActionEvent ae) {
                    String name = ((MenuItem)ae.getTarget()).getText();
                    if(name.equals( "Создание")){
                            CreateTableController c = new CreateTableController();
                            rootNode.setCenter(c.connection());
                            }

                }
                };
        EventHandler<ActionEvent> MEHandler3 =
                new EventHandler<ActionEvent>() { public void handle(ActionEvent ae) {
                    String name = ((MenuItem)ae.getTarget()).getText();
                    if(name.equals( "Заполнение")){
                        InsertTableController c = new InsertTableController();
                        rootNode.setCenter(c.connection());
                    }

                }
                };
        EventHandler<ActionEvent> MEHandler4 =
                new EventHandler<ActionEvent>() { public void handle(ActionEvent ae) {
                    String name = ((MenuItem)ae.getTarget()).getText();
                    if(name.equals( "Удаление")){
                        DeleteRowsController c = new DeleteRowsController();
                        rootNode.setCenter(c.connection());
                    }

                }
                };
        EventHandler<ActionEvent> MEHandler5 =
                new EventHandler<ActionEvent>() { public void handle(ActionEvent ae) {
                    String name = ((MenuItem)ae.getTarget()).getText();
                    if(name.equals( "Поиск")){
                        FinderRowsController c = new FinderRowsController();
                        rootNode.setCenter(c.connection());
                    }

                }
                };
        EventHandler<ActionEvent> MEHandler6 =
                new EventHandler<ActionEvent>() { public void handle(ActionEvent ae) {
                    String name = ((MenuItem)ae.getTarget()).getText();
                    if(name.equals( "Изменение")){
                        UpdateRowController c = new UpdateRowController();
                        rootNode.setCenter(c.connection());
                    }

                }
                };
        EventHandler<ActionEvent> MEHandler7 =
                new EventHandler<ActionEvent>() { public void handle(ActionEvent ae) {
                    String name = ((MenuItem)ae.getTarget()).getText();
                    if(name.equals( "About")){
                        AboutController c = new AboutController();
                        rootNode.setCenter(c.connection());
                    }

                }
                };



        // Set action event handlers for the menu items.
        exit.setOnAction(MEHandler);
        insert.setOnAction(MEHandler3); create.setOnAction(MEHandler2);
        update.setOnAction(MEHandler6);delete.setOnAction(MEHandler4);
        reset.setOnAction(MEHandler2);find.setOnAction(MEHandler5);
        about.setOnAction(MEHandler7);
        rootNode.setTop(mb);
        //----------------------------------МЕНЮ--------------------------------------------------------

        // Add keyboard accelerators for the File menu.

        //open.setAccelerator(KeyCombination.keyCombination("shortcut+O"));
        //close.setAccelerator(KeyCombination.keyCombination("shortcut+C"));
        //save.setAccelerator(KeyCombination.keyCombination("shortcut+S"));
        exit.setAccelerator(KeyCombination.keyCombination("shortcut+E"));

        //-------------ВСТАВИТЬ, КОПИРОВАТЬ, ВЫРЕЗАТЬ--------------------------------


        //Create the context menu items
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        final ContextMenu editMenu = new ContextMenu(cut, copy, paste);
        cut.setOnAction(MEHandler);
        copy.setOnAction(MEHandler);
        paste.setOnAction(MEHandler);
        // Create a text field and set its column width to 20.
        TextField tf = new TextField();
        tf.setPrefColumnCount(20);
        // Add the context menu to the textfield.
        tf.setContextMenu(editMenu);
        rootNode.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>()
                                           {public void handle(ContextMenuEvent ae) {
                        // Popup menu at the location of the right click.
                        editMenu.show(rootNode, ae.getScreenX(), ae.getScreenY());}});
        //Add the menu bar to the top of the border pane and
        //the response label to the center position.

        FlowPane fpRoot = new FlowPane(10, 10);
        ///Center the controls in the scene.
        fpRoot.setAlignment(Pos.CENTER);
        //Add both the label and the text field to the flow pane.
        fpRoot.getChildren().addAll(response, tf);
        //Add the flow pane to the center of the border layout.
        rootNode.setCenter(fpRoot);
        //-----------------------------------------------------------
        //Show the stage and its scene.
        myStage.show();

    }
}

