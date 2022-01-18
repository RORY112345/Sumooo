package com.sumo.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SumoCommandTab implements TabCompleter {

    private final Main main;

    public SumoCommandTab(Main main) { this.main = main; }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> TabResult = new ArrayList<>();

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("admin","join","start"), new ArrayList<>());
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("admin")) {
                return StringUtil.copyPartialMatches(args[1], Arrays.asList("setlocation"), new ArrayList<>());
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("setlocation")) {
                
            }
        }

        return null;
    }
}
