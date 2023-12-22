package me.ninjak.nlobby.Listener;

import me.ninjak.nlobby.ActionBar;
import me.ninjak.nlobby.ChatUtils;
import me.ninjak.nlobby.Manager.FileManager;
import me.ninjak.nlobby.NLobby;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class onJoinListener implements Listener {
    private NLobby plugin;

    public onJoinListener(NLobby plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var fileManager = plugin.getFileManager();
        Bukkit.broadcastMessage("chuj");

        // config file configuration
        var config = fileManager.getConfig("config");

        var player = (Player) e.getPlayer();
        var permission = "nlobby.admin";

        // change player gamemode to adventure if player join the server and don't have permissions
        // if admins have nlobby.admin.gamemode.bypass permission, it does not change the gamemode
        var gamemodeActive = config.getBoolean("Player.changeGameMode.enable");
        if (gamemodeActive) {
            var playerGamemode = player.getGameMode();
            var gamemode = config.getString("Player.changeGameMode.gamemode");
            // All minecraft gamemode's
            var gamemodeList = new ArrayList<String>();
            gamemodeList.add("ADVENTURE");
            gamemodeList.add("CREATIVE");
            gamemodeList.add("SURVIVAL");
            gamemodeList.add("SPECTATOR");

            // check gamemode's from gamemode list with config String value
            // if gamemode is not a valid, it change value in config file to the default
            for (String gm : gamemodeList) {
                if (!Objects.equals(gamemode, gm)) {
                    System.out.println("N ");
                    config.set("Player.changeGameMode.gamemode", "ADVENTURE");
                }
            }
            // if gamemode's is correct, this change gamemode the player
            if (playerGamemode != GameMode.valueOf(gamemode)) {
                var playerPermBypass = permission + ".gamemode.bypass";
                if (!player.hasPermission(playerPermBypass)) {
                    player.setGameMode(GameMode.valueOf(gamemode));
                }
            }
        }

        // Custom join message for rank/permission on chat/actionbar
        var customJoinMessageActive = config.getBoolean("customJoinMessage.enable");
        if (customJoinMessageActive) {
            // get rankSection from config file
            var rankSection = config.getConfigurationSection("customJoinMessage");
            if (rankSection != null) {
                for (String rankKey : rankSection.getKeys(false)) {

                    var joinPermission = rankSection.getString(rankKey + ".permission");
                    if (joinPermission != null) {
                        if (joinPermission.equals("default")) {
                            var chatJoinMessageActive = rankSection.getBoolean(rankKey + ".chat.enable");
                            if (chatJoinMessageActive) {
                                var joinMessage = rankSection.getString(rankKey + ".chat.joinMessage");
                                if (joinMessage != null)
                                    e.setJoinMessage(ChatUtils.fixColor(String.format(joinMessage, player.getDisplayName())));
                            }
                            // send custom join message on ActionBar, for all online players
                            var actionBarActive = rankSection.getBoolean(rankKey + ".ActionBar.enable");
                            if (actionBarActive) {
                                var playerList = plugin.getServer().getOnlinePlayers();
                                for (Player player1 : playerList) {
                                    var joinMessage = rankSection.getString(rankKey + ".ActionBar.onJoin.message");
                                    if (joinMessage != null)
                                        ActionBar.send(player1, ChatUtils.fixColor(String.format(joinMessage, player.getDisplayName())));
                                }
                            }
                        }
                        if (player.hasPermission(joinPermission)) {
                            // send custom join message on chat
                            var chatJoinMessageActive = rankSection.getBoolean(rankKey + ".chat.enable");
                            if (chatJoinMessageActive) {
                                var joinMessage = rankSection.getString(rankKey + ".chat.joinMessage");
                                if (joinMessage != null)
                                    e.setJoinMessage(ChatUtils.fixColor(String.format(joinMessage, player.getDisplayName())));
                            }
                            // send custom join message on ActionBar, for all online players
                            var actionBarActive = rankSection.getBoolean(rankKey + ".ActionBar.enable");
                            if (actionBarActive) {
                                var playerList = plugin.getServer().getOnlinePlayers();
                                for (Player player1 : playerList) {
                                    var joinMessage = rankSection.getString(rankKey + ".ActionBar.onJoin.message");
                                    if (joinMessage != null)
                                        ActionBar.send(player1, ChatUtils.fixColor(String.format(joinMessage, player.getDisplayName())));
                                }
                            }
                        }
                    }
                }
            }
        }

//        // player teleport to spawn
//        var spawnTeleportActive = config.getBoolean("Player.onJoin.teleportToSpawn");
//        if (spawnTeleportActive) {
//            var worldName = config.getString("World.spawn.worldName");
//            var worldX = config.getInt("World.spawn.x");
//            var worldY = config.getInt("World.spawn.y");
//            var worldz = config.getInt("World.spawn.z");
//            if (worldName != null) {
//                var world = Bukkit.getWorld(worldName);
//                player.teleport(new Location(world, worldX, worldY, worldz));
//            }
//        }




    }
}
