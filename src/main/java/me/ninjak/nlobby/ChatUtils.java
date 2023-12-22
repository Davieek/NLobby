package me.ninjak.nlobby;

import org.bukkit.ChatColor;

public class ChatUtils {
    public static String fixColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', "§");
    }
}
