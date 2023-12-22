package me.ninjak.nlobby.Manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.ninjak.nlobby.NLobby;

public class FileManager {

    private NLobby plugin;
    private HashMap<String, FileConfiguration> configMap = new HashMap<>();
    private java.util.logging.Logger logger;

    public void createConfig(String configName) {
        File configFile = new File(plugin.getDataFolder(), configName + ".yml");
        if (!configFile.exists()) {
            logger.log(Level.INFO,"Create a new file...");
            plugin.saveResource(configName + ".yml", false);
            logger.log(Level.INFO,"Creating file " + configName + " has successfully completed.");
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        configMap.put(configName, config);
    }

    public FileConfiguration getConfig(String configName) {
        return configMap.get(configName);
    }

    public void reloadConfig(String configName) {
        File configFile = new File(plugin.getDataFolder(), configName + ".yml");
        FileConfiguration config;
        if (configFile.exists() && configFile.length() > 0) {
            try {
                config = YamlConfiguration.loadConfiguration(configFile);
                configMap.put(configName, config);
                logger.log(Level.INFO,"Reloading file " + configName + " has successfully completed.");
            } catch (Exception e) {
                createConfig(configName);
            }
        } else {
            createConfig(configName);
        }
    }

    public void saveConfig(String configName) {
        File configFile = new File(plugin.getDataFolder(), configName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPlugin(NLobby plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }
}