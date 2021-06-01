package techcraftbrasil.json.mojang.libraries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Root{
    public Arguments arguments;
    public AssetIndex assetIndex;
    public String assets;
    public int complianceLevel;
    public Downloads downloads;
    public String id;
    public JavaVersion javaVersion;
    public List<Library> libraries = new ArrayList<>();
    public Logging logging;
    public String mainClass;
    public int minimumLauncherVersion;
    public Date releaseTime;
    public Date time;
    public String type;
}
