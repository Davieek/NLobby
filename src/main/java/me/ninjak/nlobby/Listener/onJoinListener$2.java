package me.ninjak.nlobby.Listener;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class onJoinListener$2 implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

        Team t = score.getTeam("nhide");
        if(t == null) {
            t = score.registerNewTeam("nhide");
            t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        }
        t.addEntry(e.getPlayer().getName());
    }

}
