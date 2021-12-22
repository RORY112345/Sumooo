package com.sumo.main;

import com.sumo.main.CommandExecutors.SumoCommand;
import com.sumo.main.Events.SumoEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Sumo Plugin Initated -");

        // CommandExecutors

        getCommand("sumo").setExecutor(new SumoCommand(this));

        // Events

        Bukkit.getPluginManager().registerEvents(new SumoEvent(this), this);

        // Config

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // YML Files



        // Other

    }

}
