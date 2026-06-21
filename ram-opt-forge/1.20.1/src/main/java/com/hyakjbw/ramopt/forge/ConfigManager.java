package com.hyakjbw.ramopt.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import com.hyakjbw.ramopt.shared.IConfigProvider;

public class ConfigManager implements IConfigProvider {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_SPEC;

    // RAM Threshold Settings
    private static final ForgeConfigSpec.IntValue RAM_THRESHOLD_PERCENTAGE;
    private static final ForgeConfigSpec.IntValue RAM_CHECK_INTERVAL_TICKS;
    private static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_CLEANUP;
    private static final ForgeConfigSpec.BooleanValue ENABLE_HUD_DISPLAY;

    // Aggressive Cleanup
    private static final ForgeConfigSpec.BooleanValue ENABLE_AGGRESSIVE_CLEANUP;
    private static final ForgeConfigSpec.IntValue AGGRESSIVE_CLEANUP_THRESHOLD;
    private static final ForgeConfigSpec.BooleanValue ENABLE_PERIODIC_CLEANUP;
    private static final ForgeConfigSpec.IntValue PERIODIC_CLEANUP_INTERVAL;

    // HUD Display
    private static final ForgeConfigSpec.IntValue HUD_X_OFFSET;
    private static final ForgeConfigSpec.IntValue HUD_Y_OFFSET;
    private static final ForgeConfigSpec.BooleanValue SHOW_MAX_RAM;
    private static final ForgeConfigSpec.BooleanValue SHOW_USED_RAM;
    private static final ForgeConfigSpec.BooleanValue SHOW_FREE_RAM;
    private static final ForgeConfigSpec.IntValue HUD_TEXT_COLOR;
    private static final ForgeConfigSpec.BooleanValue SHOW_WARNING_MESSAGES;

    // Notifications
    private static final ForgeConfigSpec.BooleanValue SHOW_ACTION_BAR_MESSAGES;
    private static final ForgeConfigSpec.BooleanValue SHOW_CHAT_MESSAGES;
    private static final ForgeConfigSpec.IntValue MESSAGE_DISPLAY_TIME_SECONDS;

    static {
        BUILDER.push("RAM Cleanup Settings");
        RAM_THRESHOLD_PERCENTAGE = BUILDER
                .comment("RAM usage percentage threshold to trigger cleanup (1-100)")
                .defineInRange("ramThresholdPercentage", 85, 1, 100);
        RAM_CHECK_INTERVAL_TICKS = BUILDER
                .comment("Interval in ticks to check RAM usage")
                .defineInRange("ramCheckIntervalTicks", 100, 10, 1000);
        ENABLE_AUTO_CLEANUP = BUILDER
                .comment("Enable automatic RAM cleanup")
                .define("enableAutoCleanup", true);
        ENABLE_HUD_DISPLAY = BUILDER
                .comment("Show RAM usage on HUD overlay")
                .define("enableHudDisplay", true);
        BUILDER.pop();

        BUILDER.push("Aggressive Cleanup Settings");
        ENABLE_AGGRESSIVE_CLEANUP = BUILDER
                .comment("Enable aggressive cleanup strategy")
                .define("enableAggressiveCleanup", true);
        AGGRESSIVE_CLEANUP_THRESHOLD = BUILDER
                .comment("RAM percentage threshold for aggressive cleanup")
                .defineInRange("aggressiveCleanupThreshold", 95, 1, 100);
        ENABLE_PERIODIC_CLEANUP = BUILDER
                .comment("Enable periodic cleanup")
                .define("enablePeriodicCleanup", false);
        PERIODIC_CLEANUP_INTERVAL = BUILDER
                .comment("Interval in ticks for periodic cleanup")
                .defineInRange("periodicCleanupIntervalTicks", 6000, 1000, 36000);
        BUILDER.pop();

        BUILDER.push("HUD Display Settings");
        HUD_X_OFFSET = BUILDER
                .comment("X offset for HUD display")
                .defineInRange("hudXOffset", 10, 0, 1000);
        HUD_Y_OFFSET = BUILDER
                .comment("Y offset for HUD display")
                .defineInRange("hudYOffset", 10, 0, 1000);
        SHOW_MAX_RAM = BUILDER
                .comment("Show maximum RAM capacity")
                .define("showMaxRam", true);
        SHOW_USED_RAM = BUILDER
                .comment("Show used RAM")
                .define("showUsedRam", true);
        SHOW_FREE_RAM = BUILDER
                .comment("Show free RAM")
                .define("showFreeRam", true);
        HUD_TEXT_COLOR = BUILDER
                .comment("HUD text color in RGB format")
                .defineInRange("hudTextColor", 16777215, 0, 16777215);
        SHOW_WARNING_MESSAGES = BUILDER
                .comment("Show warning messages when RAM is high")
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
                .comment("Message display duration")
                .defineInRange("messageDisplayTimeSeconds", 3, 1, 30);
        BUILDER.pop();

        CLIENT_SPEC = BUILDER.build();
    }

    @Override
    public int getRamThresholdPercentage() {
        return RAM_THRESHOLD_PERCENTAGE.get();
    }

    @Override
    public int getRamCheckIntervalTicks() {
        return RAM_CHECK_INTERVAL_TICKS.get();
    }

    @Override
    public boolean isAutoCleanupEnabled() {
        return ENABLE_AUTO_CLEANUP.get();
    }

    @Override
    public boolean isHudDisplayEnabled() {
        return ENABLE_HUD_DISPLAY.get();
    }

    @Override
    public boolean isAggressiveCleanupEnabled() {
        return ENABLE_AGGRESSIVE_CLEANUP.get();
    }

    @Override
    public int getAggressiveCleanupThreshold() {
        return AGGRESSIVE_CLEANUP_THRESHOLD.get();
    }

    @Override
    public boolean isPeriodicCleanupEnabled() {
        return ENABLE_PERIODIC_CLEANUP.get();
    }

    @Override
    public int getPeriodicCleanupIntervalTicks() {
        return PERIODIC_CLEANUP_INTERVAL.get();
    }

    @Override
    public int getHudXOffset() {
        return HUD_X_OFFSET.get();
    }

    @Override
    public int getHudYOffset() {
        return HUD_Y_OFFSET.get();
    }

    @Override
    public boolean shouldShowMaxRam() {
        return SHOW_MAX_RAM.get();
    }

    @Override
    public boolean shouldShowUsedRam() {
        return SHOW_USED_RAM.get();
    }

    @Override
    public boolean shouldShowFreeRam() {
        return SHOW_FREE_RAM.get();
    }

    @Override
    public int getHudTextColor() {
        return HUD_TEXT_COLOR.get();
    }

    @Override
    public boolean shouldShowWarningMessages() {
        return SHOW_WARNING_MESSAGES.get();
    }

    @Override
    public boolean shouldShowActionBarMessages() {
        return SHOW_ACTION_BAR_MESSAGES.get();
    }

    @Override
    public boolean shouldShowChatMessages() {
        return SHOW_CHAT_MESSAGES.get();
    }

    @Override
    public int getMessageDisplayTimeSeconds() {
        return MESSAGE_DISPLAY_TIME_SECONDS.get();
    }
}
