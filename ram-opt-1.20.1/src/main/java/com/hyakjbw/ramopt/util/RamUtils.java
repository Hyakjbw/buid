package com.hyakjbw.ramopt.util;

import com.hyakjbw.ramopt.model.RamStatus;

/**
 * Utility class for RAM memory calculations and operations
 */
public class RamUtils {
    
    /**
     * Get current RAM status
     * @return RamStatus containing memory information
     */
    public static RamStatus getRamStatus() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        int percentUsed = (int) ((usedMemory * 100L) / maxMemory);
        
        return new RamStatus(maxMemory, usedMemory, freeMemory, percentUsed);
    }
    
    /**
     * Format bytes to human-readable format (MB, GB, etc.)
     * @param bytes bytes to format
     * @return formatted string
     */
    public static String formatBytes(long bytes) {
        if (bytes <= 0) return "0 MB";
        final String[] units = {"B", "KB", "MB", "GB"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.1f %s", bytes / Math.pow(1024, digitGroups), units[digitGroups]);
    }
    
    /**
     * Perform garbage collection
     */
    public static void performGarbageCollection() {
        System.gc();
        try {
            Thread.sleep(50); // Small delay to allow GC to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Perform aggressive garbage collection (multiple GC runs)
     */
    public static void performAggressiveGarbageCollection() {
        for (int i = 0; i < 3; i++) {
            System.gc();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    /**
     * Clear some caches (if possible)
     */
    public static void clearCaches() {
        // Note: This is a placeholder for potential cache clearing
        // Actual implementation would depend on specific caches to clear
        performGarbageCollection();
    }
}
