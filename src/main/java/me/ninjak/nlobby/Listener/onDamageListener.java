package me.ninjak.nlobby.Listener;

import me.ninjak.nlobby.NLobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
        var players = Bukkit.getOnlinePlayers();
        for (Player player2 : players) {
            var entity = event.getEntity();
            if (entity instanceof Rabbit) {
                if (entity.getCustomName() != null && entity.getCustomName().equals(player2.getDisplayName())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
