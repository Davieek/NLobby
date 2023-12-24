package me.ninjak.nlobby.PlaceHolderApi.Listener;


import me.clip.placeholderapi.PlaceholderAPI;
import me.ninjak.nlobby.API.NPlaceHolder;
import me.ninjak.nlobby.ui.ActionBar;
import me.ninjak.nlobby.Utils.ChatUtils;
import me.ninjak.nlobby.NLobby;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;

public class onJoinListener$Papi implements Listener {
    private NLobby plugin;


    public onJoinListener$Papi(NLobby plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var fileManager = plugin.getFileManager();
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
                        if (player.hasPermission(joinPermission)) {
                            // send custom join message on chat
                            var chatJoinMessageActive = rankSection.getBoolean(rankKey + ".chat.enable");
                            if (chatJoinMessageActive) {
                                var joinMessage = rankSection.getString(rankKey + ".chat.joinMessage");
                                if (joinMessage == null)
                                    e.setJoinMessage("");
                                if (joinMessage != null) {
                                    joinMessage = NPlaceHolder.formatPlayerName(player, joinMessage);
                                    joinMessage = PlaceholderAPI.setPlaceholders(player, joinMessage);
                                    e.setJoinMessage(ChatUtils.fixColor(joinMessage));
                                }

                            }
                            // send custom join message on ActionBar, for all online players
                            var actionBarActive = rankSection.getBoolean(rankKey + ".ActionBar.enable");
                            if (actionBarActive) {
                                var playerList = plugin.getServer().getOnlinePlayers();
                                for (Player player1 : playerList) {
                                    var joinMessage = rankSection.getString(rankKey + ".ActionBar.onJoin.message");
                                    if (joinMessage != null) {
                                        joinMessage = NPlaceHolder.formatPlayerName(player, joinMessage);
                                        joinMessage = PlaceholderAPI.setPlaceholders(player, joinMessage);
                                        ActionBar.send(player1, ChatUtils.fixColor(joinMessage));
                                    }
                                }
                            }
                        } else {
                            e.setJoinMessage("");
                        }
                    }
                }
            }
        }

        // Effect on join the server
        if (player.hasPermission("nlobby.join.effect")) {
            var location = player.getLocation();
            var fireworks = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            var fireworksmeta = fireworks.getFireworkMeta();
            fireworksmeta.addEffect(FireworkEffect.builder().withColor(Color.WHITE).build());
            fireworks.setFireworkMeta(fireworksmeta);
        }


        // Create custom player tag
        var playerTags = plugin.getPlayerTags$Papi();
        playerTags.createTag(player);


        // send title for join player
        var TitleActive = config.getBoolean("Title.enable");
        if (TitleActive) {
            var lineOne = config.getString("Title.lineOne");
            var lineTwo = config.getString("Title.lineTwo");

            if (lineOne != null && lineTwo != null) {
                lineOne = NPlaceHolder.formatPlayerName(player, lineOne);
                lineTwo = NPlaceHolder.formatPlayerName(player, lineTwo);

                lineOne = PlaceholderAPI.setPlaceholders(player, lineOne);
                lineTwo = PlaceholderAPI.setPlaceholders(player, lineTwo);

                player.sendTitle(ChatUtils.fixColor(lineOne), ChatUtils.fixColor(lineTwo), 30, 8, 20);
            }

        }


    }
}
