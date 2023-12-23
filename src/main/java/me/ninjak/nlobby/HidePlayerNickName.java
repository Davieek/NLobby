package me.ninjak.nlobby;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HidePlayerNickName {
    private NLobby plugin;
    public HidePlayerNickName(NLobby plugin) {
        this.plugin = plugin;
    }

    public void hidePlayerName(Player playerToHide) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(playerToHide)) {
                p.hidePlayer(plugin, playerToHide);
            }
        }
    }
}
