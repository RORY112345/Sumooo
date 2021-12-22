package com.sumo.main.CommandExecutors;

import com.sumo.main.Main;
import com.sumo.main.YML.LocUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SumoCommand implements CommandExecutor {

    private final Main main;

    public SumoCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1 || args.length > 4) {
            sender.sendMessage(ChatColor.RED + "Invalid Arguments!");
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("admin")) {
                if (player.isOp()) {
                    if (args[1].equalsIgnoreCase("setlocation")) {
                        if (args.length == 4) {
                            player.sendMessage(ChatColor.RED + "Invalid Arguments! Please stand into the Player's Position and then do the command again.");
                            return false;
                        }

                        if (args[2].equalsIgnoreCase("player1")) {

                            String adminLocation = LocUtils.encode(player.getLocation());

                            main.getConfig().set("PLAYER1-LOCATION", adminLocation);
                            main.saveConfig();

                            player.sendMessage(ChatColor.GOLD + "Successfully set Player 1's Location to the Location your standing in.");
                        } else if (args[2].equalsIgnoreCase("player2")) {
                            String adminLocation = LocUtils.encode(player.getLocation());

                            main.getConfig().set("PLAYER2-LOCATION", adminLocation);
                            main.saveConfig();

                            player.sendMessage(ChatColor.GOLD + "Successfully set Player 2's Location to the Location your standing in.");
                        } else if (args[2].equalsIgnoreCase("spectator")) {
                            String adminLocation = LocUtils.encode(player.getLocation());

                            main.getConfig().set("SPECTATOR-LOCATION", adminLocation);
                            main.saveConfig();

                            player.sendMessage(ChatColor.GOLD + "Successfully set Spectator's Location to the Location your standing in.");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have any permission to use this command, If you believe this is an Error contact Server Owner / Staff.");
                }
            }
        }

        return false;
    }

}