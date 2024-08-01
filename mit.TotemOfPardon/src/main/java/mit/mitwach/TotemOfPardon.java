package mit.mitwach;

import org.bukkit.plugin.java.JavaPlugin;


public final class TotemOfPardon extends JavaPlugin {

    private ConfigClass configClass;
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new TotemListener(), this);
        this.configClass = new ConfigClass(this.getConfig());
        this.getCommand("totemofpardon").setExecutor(new TotemCommand());
        this.initCf();
        this.startMessage();
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    public void startMessage() {
        getLogger().info("==================================================");
        getLogger().info("||                                              ||");
        getLogger().info("||             Plugin made by 0nyc              ||");
        getLogger().info("||            Every problem and bug             ||");
        getLogger().info("||             report on discord:               ||");
        getLogger().info("||        https://discord.gg/kZUJjD5297         ||");
        getLogger().info("||                                              ||");
        getLogger().info("||        Plugin name -> TotemOfPardon          ||");
        getLogger().info("||        Plugin version -> 1.0                 ||");
        getLogger().info("||                                              ||");
        getLogger().info("==================================================");
    }

    public void initCf() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
    }
}