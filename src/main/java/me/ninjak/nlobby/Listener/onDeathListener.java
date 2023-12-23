package me.ninjak.nlobby.Listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class onDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        var player = (Player) event.getEntity();
        if (player instanceof Player) {
            var playerUUID = player.getUniqueId();
            Map<UUID, List<ArmorStand>> armorStands = onJoinListener.getArmorStands();
            Map<UUID, List<Silverfish>> silverFish = onJoinListener.getSilverFish();

            if(armorStands.containsKey(playerUUID)) {
                List<ArmorStand> stands = armorStands.get(playerUUID);
                for(ArmorStand as : stands) {
                    as.remove();
                }
                armorStands.remove(playerUUID);
            }

            if (silverFish.containsKey(playerUUID)) {
                List<Silverfish> silverfish = silverFish.get(playerUUID);
                for (Silverfish sv : silverfish) {
                    sv.remove();
                }
                silverfish.remove(playerUUID);
            }
        }

    }
}