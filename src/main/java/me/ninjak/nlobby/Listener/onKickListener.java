package me.ninjak.nlobby.Listener;

import me.ninjak.nlobby.NLobby;
import me.ninjak.nlobby.player.PlayerTags$2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class onKickListener implements Listener {
    private NLobby plugin;

    public onKickListener(NLobby plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        var player = (Player) event.getPlayer();
        var playerTag$2 = new PlayerTags$2();
        playerTag$2.removeTag(player);


    }
}
