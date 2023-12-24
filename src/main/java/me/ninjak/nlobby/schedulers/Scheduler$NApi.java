package me.ninjak.nlobby.schedulers;

import me.ninjak.nlobby.NLobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Scheduler$NApi {
    private NLobby plugin;
    public Scheduler$NApi(NLobby plugin) {
        this.plugin = plugin;
    }
    public void startShedulers() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    plugin.getPlayerTags().refreshTag(player);
                }
            }
        }, 0L, 2 * 20);
    }
}
