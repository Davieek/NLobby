package me.ninjak.nlobby;

import me.ninjak.nlobby.Manager.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NLobby extends JavaPlugin {

    private FileManager fileManager = new FileManager(this);


    @Override
    public void onEnable() {
        fileManager.createConfig("config.yml", "default");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}