package techcraftbrasil.utils;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import techcraftbrasil.Launcher;
import techcraftbrasil.interfaces.controller.ControllerModpacks;
import techcraftbrasil.json.solder.Modpack;
import techcraftbrasil.json.solder.ModpackDetails;
import techcraftbrasil.json.solder.Mods;
import techcraftbrasil.json.tcblauncher.Installed;
import techcraftbrasil.json.tcblauncher.Modpacks;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Download extends Thread {

    private Button button_download;
    private ProgressBar progressBar;
    private Label label;
    private StackPane stackPane;
    private ControllerModpacks controllerModpacks;

    private ModpackDetails modpackDetails;

    private Timer timer;
    private double progress = 0;
    private long size_total = 0;
    private String downloading = "";
    private String modpack;
    private String version;

    public Download(ControllerModpacks controllerModpacks, String modpack){

        this.modpack = modpack;
        this.controllerModpacks = controllerModpacks;
        this.modpackDetails = openUrl();

        AnchorPane content = (AnchorPane) Launcher.instance().getScene().lookup("#viewmodpacks");

        stackPane = new StackPane();
        stackPane.setLayoutY(content.getHeight() - 50);
        stackPane.setLayoutX(200);
        stackPane.setPrefWidth(content.getWidth() - 400);
        stackPane.setPrefHeight(30);
        stackPane.setId("downloading_pane");

        progressBar = new ProgressBar();
        progressBar.setProgress(0);
        progressBar.setPrefWidth(content.getWidth() - 400);
        progressBar.setPrefHeight(30);

        label = new Label();
        label.setId("downloading_progress");
        label.setText("Downloading... 0%");

        stackPane.getChildren().add(progressBar);
        stackPane.getChildren().add(label);

        content.getChildren().add(stackPane);

        button_download = (Button) Launcher.instance().getScene().lookup("#sv_"+modpack);
        if(button_download != null) {
            button_download.setText("Cancelar download");
            button_download.setOnMousePressed(event -> cancel());
        }

    }

    public Button getButton(){
        return this.button_download;
    }

    public String getModpack(){
        return this.modpack;
    }

    public StackPane getStackPane(){
        return this.stackPane;
    }

    public ModpackDetails openUrl(){

        String url = "http://solder.techcraftbrasil.com.br/api/modpack/" + modpack;
        String json = Launcher.instance().getJsonFromUrl(url);
        Gson gson = new Gson();
        Modpack modpack = gson.fromJson(json, Modpack.class);

        version = modpack.getRecommended();
        url = "http://solder.techcraftbrasil.com.br/api/modpack/" + getModpack() + "/" + modpack.getRecommended();
        json = Launcher.instance().getJsonFromUrl(url);

        return gson.fromJson(json, ModpackDetails.class);
    }

    private String getHashFromFile(File mod) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            InputStream file = new FileInputStream(mod);
            while (true) {
                int c = file.read(buffer);
                if (c > 0)
                    md5.update(buffer, 0, c);
                else if (c < 0)
                    break;
            }
            file.close();
            String result = "";
            for (byte b : md5.digest()) {
                result += String.format("%02x", b);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public File getFolder(String name){
        File file = new File(Launcher.instance().getHomePath() + "\\modpacks\\" + getModpack() + "\\" + name);
        file.getParentFile().mkdir();
        file.mkdir();
        return file;
    }

    private void unzip(File zip, File dest) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zip));
        ZipEntry zipEntry = zipInputStream.getNextEntry();

        FileOutputStream fileOutputStream;
        while((zipEntry = zipInputStream.getNextEntry()) != null) {
            File modpack = new File(dest, zipEntry.getName());
            if(!modpack.exists())
                if (zipEntry.isDirectory()) {
                    modpack.getParentFile().mkdir();
                    modpack.mkdir();
                    System.out.println("Criando arquivo " + modpack);
                } else {
                    FileOutputStream outputStream = new FileOutputStream(modpack);
                    byte[] buffer = new byte[1024];
                    int lenght;
                    while((lenght = zipInputStream.read(buffer)) != -1)
                        outputStream.write(buffer, 0, lenght);
                    outputStream.close();

                }

        }
        zipInputStream.close();
    }

    public boolean isModsContains(File modfile){
        Iterator<Mods> mods  = modpackDetails.getMods().iterator();
        while(mods.hasNext()){
            Mods mod = mods.next();
            if(getHashFromFile(modfile).equals(mod.getMd5())) {
                modpackDetails.getMods().remove(mod);
                return true;
            } else {
                size_total += mod.getFilesize();
            }
        }
        return false;

    }

    public void unzipModpack() throws IOException {
        File cache = getFolder("cache");
        File modpack = getFolder("");
        for(File modfile : cache.listFiles())
            if(modfile.getName().endsWith(".zip"))
                unzip(modfile, modpack);
    }

    public void checkUpdate(){

//        while(interatormods.hasNext()) {
//
//            Mods mod = interatormods.next();
//            final double p = i;
//
//            Platform.runLater(() -> {
//                label.setText("Verificando md5 do mod " + mod.getName() + " v" +mod.getVersion() + "");
//                progressBar.setProgress((int)(p / total));
//            });
//            i++;
//
//            isModsContains(modpackDetails.getMods(), mod);
//
//            if(modfile.exists()) {
//                String md5 = getHashFromFile(modfile);
//                if (md5 != null)
//                    if (md5.equals(mod.getMd5())) {
//                        System.out.println("Pulando download do mod " + mod.getName() + " o mesmo já existe e possui o mesmo md5");
//                        interatormods.remove();
//                        continue;
//                    }
//            }
//            size_total += mod.getFilesize();
//        }
    }
    public void checkFolderCache(){

        File cache = getFolder("cache");

        double i = 0;
        double total = cache.listFiles().length;
        for(File mod : cache.listFiles()){

            final double p = i;
            Platform.runLater(() -> {
                label.setText("Verificando md5 do arquivo " + mod.getName() + "");
                progressBar.setProgress(p / total);
            });

            if(!isModsContains(mod)) {
                System.out.println("Deletado o arquivo " + mod.getName());
                mod.delete();
            }
            i++;
        }
    }

    @Override
    public void run(){

        List<Mods> mods = modpackDetails.getMods();
        File cache = getFolder("cache");
        checkFolderCache();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    label.setText("Baixando " + downloading + " " + (int)progress + "%");
                    progressBar.setProgress(progress / 100d);
                });
            }
        }, 0, 100);

        double size = 0;
        for(Mods mod : mods) {
            try {
                downloading = mod.getName() + " v"+mod.getVersion();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(mod.getUrl()).openStream());
                File modfile = new File(cache,  mod.getName() + "-" + mod.getVersion() + ".zip");

                System.out.println("Baixando " + mod.getUrl());

                FileOutputStream out = new FileOutputStream(modfile);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = bufferedInputStream.read(bytes, 0, 1024)) != -1){
                    out.write(bytes, 0 , length);
                    size += length;
                    progress = (size / size_total) * 100d;
                    out.flush();
                }
                out.close();
                bufferedInputStream.close();
                String md5 = getHashFromFile(modfile);
                if(md5 == null || !md5.equals(mod.getMd5())) cancel();

            } catch (Exception e){
                e.printStackTrace();
                cancel();
            }
        }

        try {
            System.out.println("Download concluido, extraindo arquivos");
            unzipModpack();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            cancel();
        }

    }

    public void cancel(){
        timer.cancel();
        Platform.runLater(()-> {
            if(button_download != null) {
                button_download.setText("Download");
                button_download.setOnMousePressed(event -> controllerModpacks.downloadModpack(getModpack()));
            }

            AnchorPane content = (AnchorPane) Launcher.instance().getScene().lookup("#viewmodpacks");
            if(stackPane != null && content != null)
                content.getChildren().remove(stackPane);

        });
        stop();
        controllerModpacks.stopDownload();
    }

    public void finish() {

        try {
            Launcher.instance().setModpackVersion(modpack, version);
        } catch (IOException e) {
            cancel();
            return;
        }

        timer.cancel();
        Platform.runLater(()-> {
            if(button_download != null) {
                button_download.setText("Jogar | v" + version);
                button_download.setOnMousePressed(event -> controllerModpacks.openMinecraft(getModpack()));
            }

            AnchorPane content = (AnchorPane) Launcher.instance().getScene().lookup("#viewmodpacks");
            if(stackPane != null && content != null)
                content.getChildren().remove(stackPane);

        });
        stop();
        controllerModpacks.stopDownload();
    }

}
