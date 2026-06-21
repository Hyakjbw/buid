package com.hyakjbw.ramopt.shared;

/**
 * Shared utility class for color operations
 * Used across all mod versions
 */
public class ColorUtils {
    
    /**
     * Get color based on RAM usage percentage
     * Green (0-50%) -> Yellow (50-80%) -> Red (80-100%)
     * @param percentUsed percentage of RAM used
     * @return RGB color value
     */
    public static int getColorForRamUsage(int percentUsed) {
        if (percentUsed < 50) {
            return 0x00FF00; // Green
        } else if (percentUsed < 80) {
            return 0xFFFF00; // Yellow
        } else {
            return 0xFF0000; // Red
        }
    }
    
    /**
     * Convert RGB values to single integer
     * @param r red (0-255)
     * @param g green (0-255)
     * @param b blue (0-255)
     * @return RGB color value
     */
    public static int rgbToInt(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
    
    /**
     * Extract red component from RGB integer
     */
    public static int getRed(int color) {
        return (color >> 16) & 0xFF;
    }
    
    /**
     * Extract green component from RGB integer
     */
    public static int getGreen(int color) {
        return (color >> 8) & 0xFF;
    }
    
    /**
     * Extract blue component from RGB integer
     */
    public static int getBlue(int color) {
        return color & 0xFF;
    }
}
