package me.ninjak.nlobby.Listener.world;

import me.ninjak.nlobby.NLobby;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class onPluginEnableListener implements Listener {
    private NLobby plugin;
    public onPluginEnableListener(NLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        if (event.getPlugin() == plugin) {
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    for(World world : Bukkit.getWorlds()){
                        world.setTime(5000);
                    }
                }
            }, 0L, 100L);
        }

    }
}
