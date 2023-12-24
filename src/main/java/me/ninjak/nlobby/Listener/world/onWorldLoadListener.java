package me.ninjak.nlobby.Listener.world;

import org.bukkit.Difficulty;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class onWorldLoadListener implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        var world = event.getWorld();
        world.setSpawnFlags(false, false);
        world.setDifficulty(Difficulty.PEACEFUL);
    }
}
