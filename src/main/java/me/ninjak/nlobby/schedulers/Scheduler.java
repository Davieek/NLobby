package me.ninjak.nlobby.schedulers;

import me.ninjak.nlobby.NLobby;
import me.ninjak.nlobby.player.PlayerTagsList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.ninjak.nlobby.player.PlayerTagsList.getArmorStands;

public class Scheduler {
    private NLobby plugin;
    public Scheduler(NLobby plugin) {
        this.plugin = plugin;
    }

    public void startScheduler() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();


        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                var players = Bukkit.getOnlinePlayers();
                for(Player player : players) {
                    Map<UUID, List<Item>> itemsMap = PlayerTagsList.getItem();

                    List<Item> playerItems = itemsMap.get(player.getUniqueId());

                    if (playerItems != null && !playerItems.isEmpty()) {
                        for (Item item : playerItems) {
                            Location headLocation = player.getEyeLocation();
                            Location targetLocation = headLocation.clone().add(0, 1, 0);

                            item.teleport(targetLocation);
                            player.hideEntity(plugin, item);
                        }
                    }
                }
            }
        }, 0L,  2L);


        var filemanager = plugin.getFileManager();
        var rankConfig = filemanager.getConfig("ranks");

        var isSneakActive = rankConfig.getBoolean("playerTagConfig.hideIsSneak");
        if (isSneakActive) {
            scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    var players = Bukkit.getOnlinePlayers();
                    for(Player player : players) {
                        Map<UUID, List<ArmorStand>> armorStands = getArmorStands();
                        if (armorStands == null) {
                            return;
                        }
                        List<ArmorStand> playerArmorStands = armorStands.get(player.getUniqueId());
                        if (playerArmorStands == null) {
                            return;
                        }
                        if (playerArmorStands.size() >= 2) {
                            if (!player.isFlying()) {
                                if (player.isSneaking()){
                                    playerArmorStands.get(0).setCustomNameVisible(false);
                                    playerArmorStands.get(1).setCustomNameVisible(false);

                                } else {
                                    playerArmorStands.get(0).setCustomNameVisible(true);
                                    playerArmorStands.get(1).setCustomNameVisible(true);

                                }
                            } else {
                                playerArmorStands.get(0).setCustomNameVisible(true);
                                playerArmorStands.get(1).setCustomNameVisible(true);
                            }

                        }
                    }
                }
            }, 0L,  2);


        }


    }
}

