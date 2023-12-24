package me.ninjak.nlobby.PlaceHolderApi.Listener;


import me.ninjak.nlobby.NLobby;
import me.ninjak.nlobby.PlaceHolderApi.player.PlayerTags$Papi;
import me.ninjak.nlobby.player.PlayerTags;
import me.ninjak.nlobby.player.PlayerTags$2;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static me.ninjak.nlobby.player.PlayerTagsList.getArmorStands;

public class onPlayerCommandPreprocessListener$Papi implements Listener {
    private NLobby plugin;
    public static boolean isTrue;

    public onPlayerCommandPreprocessListener$Papi(NLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        var player = event.getPlayer();
        if (player.hasPermission("nlobby.admin")) {

            var vanish = "vanish";
            if (event.getMessage().contains(vanish)) {
                    if (!isTrue) {
                        Map<UUID, List<ArmorStand>> armorStands = getArmorStands();
                        List<ArmorStand> playerArmorStands = armorStands.get(player.getUniqueId());
                        if (playerArmorStands.size() >= 2) {
                            var playerTag = new PlayerTags$2();
                            playerTag.removeTag(player);
                            player.sendMessage("ters");
                            isTrue = true;
                        }
                    } else {
                        var playerTag = new PlayerTags$Papi(plugin);
                        playerTag.createTag(player);
                        player.sendMessage("dsdsds");
                        isTrue = false;
                    }
                }
            }
        }
    @EventHandler
    public void on(PlayerGameModeChangeEvent event) {
        var player = event.getPlayer();
        if (player.hasPermission("nlobby.admin")) {
            Map<UUID, List<ArmorStand>> armorStands = getArmorStands();

            List<ArmorStand> playerArmorStands = armorStands.get(player.getUniqueId());
            if (event.getNewGameMode() == GameMode.SPECTATOR) {
                if (playerArmorStands.size() >= 2) {
                    var playerTag = new PlayerTags$2();
                    playerTag.removeTag(player);
                    player.sendMessage("zmian gm n 23");
                }
            } else {
                var playerTag = new PlayerTags$Papi(plugin);
                playerTag.createTag(player);
                player.sendMessage("zmian gm");
            }
        }

    }
}

