package me.ninjak.nlobby.Listener;

import me.ninjak.nlobby.ActionBar;
import me.ninjak.nlobby.Manager.FileManager;
import me.ninjak.nlobby.NLobby;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class onJoinListener implements Listener {
    private NLobby plugin = NLobby.getPlugin(NLobby.class);

    private FileManager fileManager = new FileManager(plugin);
    private void onJoinListener(@NotNull PlayerJoinEvent e) {
        // config file configuration
        var config = fileManager.getConfig("config");

        var player = (Player) e.getPlayer();
        var permission = "nlobby.admin";

        // change player gamemode to adventure if player don't have permission
        var gamemodeActive = config.getBoolean("Player.changeGameMode.enable");
        if (gamemodeActive) {
            var playerGamemode = player.getGameMode();
            var gamemode = config.getString("Player.changeGameMode.gamemode");
            // Minecraft gamemode List
            var gamemodeList = new ArrayList<String>();
            gamemodeList.add("ADVENTURE");
            gamemodeList.add("CREATIVE");
            gamemodeList.add("SURVIVAL");
            gamemodeList.add("SPECTATOR");

            // check gamemode from config with gamemode list
            for (String gm : gamemodeList) {
                if (!Objects.equals(gamemode, gm)) {
                    System.out.println("N ");
                    config.set("Player.changeGameMode.gamemode", "ADVENTURE");
                }
            }
            // change player gamemode
            if (playerGamemode != GameMode.valueOf(gamemode)) {
                var playerPermBypass = permission + ".gamemode.bypass";
                if (!player.hasPermission(playerPermBypass)) {
                    player.setGameMode(GameMode.valueOf(gamemode));
                }
            }
        }

        // player custom join message
        var customJoinMessageActive = config.getBoolean("customJoinMessage.enable");
        if (customJoinMessageActive) {
            // get all rank from config
            var rankSection = config.getConfigurationSection("customJoinMessage");
            if (rankSection != null) {
                for (String rankKey : rankSection.getKeys(false)) {

                    var joinPermission = rankSection.getString(rankKey + ".permission");
                    if (joinPermission != null) {
                        if (player.hasPermission(joinPermission)) {
                            // send join message on chat
                            var chatJoinMessageActive = rankSection.getBoolean(rankKey + ".chat.enable");
                            if (chatJoinMessageActive) {
                                var joinMessage = rankSection.getString(rankKey + ".chat.joinMessage");
                                if (joinMessage != null)
                                    e.setJoinMessage(String.format(joinMessage, player.getDisplayName()));
                            }
                            // send join message on ActionBar for all online player
                            var actionBarActive = rankSection.getBoolean(rankKey + ".ActionBar.enable");
                            if (actionBarActive) {
                                var playerList = Bukkit.getOnlinePlayers();
                                for (Player player1 : playerList) {
                                    var joinMessage = rankSection.getString(rankKey + ".ActionBar.onJoin.message");
                                    if (joinMessage != null)
                                        ActionBar.send(player1, joinMessage);
                                }
                            }
                        }
                    }

                }
            }
        }

        // player teleport to spawn






    }
}
