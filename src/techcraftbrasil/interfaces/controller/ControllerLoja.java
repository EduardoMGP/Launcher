package techcraftbrasil.interfaces.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerLoja implements Initializable {


    @FXML private Button btn_back;
    @FXML private WebView webView;


    public void goBack() {
        final WebHistory history = webView.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();

        Platform.runLater(() -> {
            history.go(entryList.size() > 1
                    && currentIndex > 0
                    ? -1
                    : 0);
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
