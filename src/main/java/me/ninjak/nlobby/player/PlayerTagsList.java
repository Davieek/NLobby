package me.ninjak.nlobby.player;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Rabbit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerTagsList {
    private static Map<UUID, List<Item>> item = new HashMap<>();
    private static Map<UUID, List<ArmorStand>> armorStands = new HashMap<>();
    private static Map<UUID, List<Rabbit>> rabbits = new HashMap<>();
    public static Map<UUID, List<ArmorStand>> getArmorStands() {
        return armorStands;
    }
    public static Map<UUID, List<Rabbit>> getRabbits() {
        return rabbits;
    }
    public static Map<UUID, List<Item>> getItem() {
        return item;
    }

}
