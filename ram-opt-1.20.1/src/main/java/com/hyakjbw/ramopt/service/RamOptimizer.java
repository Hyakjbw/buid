package com.hyakjbw.ramopt.service;

import com.hyakjbw.ramopt.config.ConfigManager;
import com.hyakjbw.ramopt.util.LoggerUtil;
import com.hyakjbw.ramopt.util.RamUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Service responsible for optimizing and cleaning RAM
 */
public class RamOptimizer {
    private static final RamOptimizer INSTANCE = new RamOptimizer();
    
    private long lastCleanupTime;
    private long lastPeriodicCleanupTime;
    private int cleanupCount;

    private RamOptimizer() {
        this.lastCleanupTime = System.currentTimeMillis();
        this.lastPeriodicCleanupTime = System.currentTimeMillis();
        this.cleanupCount = 0;
    }

    public static RamOptimizer getInstance() {
        return INSTANCE;
    }

    /**
     * Perform standard RAM cleanup
     */
    public void performCleanup() {
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            RamUtils.performGarbageCollection();
            this.lastCleanupTime = System.currentTimeMillis();
            this.cleanupCount++;

            LoggerUtil.info("RAM cleanup performed! (Count: " + cleanupCount + ")");
            notifyCleanup("RAM cleaned - " + this.cleanupCount + " cleanups performed");
        } catch (Exception e) {
            LoggerUtil.error("Error performing RAM cleanup", e);
        }
    }

    /**
     * Perform aggressive RAM cleanup
     */
    public void performAggressiveCleanup() {
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            LoggerUtil.warn("Performing aggressive RAM cleanup...");
            RamUtils.performAggressiveGarbageCollection();
            RamUtils.clearCaches();
            
            this.lastCleanupTime = System.currentTimeMillis();
            this.cleanupCount++;

            LoggerUtil.info("Aggressive RAM cleanup completed! (Count: " + cleanupCount + ")");
            notifyCleanup("§c[AGGRESSIVE] RAM cleanup - " + this.cleanupCount + " total cleanups");
        } catch (Exception e) {
            LoggerUtil.error("Error performing aggressive RAM cleanup", e);
        }
    }

    /**
     * Check if periodic cleanup should be performed
     */
    public boolean shouldPerformPeriodicCleanup() {
        if (!ConfigManager.ENABLE_PERIODIC_CLEANUP.get()) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        long interval = ConfigManager.PERIODIC_CLEANUP_INTERVAL.get() * 50; // Convert ticks to ms (1 tick = 50ms)
        
        return (currentTime - lastPeriodicCleanupTime) >= interval;
    }

    /**
     * Perform periodic cleanup
     */
    public void performPeriodicCleanup() {
        if (shouldPerformPeriodicCleanup()) {
            try {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player == null) return;

                LoggerUtil.debug("Performing periodic RAM cleanup...");
                RamUtils.performGarbageCollection();
                
                this.lastPeriodicCleanupTime = System.currentTimeMillis();
                this.cleanupCount++;

                LoggerUtil.info("Periodic cleanup performed! (Count: " + cleanupCount + ")");
            } catch (Exception e) {
                LoggerUtil.error("Error performing periodic RAM cleanup", e);
            }
        }
    }

    /**
     * Send cleanup notification to player
     * @param message message to display
     */
    private void notifyCleanup(String message) {
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            if (ConfigManager.SHOW_ACTION_BAR_MESSAGES.get()) {
                mc.player.displayClientMessage(Component.literal("§e" + message), true);
            }

            if (ConfigManager.SHOW_CHAT_MESSAGES.get()) {
                mc.player.displayClientMessage(Component.literal("§e" + message), false);
            }
        } catch (Exception e) {
            LoggerUtil.error("Error sending notification", e);
        }
    }

    public long getLastCleanupTime() {
        return lastCleanupTime;
    }

    public int getCleanupCount() {
        return cleanupCount;
    }
}
