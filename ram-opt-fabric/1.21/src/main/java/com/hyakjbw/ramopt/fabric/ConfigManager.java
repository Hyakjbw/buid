package com.hyakjbw.ramopt.fabric;

import com.hyakjbw.ramopt.shared.IConfigProvider;

/**
 * Configuration manager for Fabric
 * Implements IConfigProvider for Fabric version
 */
public class ConfigManager implements IConfigProvider {
    private static final ConfigManager INSTANCE = new ConfigManager();

    // Default values
    private int ramThresholdPercentage = 85;
    private int ramCheckIntervalTicks = 100;
    private boolean enableAutoCleanup = true;
    private boolean enableHudDisplay = true;
    private boolean enableAggressiveCleanup = true;
    private int aggressiveCleanupThreshold = 95;
    private boolean enablePeriodicCleanup = false;
    private int periodicCleanupIntervalTicks = 6000;
    private int hudXOffset = 10;
    private int hudYOffset = 10;
    private boolean showMaxRam = true;
    private boolean showUsedRam = true;
    private boolean showFreeRam = true;
    private int hudTextColor = 16777215;
    private boolean showWarningMessages = true;
    private boolean showActionBarMessages = true;
    private boolean showChatMessages = false;
    private int messageDisplayTimeSeconds = 3;

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    @Override
    public int getRamThresholdPercentage() {
        return ramThresholdPercentage;
    }

    @Override
    public int getRamCheckIntervalTicks() {
        return ramCheckIntervalTicks;
    }

    @Override
    public boolean isAutoCleanupEnabled() {
        return enableAutoCleanup;
    }

    @Override
    public boolean isHudDisplayEnabled() {
        return enableHudDisplay;
    }

    @Override
    public boolean isAggressiveCleanupEnabled() {
        return enableAggressiveCleanup;
    }

    @Override
    public int getAggressiveCleanupThreshold() {
        return aggressiveCleanupThreshold;
    }

    @Override
    public boolean isPeriodicCleanupEnabled() {
        return enablePeriodicCleanup;
    }

    @Override
    public int getPeriodicCleanupIntervalTicks() {
        return periodicCleanupIntervalTicks;
    }

    @Override
    public int getHudXOffset() {
        return hudXOffset;
    }

    @Override
    public int getHudYOffset() {
        return hudYOffset;
    }

    @Override
    public boolean shouldShowMaxRam() {
        return showMaxRam;
    }

    @Override
    public boolean shouldShowUsedRam() {
        return showUsedRam;
    }

    @Override
    public boolean shouldShowFreeRam() {
        return showFreeRam;
    }

    @Override
    public int getHudTextColor() {
        return hudTextColor;
    }

    @Override
    public boolean shouldShowWarningMessages() {
        return showWarningMessages;
    }

    @Override
    public boolean shouldShowActionBarMessages() {
        return showActionBarMessages;
    }

    @Override
    public boolean shouldShowChatMessages() {
        return showChatMessages;
    }

    @Override
    public int getMessageDisplayTimeSeconds() {
        return messageDisplayTimeSeconds;
    }
}
