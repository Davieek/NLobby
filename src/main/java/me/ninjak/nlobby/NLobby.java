package me.ninjak.nlobby;

import me.ninjak.nlobby.Listener.*;
import me.ninjak.nlobby.Manager.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NLobby extends JavaPlugin {

    private FileManager fileManager = new FileManager();
    private HidePlayerNickName hidePlayerNickName;


    @Override
    public void onEnable() {
        fileManager.setPlugin(this);
        fileManager.createConfig("config");
        fileManager.createConfig("Chat/chatConfig");
        fileManager.createConfig("ranks");
        hidePlayerNickName = new HidePlayerNickName(this);

        getServer().getPluginManager().registerEvents(new onJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new onQuitListener(), this);
        getServer().getPluginManager().registerEvents(new onKickListener(), this);
        getServer().getPluginManager().registerEvents(new onDeathListener(), this);
        getServer().getPluginManager().registerEvents(new onRespawnListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public FileManager getFileManager() {
        return fileManager;
    }
    public HidePlayerNickName getHidePlayerNickName() {
        return hidePlayerNickName;
    }
}