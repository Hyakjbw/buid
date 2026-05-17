package com.example.excavatortools;

import org.bukkit.plugin.java.JavaPlugin;

public class ExcavatorTools extends JavaPlugin {

    private static ExcavatorTools instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getCommand("excavatortool").setExecutor(new ExcavatorCommand());
        getLogger().info("ExcavatorTools enabled!");
    }

    public static ExcavatorTools getInstance() {
        return instance;
    }
}
