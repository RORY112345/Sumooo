package com.sumo.main;

import com.sumo.main.CommandExecutors.SumoCommand;
import com.sumo.main.Events.SumoEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    // Files
    private File locationsFile;
    private YamlConfiguration modifyLocationsFile;

    @Override
    public void onEnable() {
        System.out.println("Sumo Plugin Initated -");

        // CommandExecutors

        getCommand("sumo").setExecutor(new SumoCommand(this));

        // Events

        Bukkit.getPluginManager().registerEvents(new SumoEvent(this), this);

        // Config

        saveDefaultConfig();

        // YML Files Initiate

        try {
            initiateFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // YML Files Options



        // Other

    }
    // Yaml Modifier getter
    public YamlConfiguration getModifyLocationsFile() { return modifyLocationsFile; }

    // File getter
    public File getLocationsFile() { return locationsFile; }


    private void initiateFiles() throws IOException {
        locationsFile = new File(Bukkit.getServer().getPluginManager().getPlugin("Sumo").getDataFolder(), "locations.yml");

        if (!locationsFile.exists()) {
            locationsFile.createNewFile();
        }
        modifyLocationsFile = YamlConfiguration.loadConfiguration(locationsFile);
    }

    public void saveCustomYML(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
