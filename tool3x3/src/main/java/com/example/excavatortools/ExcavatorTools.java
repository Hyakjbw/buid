package com.example.excavatortools;

import org.bukkit.plugin.java.JavaPlugin;

public class ExcavatorTools extends JavaPlugin {

    private static ExcavatorTools instance;

    @Override
    public void onEnable() {
        instance = this;
        WorldGuardHook.init();
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getCommand("excavatortool").setExecutor(new ExcavatorCommand());
        getLogger().info("ExcavatorTools enabled!");
    }

    @Override
    public void onDisable() {
        // Hủy đăng ký sự kiện và lệnh (Bukkit tự làm, nhưng ta đặt instance = null để GC)
        instance = null;
        getLogger().info("ExcavatorTools disabled.");
    }

    public static ExcavatorTools getInstance() {
        return instance;
    }
}
