package techcraftbrasil.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

public class Alerts {

    public static void showAlert(String header, String content, AlertType type){
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(false);
        alert.initStyle(StageStyle.UNDECORATED);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                Alerts.class.getResource("alerts.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        alert.show();
    }
}
