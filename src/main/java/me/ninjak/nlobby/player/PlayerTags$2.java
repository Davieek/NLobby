package me.ninjak.nlobby.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.ninjak.nlobby.player.PlayerTagsList.*;

public class PlayerTags$2 {

    private Map<UUID, List<ArmorStand>> armorStands = getArmorStands();
    private Map<UUID, List<Rabbit>> rabbits = getRabbits();
    private Map<UUID, List<Item>> items = getItem();
    public void removeTag(Player player) {
        var playerUUID = player.getUniqueId();

        if(armorStands.containsKey(playerUUID)) {
            List<ArmorStand> stands = armorStands.get(playerUUID);
            for(ArmorStand as : stands) {
                as.remove();
            }
            armorStands.remove(playerUUID);
        }

        if (rabbits.containsKey(playerUUID)) {
            List<Rabbit> rabbits1 = rabbits.get(playerUUID);
            for (Rabbit sv : rabbits1) {
                sv.remove();
            }
            rabbits.remove(playerUUID);
        }
        if (items.containsKey(playerUUID)) {
            List<Item> items1 = items.get(playerUUID);
            for (Item sv : items1) {
                sv.remove();
            }
            items.remove(playerUUID);
        }
    }
}
