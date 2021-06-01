package techcraftbrasil.json.forge;

import java.util.Date;
import java.util.List;

public class RootForge {
    public String id;
    public Date time;
    public Date releaseTime;
    public String type;
    public String minecraftArguments;
    public String mainClass;
    public double minimumLauncherVersion;
    public String assets;
    public String inheritsFrom;
    public String jar;
    public List<ForgeLibrary> libraries;
}