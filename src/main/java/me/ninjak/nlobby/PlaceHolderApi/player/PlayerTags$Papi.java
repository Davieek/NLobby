package me.ninjak.nlobby.PlaceHolderApi.player;


import me.clip.placeholderapi.PlaceholderAPI;
import me.ninjak.nlobby.API.NPlaceHolder;
import me.ninjak.nlobby.NLobby;
import me.ninjak.nlobby.Utils.ChatUtils;
import me.ninjak.nlobby.player.PlayerTags$2;
import me.ninjak.nlobby.player.PlayerTagsList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.*;

import static me.ninjak.nlobby.PlaceHolderApi.Listener.onPlayerCommandPreprocessListener$Papi.isTrue;
import static me.ninjak.nlobby.player.PlayerTagsList.getArmorStands;
import static me.ninjak.nlobby.player.PlayerTagsList.getRabbits;
import static org.bukkit.Bukkit.getServer;


public class PlayerTags$Papi {
    private final NLobby plugin;
    public PlayerTags$Papi(NLobby plugin) {
        this.plugin = plugin;
    }

    private final Map<UUID, List<ArmorStand>> armorStands = getArmorStands();

    private final Map<UUID, List<Rabbit>> rabbits = getRabbits();

    private final PlayerTags$2 playerTag$2 = new PlayerTags$2();
    public void createTag(Player player) {
        var fileManager = plugin.getFileManager();

        var configRank = fileManager.getConfig("ranks");
        var rankSection = configRank.getConfigurationSection("Ranks");
        if (rankSection != null) {
            for (String rankKey : rankSection.getKeys(false)) {
                if (player.hasPermission("nlobby.rank." + rankKey)) {
                    var tagActive = rankSection.getBoolean(rankKey + ".tag.enable");
                    if (tagActive) {
                        var lineOne = rankSection.getString(rankKey + ".tag.lineOne");
                        var lineTwo = rankSection.getString(rankKey + ".tag.lineTwo");

                        if (lineTwo != null && lineOne != null) {
                            var playerLocation = player.getEyeLocation().add(0, 0.5, 0);
                            var armorStand = (ArmorStand) playerLocation.getWorld().spawn(playerLocation, ArmorStand.class);
                            armorStand.setGravity(false);
                            armorStand.setCanPickupItems(false);
                            lineTwo = ChatUtils.fixColor(NPlaceHolder.formatPlayerName(player, lineTwo));
                            lineTwo = PlaceholderAPI.setPlaceholders(player, lineTwo);
                            armorStand.setCustomName(lineTwo);
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
                            lineOne = ChatUtils.fixColor(NPlaceHolder.formatPlayerName(player, lineOne));
                            lineOne = PlaceholderAPI.setPlaceholders(player, lineOne);
                            armorStand2.setCustomName(lineOne);
                            armorStand2.setCustomNameVisible(true);
                            armorStand2.setInvisible(true);
                            armorStand.setVisible(false);
                            armorStand2.setSmall(true);
                            armorStand2.setMarker(true);
                            armorStand2.setInvulnerable(true);

                            if (player.getDisplayName().equals("_Ninjak")) {
                                Location headLocation = armorStand2.getLocation();
                                Location itemLocation = headLocation.clone().add(0, 0.5, 0);

                                var item = headLocation.getWorld().dropItem(itemLocation, new ItemStack(Material.BUBBLE_CORAL));
                                item.setGravity(false);
                                item.setPickupDelay(Integer.MAX_VALUE);
                                item.setTicksLived(Integer.MAX_VALUE);

                                var items = PlayerTagsList.getItem();
                                items.putIfAbsent(player.getUniqueId(), new ArrayList<>());
                                items.get(player.getUniqueId()).add(item);
                            }

                            EntityType type2 = EntityType.RABBIT;
                            Rabbit rabbit = (Rabbit) armorStand.getWorld().spawnEntity(armorStand.getLocation(), type2);
                            rabbit.setInvulnerable(true);
                            rabbit.setInvisible(true);
                            rabbit.setSilent(true);
                            rabbit.setCustomName(player.getDisplayName());
                            rabbit.setCustomNameVisible(false);
                            rabbit.setBaby();
                            rabbit.setPassenger(armorStand2);
                            armorStand.addPassenger(rabbit);
                            player.addPassenger(armorStand);

                            player.hideEntity(plugin, armorStand2);
                            player.hideEntity(plugin, armorStand);
                            player.hideEntity(plugin, rabbit);


                            armorStands.putIfAbsent(player.getUniqueId(), new ArrayList<>());
                            armorStands.get(player.getUniqueId()).add(armorStand);
                            armorStands.get(player.getUniqueId()).add(armorStand2);
                            rabbits.putIfAbsent(player.getUniqueId(), new ArrayList<>());
                            rabbits.get(player.getUniqueId()).add(rabbit);
                            return;
                        }

                    }
                }
                if (rankKey.equals("default")) {
                    if (armorStands.containsKey(player.getUniqueId()) || rabbits.containsKey(player.getUniqueId())) {
                        return;
                    }
                    if (armorStands.get(player.getUniqueId()) != null || rabbits.get(player.getUniqueId()) != null) {
                        return;
                    }
                    var tagActive = rankSection.getBoolean(rankKey + ".tag.enable");
                    if (tagActive) {
                        var lineOne = rankSection.getString(rankKey + ".tag.lineOne");
                        var lineTwo = rankSection.getString(rankKey + ".tag.lineTwo");

                        if (lineTwo != null && lineOne != null) {
                            var playerLocation = player.getEyeLocation().add(0, 0.5, 0);
                            var armorStand = (ArmorStand) playerLocation.getWorld().spawn(playerLocation, ArmorStand.class);
                            armorStand.setGravity(false);
                            armorStand.setCanPickupItems(false);
                            lineTwo = ChatUtils.fixColor(NPlaceHolder.formatPlayerName(player, lineTwo));
                            lineTwo = PlaceholderAPI.setPlaceholders(player, lineTwo);
                            armorStand.setCustomName(lineTwo);
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
                            lineOne = ChatUtils.fixColor(NPlaceHolder.formatPlayerName(player, lineOne));
                            lineOne = PlaceholderAPI.setPlaceholders(player, lineOne);
                            armorStand2.setCustomName(lineOne);
                            armorStand2.setCustomNameVisible(true);
                            armorStand2.setInvisible(true);
                            armorStand2.setSmall(true);
                            armorStand2.setMarker(true);
                            armorStand2.setInvulnerable(true);

                            if (player.getDisplayName().equals("_Ninjak")) {
                                Location headLocation = armorStand2.getLocation();
                                Location itemLocation = headLocation.clone().add(0, 0.5, 0);

                                var item = headLocation.getWorld().dropItem(itemLocation, new ItemStack(Material.BUBBLE_CORAL));
                                item.setGravity(false);
                                item.setPickupDelay(Integer.MAX_VALUE);
                                item.setTicksLived(Integer.MAX_VALUE);

                                var items = PlayerTagsList.getItem();
                                items.putIfAbsent(player.getUniqueId(), new ArrayList<>());
                                items.get(player.getUniqueId()).add(item);
                            }

                            EntityType type2 = EntityType.RABBIT;
                            Rabbit rabbit = (Rabbit) armorStand.getWorld().spawnEntity(armorStand.getLocation(), type2);
                            rabbit.setInvulnerable(true);
                            rabbit.setInvisible(true);
                            rabbit.setSilent(true);
                            rabbit.setCustomName(player.getDisplayName());
                            rabbit.setCustomNameVisible(false);
                            rabbit.setBaby();
                            rabbit.setPassenger(armorStand2);
                            armorStand.addPassenger(rabbit);
                            player.addPassenger(armorStand);


                            player.hideEntity(plugin, armorStand2);
                            player.hideEntity(plugin, armorStand);
                            player.hideEntity(plugin, rabbit);

                            armorStands.putIfAbsent(player.getUniqueId(), new ArrayList<>());
                            armorStands.get(player.getUniqueId()).add(armorStand);
                            armorStands.get(player.getUniqueId()).add(armorStand2);
                            rabbits.putIfAbsent(player.getUniqueId(), new ArrayList<>());
                            rabbits.get(player.getUniqueId()).add(rabbit);

                        }

                    }

                }

            }
        }


    }
    public void refreshTag(Player player) {
        var fileManager = plugin.getFileManager();
        var configRank = fileManager.getConfig("ranks");
        var rankSection = configRank.getConfigurationSection("Ranks");
        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        var test = isTrue;
        if (test) {
            return;
        }
        if (rankSection != null) {
            for (String rankKey : rankSection.getKeys(false)) {
                if (player.hasPermission("nlobby.rank." + rankKey)) {
                    var tagActive = rankSection.getBoolean(rankKey + ".tag.enable");
                    if (player.getPassengers().isEmpty()) {
                        if (armorStands.containsKey(player.getUniqueId()) || rabbits.containsKey(player.getUniqueId())) {
                            playerTag$2.removeTag(player);
                            createTag(player);
                            return;
                        } else {
                            createTag(player);
                        }
                    }
                    if (tagActive) {
                        var lineOne = rankSection.getString(rankKey + ".tag.lineOne");
                        var lineTwo = rankSection.getString(rankKey + ".tag.lineTwo");


                        if (lineTwo != null && lineOne != null) {
                            List<ArmorStand> playerArmorStands = armorStands.get(player.getUniqueId());
                            if (playerArmorStands == null) {
                                return;
                            }
                            if (playerArmorStands.size() >= 2) {
                                lineTwo = ChatUtils.fixColor(NPlaceHolder.formatPlayerName(player, lineTwo));
                                lineTwo = PlaceholderAPI.setPlaceholders(player, lineTwo);
                                playerArmorStands.get(0).setCustomName(lineTwo);
                                lineOne = ChatUtils.fixColor(NPlaceHolder.formatPlayerName(player, lineOne));
                                lineOne = PlaceholderAPI.setPlaceholders(player, lineOne);
                                playerArmorStands.get(1).setCustomName(lineOne);
                                return;
                            }
                        }
                    }
                }
                if (rankKey.equals("default")) {
                    var tagActive = rankSection.getBoolean(rankKey + ".tag.enable");
                    if (player.getPassengers().isEmpty()) {
                        if (armorStands.containsKey(player.getUniqueId()) || rabbits.containsKey(player.getUniqueId())) {
                            playerTag$2.removeTag(player);
                            createTag(player);
                            return;
                        } else {
                            createTag(player);
                            return;
                        }
                    }
                    if (tagActive) {
                        var lineOne = rankSection.getString(rankKey + ".tag.lineOne");
                        var lineTwo = rankSection.getString(rankKey + ".tag.lineTwo");

                        if (lineTwo != null && lineOne != null) {
                            List<ArmorStand> playerArmorStands = armorStands.get(player.getUniqueId());
                            if (playerArmorStands.size() >= 2) {
                                lineTwo = ChatUtils.fixColor(NPlaceHolder.formatPlayerName(player, lineTwo));
                                lineTwo = PlaceholderAPI.setPlaceholders(player, lineTwo);
                                playerArmorStands.get(0).setCustomName(lineTwo);
                                lineOne = ChatUtils.fixColor(NPlaceHolder.formatPlayerName(player, lineOne));
                                lineOne = PlaceholderAPI.setPlaceholders(player, lineOne);
                                playerArmorStands.get(1).setCustomName(lineOne);
                            }
                        }
                    }
                }
            }
        }
    }



}
