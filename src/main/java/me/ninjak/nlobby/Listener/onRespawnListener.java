package me.ninjak.nlobby.Listener;

import me.ninjak.nlobby.ChatUtils;
import me.ninjak.nlobby.Manager.FileManager;
import me.ninjak.nlobby.NLobby;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.ninjak.nlobby.Listener.onJoinListener.getArmorStands;
import static me.ninjak.nlobby.Listener.onJoinListener.getSilverFish;

public class onRespawnListener implements Listener {
    private NLobby plugin;

    public onRespawnListener(NLobby plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        var player = event.getPlayer();
        var fileManager = plugin.getFileManager();
        var hidePlayerNickName = plugin.getHidePlayerNickName();
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

                            var armorStands = getArmorStands();
                            var silverFish = getSilverFish();
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
    }

}
