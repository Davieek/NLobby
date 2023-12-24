package me.ninjak.nlobby.PlaceHolderApi.schedulers;

import me.ninjak.nlobby.NLobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import static me.ninjak.nlobby.PlaceHolderApi.Listener.onPlayerCommandPreprocessListener$Papi.isTrue;


public class Scheduler$Papi {
    private NLobby plugin;
    public Scheduler$Papi(NLobby plugin) {
        this.plugin = plugin;
    }

    public void startScheduler() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (isTrue) {
                        return;
                    }
                    plugin.getPlayerTags$Papi().refreshTag(player);
                }
            }
        }, 0L, 2 * 20);

    }
}
