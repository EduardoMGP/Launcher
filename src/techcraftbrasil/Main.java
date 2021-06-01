package techcraftbrasil;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import techcraftbrasil.gui.UpdateRow;
import techcraftbrasil.gui.View;
import techcraftbrasil.json.solder.Modpack;
import techcraftbrasil.json.solder.ModpackDetails;
import techcraftbrasil.json.solder.Mods;
import techcraftbrasil.json.techniclauncher.Technic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;


public class Main extends Application {

//    public static void main(String[] args) throws IOException {
//
//        System.out.println();
//
//
////        URL url = new URL("http://repositorio.techcraftbrasil.com.br/mods/actuallyadditions/actuallyadditions-1.7.10-r21.zip");
////        double size = url.openConnection().getContentLength();
////
////        System.out.println(size);
////        BufferedInputStream in = new BufferedInputStream(
////                url.openConnection().getInputStream()
////        );
////
////        byte[] buffers = new byte[1024];
////        FileOutputStream out = new FileOutputStream("teste.zip");
////        int data;
////        double downloadFileSize = 0;
////        while ((data = in.read(buffers, 0, 1024)) != -1) {
////            downloadFileSize += data;
////            final int progress = (int)(((double)downloadFileSize) / ((double) size) * 100d);
////            System.out.println("Porcentagem: " + progress + " %");
////            out.write(buffers, 0, data);
////        }
//    }


    public static void main(String[] args) throws IOException {
        launch(args);

        String solder = "http://solder.techcraftbrasil.com.br/api/";
        String modpack_1_12 = "modpack/techcraftbrasil-versao-leve/";

        Gson gson = new Gson();
        Modpack modpack = gson.fromJson(getJsonFromUrl(solder + modpack_1_12), Modpack.class);

        Technic technic = gson.fromJson(getJsonFromUrl("https://api.technicpack.net/modpack/techcraftbrasil-versao-leve?build=944296"), Technic.class);

        System.out.println("Modpack: " + technic.getDisplayName());
        System.out.println("Versão: " + modpack.getRecommended());
        System.out.println("Descrição: " + technic.getDescription());
        System.out.println("Owner: " + technic.getUser());
        System.out.println("Downloads: " + technic.getDownloads());
        System.out.println("Executado: " + technic.getRuns());
        System.out.println("Avaliacao: " + technic.getRatings());

        ModpackDetails details = gson.fromJson(getJsonFromUrl(solder + modpack_1_12 + modpack.getRecommended()), ModpackDetails.class);
        for(Mods mod : details.getMods()){
            System.out.println("Nome: " +  mod.getName());
            System.out.println("Url: " +  mod.getUrl());
            System.out.println("Version: " +  mod.getVersion());
        }

    }

    public static String getJsonFromUrl(String url){
        String json = "";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new URL(url).openStream());
            int read;
            while((read = inputStreamReader.read()) != -1)
                json += (char)read;
        } catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void stop() throws Exception {
        System.exit(1000);
    }

    private static double x = 0;
    private static double y = 0;

    private static Scene scene;

    public static Scene getScene(){
        return scene;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            primaryStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/View.fxml"));
            AnchorPane parent = fxmlLoader.load();

            primaryStage.setResizable(false);

            scene = new Scene(parent);
            scene.setOnMousePressed(event -> {
                x = primaryStage.getX() - event.getScreenX();
                y = primaryStage.getY() - event.getScreenY();
            });

            scene.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() + x);
                primaryStage.setY(event.getScreenY() + y);
            });
            primaryStage.setScene(scene);

            primaryStage.setWidth(900);
            primaryStage.setHeight(600);
            primaryStage.getIcons().add(new Image("https://i.imgur.com/97zXnPw.png"));

            primaryStage.show();



//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//
//                    ScrollPane scrollPane = (ScrollPane) scene.lookup("#noticias");
//                    Pane pane = (Pane) scrollPane.getContent();
//
//                    Pane anterior = null;
//
//                    for(Node node : pane.getChildren()){
//                        Pane node_Pane = (Pane) node;
//                        if(anterior != null)
//                            node_Pane.setLayoutY(anterior.getLayoutY() + anterior.getHeight());
//                        anterior = node_Pane;
//                    }
//                }
//            });



//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        URL url = new URL("http://repositorio.techcraftbrasil.com.br/mods/techcraftbrasil/techcraftbrasil-4.4.zip");
//                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                        double size = connection.getContentLength();
//
//                        BufferedInputStream in = new BufferedInputStream(
//                                connection.getInputStream()
//                        );
//
//                        byte[] buffers = new byte[1024];
//                        FileOutputStream out = new FileOutputStream("teste.zip");
//                        int data;
//                        double downloadFileSize = 0;
//                        while ((data = in.read(buffers, 0, 1024)) != -1) {
//                            downloadFileSize += data;
//                            final int progress = (int)(((double)downloadFileSize) / ((double) size) * 100d);
//                            System.out.println(progress / 100d);
//                            View.instance.getProgressBar().setProgress(progress / 100d);
//                            View.instance.getText().setText("Download... " + progress + "%");
//                            out.write(buffers, 0, data);
//
//                        }
//                        in.close();
//                        out.close();
//                        connection.disconnect();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
