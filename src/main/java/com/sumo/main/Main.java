package com.sumo.main;

import com.sumo.main.CommandExecutors.SumoCommand;
import com.sumo.main.Events.SumoEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    // Files
    private File locationsFile;
    private YamlConfiguration modifyLocationsFile;

    // Players
    private Player player1 = null;
    private Player player2 = null;
    private Player player1Opponent = null;
    private Player player2Opponent = null;
    private boolean matchStarted = false;

    @Override
    public void onEnable() {
        System.out.println("Sumo Plugin Initated -");

        // CommandExecutors

        getCommand("sumo").setExecutor(new SumoCommand(this));

        // Tab Completions

        getCommand("sumo").setTabCompleter(new SumoCommandTab(this));

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

    // Player getter

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Player getPlayer1Opponent() { return player1Opponent; }
    public Player getPlayer2Opponent() { return player2Opponent; }

    // Player setter

    public void setPlayer1(Player player) { this.player1 = player; }

    public void setPlayer2(Player player) { this.player2 = player; }

    public void setPlayer1Opponent(Player player) { this.player1Opponent = player; }

    public void setPlayer2Opponent(Player player) { this.player2Opponent = player; }

    // Boolean getter
    public boolean getMatchStarted() { return matchStarted; }

    // Boolean setter

    public void setMatchStarted(boolean value) {
        this.matchStarted = value;
    }

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
