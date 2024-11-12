package org.example.coverdb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class AboutController {
    @FXML
    private Label label;
    @FXML
    private Image image;
    @FXML
    private ImageView imageView;
    @FXML
    public void initialize() {
        // Set up the image here after FXML fields have been initialized
        // Указываем полный путь к изображению
        String imagePath = "file:///C:/Users/ASUS/Downloads/me.jpg";

        // Загружаем изображение
        image = new Image(imagePath);
        imageView.setImage(image);
    }
    public AnchorPane connection() {
        FXMLLoader centerTableLoader = new FXMLLoader(getClass().getResource("about.fxml"));
        AnchorPane centerTablePane = null;
        try {
            centerTablePane = (AnchorPane) centerTableLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return centerTablePane;
    }
}