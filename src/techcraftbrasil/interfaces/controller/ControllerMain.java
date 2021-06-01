package techcraftbrasil.interfaces.controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import techcraftbrasil.Launcher;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {

    @FXML
    private Button btnModpacks;

    public void openPageModpacks() {
        Launcher.instance().openPage("ViewModpacks");
    }

    public void openPageNoticias() {
        Launcher.instance().openPage("ViewNoticias");
    }


    private String url = null;
    public void openPageLoja(){

        AnchorPane pane = (AnchorPane) Launcher.instance().openPage("ViewLoja");
        if(pane != null) {
            WebView webView = (WebView) pane.getChildren().get(0);
            if(url == null)
                url = "https://techcraftbrasil.com.br";
            webView.getEngine().load(url);

            webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if(Worker.State.SUCCEEDED.equals(newValue)){

                    url = webView.getEngine().getLocation();

                    ProgressIndicator progressIndicator = (ProgressIndicator) Launcher.instance().getScene().lookup("#load-icon");
                    if(progressIndicator != null)
                        progressIndicator.setProgress(0);

                    javafx.scene.control.TextField field = (javafx.scene.control.TextField) Launcher.instance().getScene().lookup("#search-field");
                    if(field != null)
                        field.setText(url);

                } else if(Worker.State.RUNNING.equals(newValue)){

                    url = webView.getEngine().getLocation();

                    TextField field = (TextField) Launcher.instance().getScene().lookup("#search-field");
                    if(field != null)
                        field.setText(url);

                    ProgressIndicator progressIndicator = (ProgressIndicator) Launcher.instance().getScene().lookup("#load-icon");
                    if(progressIndicator != null)
                        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

                }
            });

        }

    }

    public void openPageHome(){
        AnchorPane anchorPane = (AnchorPane) Launcher.instance().openPage("ViewHome");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
