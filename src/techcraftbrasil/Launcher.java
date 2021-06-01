package techcraftbrasil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import techcraftbrasil.json.forge.ForgeLibrary;
import techcraftbrasil.json.forge.RootForge;
import techcraftbrasil.json.mojang.Version;
import techcraftbrasil.json.mojang.VersionManifest;
import techcraftbrasil.json.mojang.libraries.Artifact;
import techcraftbrasil.json.mojang.libraries.Downloads;
import techcraftbrasil.json.mojang.libraries.Library;
import techcraftbrasil.json.mojang.libraries.Root;
import techcraftbrasil.json.tcblauncher.Installed;
import techcraftbrasil.json.tcblauncher.Modpacks;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Launcher extends Application {


//    public static void main(String[] args) throws IOException {
//
//        Gson gson = new Gson();
//        File file = new File("C:\\Users\\carlo\\AppData\\Roaming\\.tcblauncher\\modpacks\\play-craft-brasil\\bin\\modpack.jar");
//        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
//        ZipEntry zipEntry;
//        while((zipEntry = zipInputStream.getNextEntry()) != null){
//            if(zipEntry.getName().equals("version.json")){
//                File file1 = new File("C:\\Users\\carlo\\AppData\\Roaming\\.tcblauncher\\modpacks\\play-craft-brasil\\bin\\"+zipEntry.getName());
//                FileOutputStream fileOutputStream = new FileOutputStream(file1);
//                int len;
//                String json = "";
//                while ((len = zipInputStream.read()) != -1){
//                    fileOutputStream.write((char)len);
//                    json += (char)len;
//                }
//                fileOutputStream.close();
//                RootForge rootForge = gson.fromJson(json, RootForge.class);
//                for(ForgeLibrary forgeLibrary : rootForge.libraries){
//                    String url = forgeLibrary.url;
//                    String name[] = forgeLibrary.name.split(":");
//                    if(url != null)
//                        url += name[0].replace(".", "/") + "/" + name[1] + "/" + name[2] + "/" + name[1] + "-" + name[2] + ".jar";
//                    else
//                        url = "https://maven.minecraftforge.net/" + name[0].replace(".", "/") + "/" + name[1] + "/" + name[2] + "/" + name[1] + "-" + name[2] + ".jar";
//                    new Launcher().download(url, name[1] + "-" + name[2] + ".jar");
//                }
//
//                //https://maven.minecraftforge.net/lzma/lzma/0.0.1/lzma-0.0.1.jar
//                //https://maven.minecraftforge.net/lzma/lzma/0.0.1/lzma-0.0.1.jar
//                //https://maven.minecraftforge.net/lzma/lzma/0.0.1/lzma-0.0.1.jar
//
//                //https://libraries.minecraft.net/lzma/lzma/0.0.1/lzma-0.0.1.jar
//                return;
//            }
//        }
//        zipInputStream.close();
//        System.out.println("acabou :)");
//
////        Launcher launcher =  new Launcher();
////        String json = launcher.getJsonFromUrl("https://launchermeta.mojang.com/mc/game/version_manifest.json");
////        Gson gson = new Gson();
////        VersionManifest versionManifest = gson.fromJson(json, VersionManifest.class);
////        for(Version v : versionManifest.versions){
////            if(v.id.equals("1.7.10")){
////                json = launcher.getJsonFromUrl(v.url);
////                Root root = gson.fromJson(json, Root.class);
////                for(Library library : root.libraries){
////                    Downloads downloads = library.downloads;
////                    Artifact artifact = downloads.artifact;
////                    if(artifact == null) continue;
////                    String name[] = library.name.split(":");
////                    launcher.download(artifact.url, name[1] + "-" + name[2] + ".jar");
////                }
////                return;
////            }
////        }
//    }
//
//    public void download(String link, String nome) throws IOException {
//        try {
//            URL url = new URL(link);
//            URLConnection connection = url.openConnection();
//            System.out.println("Baixando: " + connection.getURL().toString());
//
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
//            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\carlo\\AppData\\Roaming\\.tcblauncher\\teste" + "\\"+nome);
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = bufferedInputStream.read(buffer, 0, buffer.length)) != -1){
//                outputStream.write(buffer, 0, buffer.length);
//            }
//            outputStream.close();
//        } catch (Exception e){
//            System.out.println("URL: " + link + " deu erro");
//        }
//
//    }

    private Scene scene;
    private String username = "EduardoMGP";
    private String homepath;
    private static Launcher instance;

    public static Launcher instance(){
        return instance;
    }

    public Scene getScene(){
        return scene;
    }
    public boolean isLogged(){
        return true;
    }
    public String getUsername(){
        return username;
    }
    public String getHomePath(){
        return homepath;
    }

    public Installed getModpacksInstalled() throws IOException {

        Gson gson = new Gson();
        File file = new File(getHomePath() + "\\installed.json");
        String json = getJsonFromFile(file);

        if(json != null)
            return gson.fromJson(json, Installed.class);
        else
            return new Installed();
    }

    public Modpacks getModpackVersion(String modpack_slug) throws IOException {

        Installed installed = getModpacksInstalled();
        if(installed == null)
            return null;
        else
            for(Modpacks modpacks : installed.getModpacksList())
                if(modpacks.getName().equalsIgnoreCase(modpack_slug))
                    return modpacks;
        return null;

    }

    public void setModpackVersion(String modpack_slug, String version) throws IOException {

        Gson gson = new Gson();
        Modpacks old_version = getModpackVersion(modpack_slug);
        Installed installed = getModpacksInstalled();
        if(old_version == null) {

            Modpacks modpack = new Modpacks();
            modpack.setVersion(version);
            modpack.setName(modpack_slug);
            installed.getModpacksList().add(modpack);

            File file = new File(getHomePath() + "\\installed.json");
            writefile(file, gson.toJson(installed));

        } else {
            installed.getModpacksList().remove(old_version);
            old_version.setVersion(version);
            installed.getModpacksList().add(old_version);
            gson.toJson(installed);
        }

    }

    private void writefile(File file, String text) throws IOException {
        if(!file.exists()) file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(text.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public void play(String name) {
        try {
            File file = new File(Launcher.class.getResource(name).toURI());

            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private void createpage(String name) {

        try {

            AnchorPane pane_load = FXMLLoader.load(Launcher.class.getResource("interfaces/view/" + name + ".fxml"));

            AnchorPane content = (AnchorPane) pane_load.getChildren().get(0);
            content.setLayoutX(0);
            content.setLayoutY(0);
            content.setVisible(false);
            content.setManaged(false);

            AnchorPane principal_pane = (AnchorPane) instance().scene.lookup("#content");

            principal_pane.getChildren().add(content);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Parent openPage(String name) {

        try {

            AnchorPane anchorPane = (AnchorPane) scene.lookup("#content");
            for(Node n : anchorPane.getChildren()){
                AnchorPane a = (AnchorPane) n;
                a.setVisible(false);
                a.setManaged(false);
            }

            anchorPane = (AnchorPane) scene.lookup("#"+name.toLowerCase());
            anchorPane.setVisible(true);
            anchorPane.setManaged(true);

            return anchorPane;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getJsonFromFile(File file){
        if(!file.exists()) return null;

        String json = "";
        try {

            FileInputStream inputStream = new FileInputStream(file);
            int read;
            while((read = inputStream.read()) != -1)
                json += (char)read;
            return json;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getJsonFromUrl(String url){
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
    public void start(Stage primaryStage) throws Exception {

        instance = this;
        homepath = System.getProperty("user.home") + "\\AppData\\Roaming\\.tcblauncher";

        File file = new File(homepath);
        if(!file.exists())
            if(!file.mkdir())
                System.out.println("Não foi possivel criar a pasta do launcher");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaces/view/ViewMain.fxml"));
        Pane pane = loader.load();
        scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);


        primaryStage.show();

        Launcher.instance().createpage("ViewModpacks");
        Launcher.instance().createpage("ViewLoja");
        Launcher.instance().createpage("ViewNoticias");
        Launcher.instance().createpage("ViewHome");

        Launcher.instance().openPage("ViewHome");

    }

}
