package techcraftbrasil.interfaces.controller;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import techcraftbrasil.Launcher;
import techcraftbrasil.json.tcblauncher.Installed;
import techcraftbrasil.json.tcblauncher.Modpacks;
import techcraftbrasil.utils.Download;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerModpacks implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String home = Launcher.instance().getHomePath();
        File file = new File(home);
        if(!file.exists())
            if(!file.mkdir())
                System.out.println("Não foi possivel criar a pasta do launcher");

        try {

            Scene scene = Launcher.instance().getScene();

            Platform.runLater(() -> {
                for(Node n : scene.getRoot().lookupAll(".server-selector"))
                    if(n instanceof Pane)
                        for (Node node : ((Pane) n).getChildren())
                            if (node instanceof Button){
                                String id = node.getId();
                                if (id != null)
                                    if (id.startsWith("sv_")){
                                        Button button = (Button) node;
                                        button.setOnMousePressed(event -> downloadModpack(id.replace("sv_", "")));
                                    }

                            }

            });

            for(Modpacks modpacks : Launcher.instance().getModpacksInstalled().getModpacksList()) {
                String name = modpacks.getName();
                String version = modpacks.getVersion();
                Platform.runLater(() -> {
                    Button button = (Button) scene.lookup("#sv_"+name);
                    if(button != null) {
                        button.setText("Jogar | v" + version);
                        button.setOnMousePressed(event -> openMinecraft(name));
                    }
                });
            }

        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Download downloading = null;

    public void downloadModpack(String sv_) {
        if(downloading == null) {
            downloading = new Download(this, sv_);
            downloading.start();
        }

    }

    public void stopDownload() {
        if(downloading != null)
            downloading = null;
    }

    public void openMinecraft(String name) {
        System.out.println("jogando " + name);
    }

    public void onClick(){
        Launcher.instance().play("interfaces/view/sounds/button-click.wav");
    }
    public void onHover(){
        Launcher.instance().play("interfaces/view/sounds/button-hover.mp3");
    }
}
