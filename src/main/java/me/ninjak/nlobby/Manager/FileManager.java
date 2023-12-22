package me.ninjak.nlobby.Manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.ninjak.nlobby.NLobby;

public class FileManager {

    private Map<String, FileConfiguration> configMap = new HashMap<>();
    private String configNameLoc;
    private NLobby plugin;

    public FileManager(NLobby plugin) {
        this.plugin = plugin;
    }

    public void createConfig(String configName, String configLocation) {
        configNameLoc = formatConfigLocation(configName, configLocation);

        File configFile = new File(plugin.getDataFolder(), configNameLoc + ".yml");
        if (!configFile.exists()) {
            plugin.getLogger().log(Level.INFO, "Create a new file...");
            plugin.saveResource(configNameLoc + ".yml", false);
            plugin.getLogger().log(Level.INFO, "Creating file " + configName + " has successfully completed.");
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        configMap.put(configName, config);
    }

    public FileConfiguration getConfig(String configName) {
        FileConfiguration config = configMap.get(configName);
        if (config != null) {
            return config;
        } else {
            throw new RuntimeException("Config " + configName + " not found");
        }
    }

    public void reloadConfig(String configName, String configLocation) {
        configNameLoc = formatConfigLocation(configName, configLocation);

        File configFile = new File(plugin.getDataFolder(), configNameLoc + ".yml");
        if (configFile.exists() && configFile.length() > 0) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                configMap.put(configName, config);
                plugin.getLogger().log(Level.INFO, "Reloading file " + configName + " has successfully completed.");
            } catch (Exception e) {
                createConfig(configName, configLocation);
            }
        } else {
            createConfig(configName, configLocation);
        }
    }

    public void saveConfig(String configName, String configLocation) {
        configNameLoc = formatConfigLocation(configName, configLocation);

        File configFile = new File(plugin.getDataFolder(), configNameLoc + ".yml");
        FileConfiguration config = getConfig(configName);
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatConfigLocation(String configName, String configLocation) {
        if ("default".equals(configLocation)) {
            return configName;
        } else {
            return configLocation + "/" + configName;
        }
    }
}