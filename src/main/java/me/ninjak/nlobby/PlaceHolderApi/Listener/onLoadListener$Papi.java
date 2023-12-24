package me.ninjak.nlobby.PlaceHolderApi.Listener;

import me.ninjak.nlobby.NLobby;
import me.ninjak.nlobby.player.PlayerTags$2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class onLoadListener$Papi implements Listener {

    private NLobby plugin;

    public onLoadListener$Papi(NLobby plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onServerLoaded(ServerLoadEvent event) {
        var players = Bukkit.getOnlinePlayers();

        for (Player player : players) {
            var playerTags = plugin.getPlayerTags$Papi();
            var playerTag$2 = new PlayerTags$2();
            playerTag$2.removeTag(player);
            playerTags.createTag(player);
        }
    }
}
