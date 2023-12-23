package me.ninjak.nlobby.Listener;

import me.ninjak.nlobby.ActionBar;
import me.ninjak.nlobby.ChatUtils;
import me.ninjak.nlobby.Manager.FileManager;
import me.ninjak.nlobby.NLobby;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class onJoinListener implements Listener {
    private NLobby plugin;
    private static Map<UUID, List<ArmorStand>> armorStands = new HashMap<>();
    private static Map<UUID, List<Silverfish>> silverFish = new HashMap<>();

    public onJoinListener(NLobby plugin) {
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

        // Effect on join the server
        if (player.hasPermission("nlobby.join.effect")) {
            var location = player.getLocation();
            var fireworks = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            var fireworksmeta = fireworks.getFireworkMeta();
            fireworksmeta.addEffect(FireworkEffect.builder().withColor(Color.WHITE).build());
            fireworks.setFireworkMeta(fireworksmeta);
        }

        var configRank = fileManager.getConfig("ranks");
        var rankSection = configRank.getConfigurationSection("Ranks");
        if (rankSection != null) {
            for (String rankKey : rankSection.getKeys(false)) {
                if (player.hasPermission(rankKey)) {
                    var tagActive = rankSection.getBoolean(rankKey + ".tag.enable");
                    if (tagActive) {
                        var lineOne = rankSection.getString(rankKey + ".tag.lineOne");
                        var lineTwo = rankSection.getString(rankKey + ".tag.lineTwo");

                        if (lineTwo != null && lineOne != null) {
                            var hidePlayerNickName = plugin.getHidePlayerNickName();
                            hidePlayerNickName.hidePlayerName(player);
                            var playerLocation = player.getEyeLocation().add(0, 0.5, 0);
                            var armorStand = (ArmorStand) playerLocation.getWorld().spawn(playerLocation, ArmorStand.class);
                            armorStand.setGravity(false);
                            armorStand.setCanPickupItems(false);
                            armorStand.setCustomName(ChatUtils.fixColor(String.format(lineTwo, player.getDisplayName())));
                            armorStand.setCustomNameVisible(true);
                            armorStand.setVisible(false);
                            armorStand.setInvulnerable(true);
                            armorStand.setSilent(true);
                            armorStand.setSmall(true);
                            armorStand.setAI(false);
                            armorStand.setCollidable(false);
                            armorStand.setMarker(true);

                            EntityType type = EntityType.ARMOR_STAND;
                            ArmorStand armorStand2 = (ArmorStand) armorStand.getWorld().spawnEntity(armorStand.getLocation().add(0, 0.25, 0), type);
                            armorStand2.setGravity(false);
                            armorStand2.setCustomName(ChatUtils.fixColor(lineOne));
                            armorStand2.setCustomNameVisible(true);
                            armorStand2.setInvisible(true);
                            armorStand2.setSmall(true);
                            armorStand2.setMarker(true);
                            armorStand2.setInvulnerable(true);

                            EntityType type2 = EntityType.SILVERFISH;
                            Silverfish silverfish = (Silverfish) armorStand.getWorld().spawnEntity(armorStand.getLocation(), type2);
                            silverfish.setInvulnerable(true);
                            silverfish.setInvisible(true);
                            silverfish.setSilent(true);
                            silverfish.setPassenger(armorStand2);
                            armorStand.addPassenger(silverfish);
                            player.addPassenger(armorStand);

                            armorStands.putIfAbsent(player.getUniqueId(), new ArrayList<>());
                            armorStands.get(player.getUniqueId()).add(armorStand);
                            armorStands.get(player.getUniqueId()).add(armorStand2);
                            silverFish.putIfAbsent(player.getUniqueId(), new ArrayList<>());
                            silverFish.get(player.getUniqueId()).add(silverfish);
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
    public static Map<UUID, List<ArmorStand>> getArmorStands() {
        return armorStands;
    }
    public static Map<UUID, List<Silverfish>> getSilverFish() {
        return silverFish;
    }
}
