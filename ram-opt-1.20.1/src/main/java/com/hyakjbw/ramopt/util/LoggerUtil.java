package com.hyakjbw.ramopt.util;

import com.hyakjbw.ramopt.RamOptMod;
import org.apache.logging.log4j.Level;

/**
 * Centralized logging utility
 */
public class LoggerUtil {
    
    public static void info(String message) {
        RamOptMod.LOGGER.log(Level.INFO, message);
    }
    
    public static void debug(String message) {
        RamOptMod.LOGGER.log(Level.DEBUG, message);
    }
    
    public static void warn(String message) {
        RamOptMod.LOGGER.log(Level.WARN, message);
    }
    
    public static void error(String message) {
        RamOptMod.LOGGER.log(Level.ERROR, message);
    }
    
    public static void error(String message, Throwable throwable) {
        RamOptMod.LOGGER.log(Level.ERROR, message, throwable);
    }
}
