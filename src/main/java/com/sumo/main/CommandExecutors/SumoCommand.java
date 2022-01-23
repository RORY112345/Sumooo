package com.sumo.main.CommandExecutors;

import com.sumo.main.Main;
import com.sumo.main.YML.LocUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
                    } else if (args[1].equalsIgnoreCase("reset")) {
                        main.setPlayer1(null);
                        main.setPlayer2(null);
                        main.setPlayer1Opponent(null);
                        main.setPlayer2Opponent(null);
                        main.setMatchStarted(false);
                        main.setCountdownInProgress(false);
                        player.sendMessage(ChatColor.GREEN + "Successfully Reset.");
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
                    main.setPlayer2(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("PREFIX") + " &6You have joined the Sumo Match, Please wait for other Players to join. Then start the match by doing " + ChatColor.GRAY + "/sumo start"));
                } else {
                    player.sendMessage(ChatColor.RED + "The queue is currently full. Please try again later!");
                }
            } else if (args[0].equalsIgnoreCase("start")) {
                if (main.getPlayer1() != player && main.getPlayer2() != player) {
                    player.sendMessage(ChatColor.RED + "You're not even in the queue!");
                    return false;
                }
                if (main.getPlayer1() == null || main.getPlayer2() == null) {
                    player.sendMessage(ChatColor.RED + "There's not enough player to start the Game!");
                    return false;
                }

                main.setPlayer1Opponent(main.getPlayer2());
                main.setPlayer2Opponent(main.getPlayer1());

                if (main.getPlayer1() != null && main.getPlayer2() != null) {
                    main.getPlayer1().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("PREFIX") + " " + main.getConfig().getString("START-PRIVATE-MESSAGE").replace("{opponent}", main.getPlayer1Opponent().getName())));
                    main.getPlayer2().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("PREFIX") + " " + main.getConfig().getString("START-PRIVATE-MESSAGE").replace("{opponent}", main.getPlayer2Opponent().getName())));
                    if (main.getConfig().getBoolean("START-SHOW-PUBLIC") == true) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("PREFIX") + " " + main.getConfig().getString("START-PUBLIC-MESSAGE").replace("{player1}", main.getPlayer1().getName()).replace("{player2}", main.getPlayer2().getName())));
                    }
                    main.getPlayer1().teleport(main.getModifyLocationsFile().getLocation("PLAYER1-LOCATION"));
                    main.getPlayer2().teleport(main.getModifyLocationsFile().getLocation("PLAYER2-LOCATION"));
                    main.setMatchStarted(true);
                    main.setCountdownInProgress(true);
                    new BukkitRunnable() {
                        int countdown = 10; // The amount of seconds this should run for

                        public void run() {
                            if (countdown >= 1) {
                                main.getPlayer1().sendTitle("Starts in:",Integer.toString(main.getConfig().getInt("COUNTDOWN-TIME")),5,20,5);
                                main.getPlayer2().sendTitle("Starts in:",Integer.toString(main.getConfig().getInt("COUNTDOWN-TIME")),5,20,5);
                                countdown--;
                            } else {
                                main.setCountdownInProgress(false);
                                cancel();
                            }
                        }
                    }.runTaskTimer(main, 0, 20);
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (player.isOp()) {
                    main.reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "Config Reloaded!");
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                }
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (main.getPlayer2() == player) {
                    player.sendMessage(ChatColor.GREEN + "Successfully left the queue.");
                    main.setPlayer2(null);
                } else if (main.getPlayer1() == player) {
                    player.sendMessage(ChatColor.GREEN + "Successfully left the queue.");
                    main.setPlayer1(null);
                } else {
                    player.sendMessage(ChatColor.RED + "You're not in a queue!");
                }
            }
        }

        return false;
    }

}
