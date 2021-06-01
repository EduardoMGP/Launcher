package techcraftbrasil.json.solder;

import java.util.ArrayList;
import java.util.List;

public class ModpackDetails {

    private String minecraft;
    private List<Mods> mods = new ArrayList<>();

    public String getMinecraft() {
        return minecraft;
    }

    public void setMinecraft(String minecraft) {
        this.minecraft = minecraft;
    }

    public List<Mods> getMods() {
        return mods;
    }

    public void setMods(List<Mods> mods) {
        this.mods = mods;
    }
}
