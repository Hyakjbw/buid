package com.hyakjbw.ramopt.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Centralized configuration manager for RAM Optimization Mod
 * Handles all configuration options
 */
public class ConfigManager {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_SPEC;

    // RAM Threshold Settings
    public static final ForgeConfigSpec.IntValue RAM_THRESHOLD_PERCENTAGE;
    public static final ForgeConfigSpec.IntValue RAM_CHECK_INTERVAL_TICKS;
    public static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_CLEANUP;
    public static final ForgeConfigSpec.BooleanValue ENABLE_HUD_DISPLAY;

    // Cleanup Strategy Settings
    public static final ForgeConfigSpec.BooleanValue ENABLE_AGGRESSIVE_CLEANUP;
    public static final ForgeConfigSpec.IntValue AGGRESSIVE_CLEANUP_THRESHOLD;
    public static final ForgeConfigSpec.BooleanValue ENABLE_PERIODIC_CLEANUP;
    public static final ForgeConfigSpec.IntValue PERIODIC_CLEANUP_INTERVAL;

    // HUD Display Settings
    public static final ForgeConfigSpec.IntValue HUD_X_OFFSET;
    public static final ForgeConfigSpec.IntValue HUD_Y_OFFSET;
    public static final ForgeConfigSpec.BooleanValue SHOW_MAX_RAM;
    public static final ForgeConfigSpec.BooleanValue SHOW_USED_RAM;
    public static final ForgeConfigSpec.BooleanValue SHOW_FREE_RAM;
    public static final ForgeConfigSpec.IntValue HUD_TEXT_COLOR;
    public static final ForgeConfigSpec.BooleanValue SHOW_WARNING_MESSAGES;

    // Notification Settings
    public static final ForgeConfigSpec.BooleanValue SHOW_ACTION_BAR_MESSAGES;
    public static final ForgeConfigSpec.BooleanValue SHOW_CHAT_MESSAGES;
    public static final ForgeConfigSpec.IntValue MESSAGE_DISPLAY_TIME_SECONDS;

    static {
        BUILDER.push("RAM Cleanup Settings");
        
        // Main threshold settings
        RAM_THRESHOLD_PERCENTAGE = BUILDER
                .comment("RAM usage percentage threshold to trigger cleanup (1-100)")
                .defineInRange("ramThresholdPercentage", 85, 1, 100);
        
        RAM_CHECK_INTERVAL_TICKS = BUILDER
                .comment("Interval in ticks to check RAM usage (20 ticks = 1 second)")
                .defineInRange("ramCheckIntervalTicks", 100, 10, 1000);
        
        ENABLE_AUTO_CLEANUP = BUILDER
                .comment("Enable automatic RAM cleanup when threshold is reached")
                .define("enableAutoCleanup", true);
        
        ENABLE_HUD_DISPLAY = BUILDER
                .comment("Show RAM usage on HUD overlay")
                .define("enableHudDisplay", true);
        
        BUILDER.pop();
        BUILDER.push("Aggressive Cleanup Settings");
        
        ENABLE_AGGRESSIVE_CLEANUP = BUILDER
                .comment("Enable aggressive cleanup strategy when RAM usage is critical")
                .define("enableAggressiveCleanup", true);
        
        AGGRESSIVE_CLEANUP_THRESHOLD = BUILDER
                .comment("RAM percentage threshold for aggressive cleanup (1-100)")
                .defineInRange("aggressiveCleanupThreshold", 95, 1, 100);
        
        ENABLE_PERIODIC_CLEANUP = BUILDER
                .comment("Enable periodic cleanup regardless of current RAM usage")
                .define("enablePeriodicCleanup", false);
        
        PERIODIC_CLEANUP_INTERVAL = BUILDER
                .comment("Interval in ticks for periodic cleanup (20 ticks = 1 second)")
                .defineInRange("periodicCleanupIntervalTicks", 6000, 1000, 36000);
        
        BUILDER.pop();
        BUILDER.push("HUD Display Settings");
        
        HUD_X_OFFSET = BUILDER
                .comment("X offset for HUD display (pixels from top-left)")
                .defineInRange("hudXOffset", 10, 0, 1000);
        
        HUD_Y_OFFSET = BUILDER
                .comment("Y offset for HUD display (pixels from top-left)")
                .defineInRange("hudYOffset", 10, 0, 1000);
        
        SHOW_MAX_RAM = BUILDER
                .comment("Show maximum RAM capacity in HUD")
                .define("showMaxRam", true);
        
        SHOW_USED_RAM = BUILDER
                .comment("Show used RAM in HUD")
                .define("showUsedRam", true);
        
        SHOW_FREE_RAM = BUILDER
                .comment("Show free RAM in HUD")
                .define("showFreeRam", true);
        
        HUD_TEXT_COLOR = BUILDER
                .comment("HUD text color in RGB format (e.g., 16777215 for white)")
                .defineInRange("hudTextColor", 16777215, 0, 16777215);
        
        SHOW_WARNING_MESSAGES = BUILDER
                .comment("Show warning messages when RAM usage is high")
                .define("showWarningMessages", true);
        
        BUILDER.pop();
        BUILDER.push("Notification Settings");
        
        SHOW_ACTION_BAR_MESSAGES = BUILDER
                .comment("Show cleanup notifications in action bar")
                .define("showActionBarMessages", true);
        
        SHOW_CHAT_MESSAGES = BUILDER
                .comment("Show cleanup notifications in chat")
                .define("showChatMessages", false);
        
        MESSAGE_DISPLAY_TIME_SECONDS = BUILDER
                .comment("How long to display cleanup messages (seconds)")
                .defineInRange("messageDisplayTimeSeconds", 3, 1, 30);
        
        BUILDER.pop();
        CLIENT_SPEC = BUILDER.build();
    }
}
