package me.ninjak.nlobby.API;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class NPlaceHolder {
    public static String formatPlayerName(Player player, String message) {

        return message.replace("%playerName%", player.getDisplayName());
    }
}
