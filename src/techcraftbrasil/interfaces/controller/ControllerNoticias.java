package techcraftbrasil.interfaces.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

public class ControllerNoticias implements Initializable {

    @FXML
    private ListView list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image image = new Image("https://i.imgur.com/bWoNexA.png");
        for(int i = 0 ; i < 50; i++){
            Pane pane = new Pane();

            double width = list.getPrefWidth() - 20;
            pane.setLayoutY(0);
            pane.setLayoutX(0);
            pane.setMaxWidth(width);
            pane.setMinWidth(width);
            pane.setPrefWidth(width);
            pane.getStyleClass().add("teste");
            pane.setPrefHeight(30);


            ImageView imageView = new ImageView();
            imageView.setImage(image);

            imageView.setLayoutY(3);
            imageView.setLayoutX(3);

            imageView.setFitHeight(24);
            imageView.setFitWidth(24);

            Label titulo = new Label();
            titulo.setMinWidth(list.getPrefWidth() - 110 - 30 - 6);
            titulo.setMaxWidth(list.getPrefWidth() - 110 - 30 - 6);
            titulo.setLayoutY(0);
            titulo.setLayoutX(30);
            titulo.setEllipsisString("...");
            titulo.setText("Titulo " + i + " " + UUID.randomUUID());
            titulo.setPrefHeight(30);


            Label date = new Label();
            date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

            date.setLayoutX(list.getPrefWidth() - 110);
            date.setLayoutY(0);
            date.getStyleClass().add("date-notice-cell");
            date.setPrefHeight(30);

            pane.getChildren().add(imageView);
            pane.getChildren().add(titulo);
            pane.getChildren().add(date);
            list.getItems().add(pane);
        }
    }
}
