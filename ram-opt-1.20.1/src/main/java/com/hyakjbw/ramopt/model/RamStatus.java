package com.hyakjbw.ramopt.model;

import com.hyakjbw.ramopt.util.RamUtils;

/**
 * Data class representing current RAM status
 */
public class RamStatus {
    private final long maxMemory;
    private final long usedMemory;
    private final long freeMemory;
    private final int percentUsed;
    private final long timestamp;

    public RamStatus(long maxMemory, long usedMemory, long freeMemory, int percentUsed) {
        this.maxMemory = maxMemory;
        this.usedMemory = usedMemory;
        this.freeMemory = freeMemory;
        this.percentUsed = percentUsed;
        this.timestamp = System.currentTimeMillis();
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public int getPercentUsed() {
        return percentUsed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedMaxMemory() {
        return RamUtils.formatBytes(maxMemory);
    }

    public String getFormattedUsedMemory() {
        return RamUtils.formatBytes(usedMemory);
    }

    public String getFormattedFreeMemory() {
        return RamUtils.formatBytes(freeMemory);
    }

    @Override
    public String toString() {
        return String.format("RamStatus{max=%s, used=%s, free=%s, percent=%d%%}",
                getFormattedMaxMemory(),
                getFormattedUsedMemory(),
                getFormattedFreeMemory(),
                percentUsed);
    }
}
