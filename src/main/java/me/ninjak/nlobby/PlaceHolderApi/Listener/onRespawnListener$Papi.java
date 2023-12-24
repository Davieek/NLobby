package me.ninjak.nlobby.PlaceHolderApi.Listener;

import me.ninjak.nlobby.NLobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class onRespawnListener$Papi implements Listener {
    private NLobby plugin;

    public onRespawnListener$Papi(NLobby plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        var player = event.getPlayer();
        var playerTags = plugin.getPlayerTags$Papi();
        playerTags.createTag(player);
    }

}

