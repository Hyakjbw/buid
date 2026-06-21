package com.hyakjbw.ramopt.shared;

/**
 * Interface for configuration providers
 * Allows different implementations for Forge and Fabric
 */
public interface IConfigProvider {
    
    // RAM Cleanup Settings
    int getRamThresholdPercentage();
    int getRamCheckIntervalTicks();
    boolean isAutoCleanupEnabled();
    boolean isHudDisplayEnabled();
    
    // Aggressive Cleanup
    boolean isAggressiveCleanupEnabled();
    int getAggressiveCleanupThreshold();
    boolean isPeriodicCleanupEnabled();
    int getPeriodicCleanupIntervalTicks();
    
    // HUD Display
    int getHudXOffset();
    int getHudYOffset();
    boolean shouldShowMaxRam();
    boolean shouldShowUsedRam();
    boolean shouldShowFreeRam();
    int getHudTextColor();
    boolean shouldShowWarningMessages();
    
    // Notifications
    boolean shouldShowActionBarMessages();
    boolean shouldShowChatMessages();
    int getMessageDisplayTimeSeconds();
}
