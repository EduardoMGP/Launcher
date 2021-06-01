package techcraftbrasil.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import techcraftbrasil.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class View implements Initializable {

    public static View instance;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMin;

    @FXML
    private TextField fildUsername;

    public String getUsername(){
        System.out.println(fildUsername.getText());
        if(fildUsername != null)
            if(!fildUsername.getText().equals(""))
                return fildUsername.getText();
        return null;
    }

    public void onClickButtonExit(){
        Platform.exit();
    }

    public void onClickButtonMin(){
        if(Main.getScene() != null)
            ((Stage) Main.getScene().getWindow()).setIconified(true);
    }

    public void onClickServer() throws IOException {
        String username = getUsername();
        if(username == null)
            Alerts.showAlert( "Username inválido", "Digite seu username no campo ao lado", Alert.AlertType.ERROR);
        else if(username.length() > 16)
            Alerts.showAlert( "Username inválido", "O username pode ter no máximo 16 caracteres", Alert.AlertType.ERROR);
        else if(username.length() < 3)
            Alerts.showAlert( "Username inválido", "O username precisa ter no mínomo 3 caracteres", Alert.AlertType.ERROR);
        else
            Runtime.getRuntime().exec("\"C:\\Program Files\\Java\\jre1.8.0_271\\bin\\javaw.exe\" -Xms4096m -Xmx4096m -XX:+UseG1GC -XX:MaxGCPauseMillis=4 -Djava.library.path=C:\\Users\\carlo\\AppData\\Roaming\\.technic\\modpacks\\vanilla\\bin\\natives -Dfml.core.libraries.mirror=http://mirror.technicpack.net/Technic/lib/fml/%s -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -Dminecraft.applet.TargetDirectory=C:\\Users\\carlo\\AppData\\Roaming\\.technic\\modpacks\\vanilla -Duser.language=en -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump -cp C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\mojang\\netty\\1.6\\netty-1.6.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\mojang\\realms\\1.3.5\\realms-1.3.5.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\org\\apache\\commons\\commons-compress\\1.8.1\\commons-compress-1.8.1.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\org\\apache\\httpcomponents\\httpclient\\4.3.3\\httpclient-4.3.3.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\commons-logging\\commons-logging\\1.1.3\\commons-logging-1.1.3.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\org\\apache\\httpcomponents\\httpcore\\4.3.2\\httpcore-4.3.2.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\java3d\\vecmath\\1.3.1\\vecmath-1.3.1.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\net\\sf\\trove4j\\trove4j\\3.0.3\\trove4j-3.0.3.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\ibm\\icu\\icu4j-core-mojang\\51.2\\icu4j-core-mojang-51.2.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\net\\sf\\jopt-simple\\jopt-simple\\4.5\\jopt-simple-4.5.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\paulscode\\codecjorbis\\20101023\\codecjorbis-20101023.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\paulscode\\codecwav\\20101023\\codecwav-20101023.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\paulscode\\libraryjavasound\\20101123\\libraryjavasound-20101123.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\paulscode\\librarylwjglopenal\\20100824\\librarylwjglopenal-20100824.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\paulscode\\soundsystem\\20120107\\soundsystem-20120107.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\io\\netty\\netty-all\\4.0.10.Final\\netty-all-4.0.10.Final.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\google\\guava\\guava\\15.0\\guava-15.0.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\org\\apache\\commons\\commons-lang3\\3.1\\commons-lang3-3.1.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\commons-io\\commons-io\\2.4\\commons-io-2.4.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\commons-codec\\commons-codec\\1.9\\commons-codec-1.9.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\net\\java\\jinput\\jinput\\2.0.5\\jinput-2.0.5.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\net\\java\\jutils\\jutils\\1.0.0\\jutils-1.0.0.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\google\\code\\gson\\gson\\2.2.4\\gson-2.2.4.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\com\\mojang\\authlib\\1.5.21\\authlib-1.5.21.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\org\\apache\\logging\\log4j\\log4j-api\\2.0-beta9\\log4j-api-2.0-beta9.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\org\\apache\\logging\\log4j\\log4j-core\\2.0-beta9\\log4j-core-2.0-beta9.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\org\\lwjgl\\lwjgl\\lwjgl\\2.9.1\\lwjgl-2.9.1.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\org\\lwjgl\\lwjgl\\lwjgl_util\\2.9.1\\lwjgl_util-2.9.1.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\cache\\tv\\twitch\\twitch\\5.16\\twitch-5.16.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\modpacks\\vanilla\\bin\\modpack.jar;C:\\Users\\carlo\\AppData\\Roaming\\.technic\\modpacks\\vanilla\\bin\\minecraft.jar net.minecraft.client.main.Main --username "+username+" --version 1.7.10 --gameDir C:\\Users\\carlo\\AppData\\Roaming\\.technic\\modpacks\\vanilla --assetsDir C:\\Users\\carlo\\AppData\\Roaming\\.technic\\assets --assetIndex 1.7.10 --uuid 2bb4921358a5a2766c724833862256f1 --accessToken 5ce690d9e272428f9c9cff858dfaf6ad --userProperties {} --userType mojang");
    }
}
