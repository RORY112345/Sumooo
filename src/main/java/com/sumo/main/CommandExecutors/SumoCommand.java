package com.sumo.main.CommandExecutors;

import com.sumo.main.Main;
import com.sumo.main.YML.LocUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

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

                            Location adminLocation = player.getLocation();

                            main.getModifyLocationsFile().createSection("PLAYER1-LOCATION");
                            main.getModifyLocationsFile().set("PLAYER1-LOCATION", adminLocation);

                            main.saveCustomYML(main.getModifyLocationsFile(), main.getLocationsFile());

                            player.sendMessage(ChatColor.GOLD + "Successfully set Player 1's Location to the Location your standing in.");
                        } else if (args[2].equalsIgnoreCase("player2")) {
                            Location adminLocation = player.getLocation();

                            main.getModifyLocationsFile().createSection("PLAYER2-LOCATION");
                            main.getModifyLocationsFile().set("PLAYER2-LOCATION", adminLocation);

                            main.saveCustomYML(main.getModifyLocationsFile(), main.getLocationsFile());

                            player.sendMessage(ChatColor.GOLD + "Successfully set Player 2's Location to the Location your standing in.");
                        } else if (args[2].equalsIgnoreCase("spectator")) {
                            Location adminLocation = player.getLocation();

                            main.getModifyLocationsFile().createSection("PLAYER3-LOCATION");
                            main.getModifyLocationsFile().set("PLAYER3-LOCATION", adminLocation);

                            main.saveCustomYML(main.getModifyLocationsFile(), main.getLocationsFile());

                            player.sendMessage(ChatColor.GOLD + "Successfully set Spectator's Location to the Location your standing in.");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have any permission to use this command, If you believe this is an Error contact Server Owner / Staff.");
                }
            } else if (args[0].equalsIgnoreCase("join")) {
                if (main.getPlayer1() == player || main.getPlayer2() == player) {
                    player.sendMessage(ChatColor.RED + "You're already in the queue!");
                    return false;
                }
                if (main.getPlayer1() == null) {
                    main.setPlayer1(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("PREFIX") + " &6You have joined the Sumo Match, Please wait for other Players to join. Then start the match by doing " + ChatColor.GRAY + "/sumo start"));
                    return false;
                }
                if (main.getPlayer2() == null) {
                    main.setPlayer1(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("PREFIX") + " &6You have joined the Sumo Match, Please wait for other Players to join. Then start the match by doing " + ChatColor.GRAY + "/sumo start"));
                } else {
                    player.sendMessage(ChatColor.RED + "The queue is currently full. Please try again later!");
                }
            } else if (args[0].equalsIgnoreCase("start")) {

            }
        }

        return false;
    }

}
