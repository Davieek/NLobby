package me.ninjak.nlobby;

import me.ninjak.nlobby.Listener.onJoinListener;
import me.ninjak.nlobby.Manager.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NLobby extends JavaPlugin {

    private FileManager fileManager = new FileManager();


    @Override
    public void onEnable() {
        fileManager.setPlugin(this);
        fileManager.createConfig("config");
        fileManager.createConfig("chatConfig");
        fileManager.createConfig("CustomJoinMessage");
        getServer().getPluginManager().registerEvents(new onJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public FileManager getFileManager() {
        return fileManager;
    }
}