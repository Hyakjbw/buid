package com.example.excavatortools;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final ExcavatorTools plugin;
    private FileConfiguration config;

    public ConfigManager(ExcavatorTools plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reload();
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public boolean isDebug() {
        return config.getBoolean("debug", false);
    }
}
