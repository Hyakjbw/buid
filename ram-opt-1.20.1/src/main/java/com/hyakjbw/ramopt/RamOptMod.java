package com.hyakjbw.ramopt;

import com.hyakjbw.ramopt.config.ConfigManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main entry point for RAM Optimization Mod
 * Handles initialization and event bus registration
 */
@Mod("ramopt")
public class RamOptMod {
    public static final String MOD_ID = "ramopt";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public RamOptMod() {
        LOGGER.info("Initializing RAM Optimization Mod...");
        
        // Register config files
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigManager.CLIENT_SPEC, "ramopt-client.toml");
        
        // Register event bus
        MinecraftForge.EVENT_BUS.register(this);
        
        LOGGER.info("RAM Optimization Mod initialized successfully!");
    }
}
