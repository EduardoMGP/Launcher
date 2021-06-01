package techcraftbrasil.gui;

import javafx.scene.text.Text;

public class UpdateRow {

    private String versao;
    private String data;
    private String noticia;
    private String username;

    public UpdateRow(String versao, String data, String noticia, String username) {
        this.versao = versao;
        this.data = data;
        Text text = new Text();
        text.setText(noticia);
        text.setWrappingWidth(100);
        this.noticia = text.getText();
        this.username = username;
    }

    public String getVersao() {
        return versao;
    }

    public String getData() {
        return data;
    }

    public String getNoticia() {
        return noticia;
    }

    public String getUsername() {
        return username;
    }
}
