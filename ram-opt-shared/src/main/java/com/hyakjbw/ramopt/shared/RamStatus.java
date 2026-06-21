package com.hyakjbw.ramopt.shared;

/**
 * Shared data class representing current RAM status
 * Used across all mod versions (Forge & Fabric)
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
        return formatBytes(maxMemory);
    }

    public String getFormattedUsedMemory() {
        return formatBytes(usedMemory);
    }

    public String getFormattedFreeMemory() {
        return formatBytes(freeMemory);
    }

    private static String formatBytes(long bytes) {
        if (bytes <= 0) return "0 MB";
        final String[] units = {"B", "KB", "MB", "GB"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.1f %s", bytes / Math.pow(1024, digitGroups), units[digitGroups]);
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
