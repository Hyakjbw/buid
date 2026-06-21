package com.hyakjbw.ramopt.service;

import com.hyakjbw.ramopt.config.ConfigManager;
import com.hyakjbw.ramopt.model.RamStatus;
import com.hyakjbw.ramopt.util.LoggerUtil;
import com.hyakjbw.ramopt.util.RamUtils;

/**
 * Service responsible for monitoring RAM usage
 */
public class RamMonitor {
    private static final RamMonitor INSTANCE = new RamMonitor();
    
    private RamStatus lastStatus;
    private long lastCheckTime;
    private boolean needsCleanup;
    private boolean needsAggressiveCleanup;

    private RamMonitor() {
        this.lastStatus = RamUtils.getRamStatus();
        this.lastCheckTime = System.currentTimeMillis();
        this.needsCleanup = false;
        this.needsAggressiveCleanup = false;
    }

    public static RamMonitor getInstance() {
        return INSTANCE;
    }

    /**
     * Check RAM usage and determine if cleanup is needed
     * @return true if cleanup is needed
     */
    public boolean checkAndUpdate() {
        try {
            RamStatus currentStatus = RamUtils.getRamStatus();
            this.lastStatus = currentStatus;
            this.lastCheckTime = System.currentTimeMillis();

            int currentPercent = currentStatus.getPercentUsed();
            int threshold = ConfigManager.RAM_THRESHOLD_PERCENTAGE.get();
            int aggressiveThreshold = ConfigManager.AGGRESSIVE_CLEANUP_THRESHOLD.get();

            // Check if aggressive cleanup is needed
            if (ConfigManager.ENABLE_AGGRESSIVE_CLEANUP.get() && currentPercent >= aggressiveThreshold) {
                this.needsAggressiveCleanup = true;
                this.needsCleanup = true;
                LoggerUtil.warn("Aggressive cleanup needed! RAM at " + currentPercent + "%");
                return true;
            }

            // Check if normal cleanup is needed
            if (ConfigManager.ENABLE_AUTO_CLEANUP.get() && currentPercent >= threshold) {
                this.needsCleanup = true;
                LoggerUtil.debug("Cleanup needed! RAM at " + currentPercent + "%");
                return true;
            }

            this.needsCleanup = false;
            this.needsAggressiveCleanup = false;
            return false;
        } catch (Exception e) {
            LoggerUtil.error("Error checking RAM status", e);
            return false;
        }
    }

    public RamStatus getLastStatus() {
        return lastStatus;
    }

    public boolean needsCleanup() {
        return needsCleanup;
    }

    public boolean needsAggressiveCleanup() {
        return needsAggressiveCleanup;
    }

    public long getLastCheckTime() {
        return lastCheckTime;
    }
}
