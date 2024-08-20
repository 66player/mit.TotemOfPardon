package mit.mitwach;

import org.bukkit.configuration.file.FileConfiguration;


public class ConfigClass {

    public FileConfiguration cf;

    public ConfigClass(FileConfiguration cf) {
        this.cf = cf;
    }

    public FileConfiguration getCf() {
        return cf;
    }
    public void reloadCf() {
        (TotemOfPardon.getPlugin(TotemOfPardon.class)).reloadConfig();
        this.cf = (TotemOfPardon.getPlugin(TotemOfPardon.class)).getConfig();
    }
}
