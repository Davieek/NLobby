package me.ninjak.nlobby.Listener;

import me.ninjak.nlobby.player.PlayerTags$2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onQuitListener$2 implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        var player = (Player) event.getPlayer();
        var playerTag$2 = new PlayerTags$2();
        playerTag$2.removeTag(player);

    }
}
