package com.example.excavatortools;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ExcavatorTools extends JavaPlugin {

    private static ExcavatorTools instance;
    private static NamespacedKey excavatorKey;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        excavatorKey = new NamespacedKey(this, "excavator");
        configManager = new ConfigManager(this);
        WorldGuardHook.init(); // nếu có
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getCommand("excavatortool").setExecutor(new ExcavatorCommand());
        getLogger().info("ExcavatorTools enabled! Debug: " + configManager.isDebug());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static ExcavatorTools getInstance() { return instance; }
    public static NamespacedKey getExcavatorKey() { return excavatorKey; }
    public ConfigManager getConfigManager() { return configManager; }

    public static boolean isExcavatorTool(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(getExcavatorKey(), PersistentDataType.BYTE);
    }

    public void debug(String message) {
        if (configManager.isDebug()) {
            getLogger().info("[DEBUG] " + message);
        }
    }
}
