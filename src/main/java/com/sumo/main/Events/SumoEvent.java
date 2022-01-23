package com.sumo.main.Events;

import com.sumo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SumoEvent implements Listener {

    private final Main main;

    public SumoEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (main.getMatchStarted() == true) {
            if (e.getEntity() == main.getPlayer1()) {
                if (main.getConfig().getBoolean("FINISH-SHOW-PUBLIC") == true) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("FINISH-PUBLIC-MESSAGE").replace("{winner}", main.getPlayer1().getName()).replace("{loser}", main.getPlayer2().getName())));
                }
                main.getPlayer1().sendMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("FINISH-WON-PRIVATE-MESSAGE").replace("{loser}", main.getPlayer2().getName())));
                main.getPlayer2().sendMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("FINISH-LOST-PRIVATE-MESSAGE").replace("{winner}", main.getPlayer1().getName())));
                main.getPlayer1().performCommand("spawn");
                main.getPlayer2().performCommand("spawn");
                main.setMatchStarted(false);
                main.setPlayer1(null);
            } else if (e.getEntity() == main.getPlayer2()) {
                if (main.getConfig().getBoolean("FINISH-SHOW-PUBLIC") == true) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("FINISH-PUBLIC-MESSAGE").replace("{winner}", main.getPlayer2().getName()).replace("{loser}", main.getPlayer1().getName())));
                }
                main.getPlayer2().sendMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("FINISH-WON-PRIVATE-MESSAGE").replace("{loser}", main.getPlayer1().getName())));
                main.getPlayer1().sendMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("FINISH-LOST-PRIVATE-MESSAGE").replace("{winner}", main.getPlayer2().getName())));
                main.getPlayer2().performCommand("spawn");
                main.getPlayer1().performCommand("spawn");
                main.setMatchStarted(false);
                main.setPlayer2(null);



            }
        }
    }


    @EventHandler
    public void onMoveCountdown(PlayerMoveEvent e) {
        if (main.getPlayer1() == e.getPlayer() || main.getPlayer2() == e.getPlayer()) {
            if (main.getCountdownInProgress() == true) {
                e.setCancelled(true);
            }
        }
    }

}
