package me.ninjak.nlobby.Listener;

import me.ninjak.nlobby.NLobby;
import me.ninjak.nlobby.player.PlayerTags$2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onDeathListener implements Listener {
    private NLobby plugin;
    public onDeathListener(NLobby plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        var entity = event.getEntity();
        if (entity instanceof Player) {
            var player = (Player) entity;
            var playerTag$2 = new PlayerTags$2();
            playerTag$2.removeTag(player);
        }
    }
}
