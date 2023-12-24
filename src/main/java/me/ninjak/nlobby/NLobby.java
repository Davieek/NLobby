package me.ninjak.nlobby;


import me.ninjak.nlobby.Listener.*;
import me.ninjak.nlobby.PlaceHolderApi.Listener.*;
import me.ninjak.nlobby.Listener.world.onPluginEnableListener;
import me.ninjak.nlobby.Listener.world.onWorldLoadListener;
import me.ninjak.nlobby.Manager.FileManager;
import me.ninjak.nlobby.PlaceHolderApi.player.PlayerTags$Papi;
import me.ninjak.nlobby.PlaceHolderApi.schedulers.Scheduler$Papi;
import me.ninjak.nlobby.Utils.ChatUtils;
import me.ninjak.nlobby.player.PlayerTags;
import me.ninjak.nlobby.player.PlayerTags$2;
import me.ninjak.nlobby.schedulers.Scheduler;
import me.ninjak.nlobby.schedulers.Scheduler$NApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NLobby extends JavaPlugin {

    private FileManager fileManager = new FileManager();
    private PlayerTags playerTags;
    private PlayerTags$Papi playerTags$Papi;
    private Scheduler scheduler;
    private Scheduler$NApi scheduler$NApi;
    private Scheduler$Papi scheduler$Papi;

    @Override
    public void onEnable() {
        fileManager.setPlugin(this);
        fileManager.createConfig("config");
        fileManager.createConfig("Chat/chatConfig");
        fileManager.createConfig("ranks");
        scheduler = new Scheduler(this);

        scheduler.startScheduler();


        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage(ChatUtils.fixColor("&4Z Placeholder"));
            playerTags$Papi = new PlayerTags$Papi(this);
            scheduler$Papi = new Scheduler$Papi(this);
            scheduler$Papi.startScheduler();
            getServer().getPluginManager().registerEvents(new onJoinListener$Papi(this), this);
            getServer().getPluginManager().registerEvents(new onQuitListener$Papi(this), this);
            getServer().getPluginManager().registerEvents(new onLoadListener$Papi(this), this);
            getServer().getPluginManager().registerEvents(new onRespawnListener$Papi(this), this);
            getServer().getPluginManager().registerEvents(new onPlayerCommandPreprocessListener$Papi(this), this);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatUtils.fixColor("&4bez Placeholder"));
            playerTags = new PlayerTags(this);
            scheduler$NApi = new Scheduler$NApi(this);
            scheduler$NApi.startShedulers();
            getServer().getPluginManager().registerEvents(new onJoinListener(this), this);
            getServer().getPluginManager().registerEvents(new onQuitListener(this), this);
            getServer().getPluginManager().registerEvents(new onLoadListener(this), this);
            getServer().getPluginManager().registerEvents(new onRespawnListener(this), this);
            getServer().getPluginManager().registerEvents(new onPlayerCommandPreprocessListener(this), this);
        }
        getServer().getPluginManager().registerEvents(new onWorldLoadListener(), this);
        getServer().getPluginManager().registerEvents(new onPluginEnableListener(this), this);
        getServer().getPluginManager().registerEvents(new onDamageListener(), this);
        getServer().getPluginManager().registerEvents(new onDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new onKickListener(this), this);
        getServer().getPluginManager().registerEvents(new onQuitListener$2(), this);
        getServer().getPluginManager().registerEvents(new onJoinListener$2(), this);

    }

    @Override
    public void onDisable() {
        var players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            var playerTag = new PlayerTags$2();
            playerTag.removeTag(player);
        }

    }
    public FileManager getFileManager() {
        return fileManager;
    }
    public PlayerTags getPlayerTags() {
        return playerTags;
    }
    public PlayerTags$Papi getPlayerTags$Papi() {
        return playerTags$Papi;
    }

}