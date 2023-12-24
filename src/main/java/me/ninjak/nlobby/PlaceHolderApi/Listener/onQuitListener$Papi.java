package me.ninjak.nlobby.PlaceHolderApi.Listener;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ninjak.nlobby.API.NPlaceHolder;
import me.ninjak.nlobby.player.PlayerTags$2;
import me.ninjak.nlobby.ui.ActionBar;
import me.ninjak.nlobby.Utils.ChatUtils;
import me.ninjak.nlobby.NLobby;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class onQuitListener$Papi implements Listener {
    private NLobby plugin;

    public onQuitListener$Papi(NLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        var player = event.getPlayer();
        if (player.hasPermission("nlobby.quit.effect")) {
            player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 25);
        }

        var fileManager = plugin.getFileManager();
        // config file configuration
        var config = fileManager.getConfig("config");

        var customJoinMessageActive = config.getBoolean("customJoinMessage.enable");
        if (customJoinMessageActive) {
            // get rankSection from config file
            var rankSection = config.getConfigurationSection("customJoinMessage");
            if (rankSection != null) {
                for (String rankKey : rankSection.getKeys(false)) {

                    var joinPermission = rankSection.getString(rankKey + ".permission");
                    if (joinPermission != null) {
                        if (player.hasPermission(joinPermission)) {
                            // send custom join message on chat
                            var chatJoinMessageActive = rankSection.getBoolean(rankKey + ".chat.enable");
                            if (chatJoinMessageActive) {
                                var quitMessage = rankSection.getString(rankKey + ".chat.quitMessage");
                                if (quitMessage == null)
                                    event.setQuitMessage("");
                                if (quitMessage != null) {
                                    quitMessage = NPlaceHolder.formatPlayerName(player, quitMessage);
                                    quitMessage = PlaceholderAPI.setPlaceholders(player, quitMessage);
                                    event.setQuitMessage(ChatUtils.fixColor(quitMessage));
                                }
                            }
                            // send custom join message on ActionBar, for all online players
                            var actionBarActive = rankSection.getBoolean(rankKey + ".ActionBar.enable");
                            if (actionBarActive) {
                                var playerList = plugin.getServer().getOnlinePlayers();
                                for (Player player1 : playerList) {
                                    var quitMessage = rankSection.getString(rankKey + ".ActionBar.onQuit.message");
                                    if (quitMessage != null) {
                                        quitMessage = NPlaceHolder.formatPlayerName(player, quitMessage);
                                        quitMessage = PlaceholderAPI.setPlaceholders(player, quitMessage);
                                        ActionBar.send(player1, ChatUtils.fixColor(quitMessage));
                                    }

                                }
                            }
                        } else {
                            event.setQuitMessage("");
                        }
                    }
                }
            }
        }

    }

}
